package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.entity.Task;
import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.exceptions.AlertData;
import com.epam.cloudgantt.exceptions.ErrorData;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.parser.CsvParser;
import com.epam.cloudgantt.parser.CsvValidator;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.payload.ProjectDTO;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import com.epam.cloudgantt.payload.SectionDTO;
import com.epam.cloudgantt.payload.TaskDTO;
import com.epam.cloudgantt.payload.UpdateProjectDTO;
import com.epam.cloudgantt.repository.ProjectRepository;
import com.epam.cloudgantt.repository.TaskRepository;
import com.epam.cloudgantt.util.CSVConstants;
import com.epam.cloudgantt.util.CommonUtils;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;

    private final CsvParser csvParser;

    private final CsvValidator csvValidator;

    @Override
    public ApiResult<?> delete(UUID id, User user) {
        Project project = projectRepository.findById(id).orElseThrow(() -> RestException.restThrow("Project does not exist."));

        if (!Objects.equals(project.getUser(), user))
            throw RestException.restThrow("You are not allowed to rename.");

        projectRepository.deleteById(id);
        return ApiResult.successResponse("Project was successfully deleted.");
    }

    @Override
    public ApiResult<ProjectResponseDTO> createNewProject(CreateProjectDTO createProjectDTO, User user) {

        if (Objects.isNull(createProjectDTO))
            throw RestException.restThrow("NAME_MUST_NOT_BE_NULL");

        Project project = new Project();
        project.setName(createProjectDTO.getName());
        project.setUser(user);
        projectRepository.save(project);

        return ApiResult.successResponse("Project was successfully created");
    }


    @Override
    public ApiResult<ProjectResponseDTO> updateProjectName(UpdateProjectDTO updateProjectDTO, User user) {
        if (Objects.isNull(updateProjectDTO)) {
            throw RestException.restThrow("NAME_MUST_NOT_BE_NULL");
        }
        String input = updateProjectDTO.getName();
        int lastIndex = input.length() - 1;
        while (lastIndex >= 0 && Character.isWhitespace(input.charAt(lastIndex))) {
            lastIndex--;
        }

        int firstIndex = 0;
        while (firstIndex < input.length() && Character.isWhitespace(input.charAt(firstIndex))) {
            firstIndex++;
        }

        String inputNameAfterIgnoringSpaces = input.substring(firstIndex, lastIndex + 1);

        if (inputNameAfterIgnoringSpaces.length() < 3 ||
                inputNameAfterIgnoringSpaces.length() > 255) {
            throw RestException.restThrow(MessageByLang.getMessage("PROJECT_NAME_LENGTH_ERROR"));
        }
        Project project = projectRepository.findById(updateProjectDTO.getId()).orElseThrow(() -> RestException.restThrow("Project does not exist."));

        if (!project.getUser().equals(user)) throw RestException.restThrow("You are not allowed to rename.");

        project.setName(inputNameAfterIgnoringSpaces);
        projectRepository.save(project);
        return ApiResult.successResponse("Project_Name was successfully edited.");
    }

    @Override
    public ApiResult<List<ProjectDTO>> myProjects(User user) {
        List<Project> projects = projectRepository.findAllByUser(user);
        return ApiResult.successResponse(projects.stream().map(this::mapProjectToProjectDTO).collect(Collectors.toList()));
    }

    @Override
    public ApiResult<ProjectDTO> myProjectById(UUID id, User user, PageRequest pageRequest) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("Project not found"));
        ProjectDTO projectDTO = mapProjectToProjectDTO(project);

        Page<Task> pageableTasks = taskRepository.findByProjectId(id, pageRequest);

        int numberOfPages = project.getTasks().size() / pageRequest.getPageSize() + 1;
        projectDTO.setTotalPages(numberOfPages);
        projectDTO.setHasAssignee(hasAssignee(project.getTasks()));
        projectDTO.setHasDate(hasDate(project.getTasks()));
        projectDTO.setSections(mapTasksToSectionDTO(pageableTasks));

        return ApiResult.successResponse(projectDTO);
    }

    private boolean hasDate(List<Task> tasks) {
        for (Task task : tasks) {
            if (Objects.nonNull(task.getBeginDate()) || Objects.nonNull(task.getEndDate())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasAssignee(List<Task> tasks) {
        for (Task task : tasks) {
            if (!StringUtils.isEmpty(task.getAssignee())) {
                return true;
            }
        }
        return false;
    }

    private ProjectDTO mapProjectToProjectDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getName());
    }

    @Override
    public ApiResult<ProjectResponseDTO> uploadCSVFileToCreateProject(MultipartFile file, User user) {
        if (file.getSize() > CSVConstants.MAX_FILE_SIZE)
            throw RestException.restThrow(MessageByLang.getMessage("CSV_FILE_SIZE_EXCEEDS_5MB"));

        List<Task> tasks;
        AlertData alertData = new AlertData(new ArrayList<>());
        ErrorData errorData = new ErrorData((new ArrayList<>()));

        try {
            csvParser.setAlertData(alertData);
            csvParser.setErrorData(errorData);
            csvValidator.setErrorData(errorData);
            tasks = csvParser.parseCsvFile(file.getInputStream());
        } catch (IOException e) {
            throw RestException.restThrow(e.getMessage());
        }

        csvValidator.setAlertData(alertData);
        tasks = csvValidator.validateAll(tasks);
        if (tasks.isEmpty()) {
            ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
            projectResponseDTO.setMessage(errorData.getErrorMessages());
            return ApiResult.errorResponseWithData(projectResponseDTO);
        }
        Project project = new Project();
        project.setTasks(tasks);

        String originalFilename = file.getOriginalFilename();
        String projectName = "Project";
        if (Objects.nonNull(originalFilename)) {
            projectName = originalFilename.substring(0, originalFilename.indexOf("."));
        }
        project.setName(projectName);
        project.setUser(user);

        tasks.forEach(task -> task.setProject(project));

        projectRepository.save(project);

        return ApiResult.successResponse(
                new ProjectResponseDTO(
                        project.getId(),
                        alertData.getAlertMessages()
                ),
                MessageByLang.getMessage("CSV_PROJECT_SUCCESSFULLY_UPLOADED"));
    }

    private TaskDTO mapTaskToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setProjectId(task.getProject().getId());
        taskDTO.setAssignee(task.getAssignee());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setTaskNumber(task.getTaskNumber());
        taskDTO.setBeginDate(task.getBeginDate());
        taskDTO.setEndDate(task.getEndDate());
        taskDTO.setTaskName(task.getTaskName());
        taskDTO.setId(task.getId());
        taskDTO.setSectionName(task.getSectionName());
        taskPredecessor(task, taskDTO);
        taskSuccessor(task, taskDTO);

        int duration = 1;
        if (Objects.nonNull(task.getEndDate()) && Objects.nonNull(task.getBeginDate())) {
            duration = CommonUtils.getDiffTwoDateInDays(task.getBeginDate(), task.getEndDate()) + 1;
        } else if (task.getBeginDate() == null && task.getEndDate() == null) {
            duration = 0;
        }

        taskDTO.setDuration(duration);
        return taskDTO;
    }

    private void taskSuccessor(Task task, TaskDTO taskDTO) {
        String taskNumber = String.valueOf(task.getTaskNumber());
        List<Task> allByDependencyContaining = taskRepository.getAllByDependencyContaining(taskNumber);

        if (!allByDependencyContaining.isEmpty()) {
            allByDependencyContaining = allByDependencyContaining.stream()
                    .filter(t -> Arrays.stream(t.getDependency().split(",")).collect(Collectors.toSet()).contains(taskNumber)).toList();

            taskDTO.setSuccessor(allByDependencyContaining
                    .stream()
                    .map(Task::getTaskNumber)
                    .toList().toString().replace("[", "").replace("]", ""));
        }
    }

    private void taskPredecessor(Task task, TaskDTO taskDTO) {
        taskDTO.setPredecessor(task.getDependency());
    }

    private List<TaskDTO> mapTasksToTaskDTOList(List<Task> tasks) {
        return tasks.stream().map(this::mapTaskToTaskDTO).toList();
    }

    private List<SectionDTO> mapTasksToSectionDTO(Page<Task> pageableTasks) {
        TreeMap<String, List<Task>> sectionMap =
                new TreeMap<>((s1, s2) -> {
                    List<Task> tasks1 = pageableTasks.stream()
                            .filter(t -> t.getSectionName().equals(s1))
                            .sorted(Comparator.comparingInt(t -> Math.toIntExact(t.getTaskNumber()))).toList();
                    List<Task> tasks2 = pageableTasks.stream()
                            .filter(t -> t.getSectionName().equals(s2))
                            .sorted(Comparator.comparingInt(t -> Math.toIntExact(t.getTaskNumber()))).toList();
                    return Math.toIntExact(tasks1.get(0).getTaskNumber() - tasks2.get(0).getTaskNumber());
                });
        pageableTasks.forEach(task ->
                sectionMap.computeIfAbsent(task.getSectionName(), k -> new ArrayList<>()).add(task));

        List<SectionDTO> sectionDTOList = new ArrayList<>();
        sectionMap.forEach((sectionName, tasks) ->
                sectionDTOList.add(new SectionDTO(
                        sectionName,
                        mapTasksToTaskDTOList(tasks)
                )));

        return sectionDTOList;
    }

}

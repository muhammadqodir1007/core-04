package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.entity.Task;
import com.epam.cloudgantt.entity.User;
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
import com.epam.cloudgantt.util.CSVConstants;
import com.epam.cloudgantt.util.CommonUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

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
        if (updateProjectDTO == null) throw RestException.restThrow("NAME_MUST_NOT_BE_NULL");
        Project project = projectRepository.findById(updateProjectDTO.getId()).orElseThrow(() -> RestException.restThrow("Project does not exist."));

        if (!project.getUser().equals(user)) throw RestException.restThrow("You are not allowed to rename.");

        project.setName(updateProjectDTO.getName());
        projectRepository.save(project);
        return ApiResult.successResponse("Project_Name was successfully edited.");
    }

    @Override
    public ApiResult<List<ProjectDTO>> myProjects(User user) {
        List<Project> projects = projectRepository.findAllByUser(user);
        return ApiResult.successResponse(projects.stream().map(this::mapProjectToProjectDTO).collect(Collectors.toList()));
    }

    @Override
    public ApiResult<ProjectDTO> myProjectById(UUID id, User user) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("Project not found"));

        ProjectDTO projectDTO = mapProjectToProjectDTO(project);

        projectDTO.setSections(mapTasksToSectionDTO(project));

        return ApiResult.successResponse(projectDTO);
    }


    private ProjectDTO mapProjectToProjectDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getName());
    }

    @Override
    public ApiResult<ProjectResponseDTO> uploadCSVFileToCreateProject(MultipartFile file, User user) {
        if (file.getSize() > CSVConstants.MAX_FILE_SIZE)
            throw RestException.restThrow(MessageByLang.getMessage("CSV_FILE_SIZE_EXCEEDS_5MB"));

        List<Task> tasks;
        ErrorData errorData = new ErrorData(new ArrayList<>());

        try {
            csvParser.setErrorData(errorData);
            tasks = csvParser.parseCsvFile(file.getInputStream());
        } catch (IOException e) {
            throw RestException.restThrow(e.getMessage());
        }

        csvValidator.setErrorData(errorData);
        tasks = csvValidator.validateAll(tasks);

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
                        errorData.getAlertMessages()
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
        int duration = 0;
        if (Objects.nonNull(task.getEndDate()) && Objects.nonNull(task.getBeginDate()))
            duration = CommonUtils.getDiffTwoDateInDays(task.getBeginDate(), task.getEndDate()) + 1;

        taskDTO.setDuration(duration);
        return taskDTO;
    }

    private List<TaskDTO> mapTasksToTaskDTOList(List<Task> tasks) {
        return tasks.stream().map(this::mapTaskToTaskDTO).collect(Collectors.toList());
    }

    private List<SectionDTO> mapTasksToSectionDTO(Project project) {
        Map<String, List<Task>> sectionMap =
                project.getTasks().stream().collect(Collectors.groupingBy(Task::getSectionName));

        List<SectionDTO> sectionDTOList = new ArrayList<>();
        sectionMap.forEach((sectionName, tasks) ->
                sectionDTOList.add(new SectionDTO(
                        sectionName,
                        mapTasksToTaskDTOList(tasks)
                )));

        return sectionDTOList;
    }

}

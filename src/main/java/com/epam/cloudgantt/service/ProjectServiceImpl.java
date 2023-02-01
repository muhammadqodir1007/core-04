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
import com.epam.cloudgantt.payload.TaskDTO;
import com.epam.cloudgantt.payload.UpdateProjectDTO;
import com.epam.cloudgantt.repository.ProjectRepository;
import com.epam.cloudgantt.util.CSVConstants;
import com.epam.cloudgantt.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ErrorData errorData;

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

        List<TaskDTO> taskDTOList = project.getTasks()
                .stream()
                .map(this::mapTaskToTaskDTO)
                .collect(Collectors.toList());

        projectDTO.setTasks(taskDTOList);

        return ApiResult.successResponse(projectDTO);
    }


    private ProjectDTO mapProjectToProjectDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getName());
    }

    @Override
    public ApiResult<String> uploadCSVFileToCreateProject(MultipartFile file, User user) {
        if (file.getSize() > CSVConstants.MAX_FILE_SIZE)
            throw RestException.restThrow(MessageByLang.getMessage("CSV_FILE_SIZE_EXCEEDS_5MB"));

        List<Task> tasks;
        try {
            tasks = new CsvParser(errorData)
                    .parseCsvFile(file.getInputStream());
        } catch (IOException e) {
            throw RestException.restThrow(e.getMessage());
        }
        tasks = new CsvValidator(errorData).validateAll(tasks);
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


        System.out.println(MessageByLang.getMessage("CSV_PROJECT_SUCCESSFULLY_UPLOADED") + " \n" + String.join("\n", errorData.getErrorMessages()));
        return ApiResult.successResponse(MessageByLang.getMessage("CSV_PROJECT_SUCCESSFULLY_UPLOADED") + " \n" + String.join("\n", errorData.getErrorMessages()));

    }

    private TaskDTO mapTaskToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setProjectId(task.getProject().getId());
        taskDTO.setAssignee(task.getAssignee());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setTaskNumber(task.getTaskNumber());
        taskDTO.setBeginDate(task.getBeginDate());
        taskDTO.setEndDate(task.getEndDate());
        taskDTO.setId(task.getId());
        taskDTO.setSectionName(task.getSectionName());
        int duration = 0;
        if (Objects.nonNull(task.getEndDate()) && Objects.nonNull(task.getBeginDate()))
            duration = CommonUtils.getDiffTwoDateInDays(task.getEndDate(), task.getBeginDate());
        else if (Objects.isNull(task.getEndDate()) || Objects.isNull(task.getBeginDate()))
            duration = 1;

        taskDTO.setDuration(duration);
        return taskDTO;
    }

}

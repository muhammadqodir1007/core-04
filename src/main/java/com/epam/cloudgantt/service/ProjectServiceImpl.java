package com.epam.cloudgantt.service;

import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.entity.Task;
import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.exceptions.ErrorData;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.mapper.ProjectMapper;
import com.epam.cloudgantt.parser.CsvParser;
import com.epam.cloudgantt.parser.CsvValidator;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.payload.ProjectDTO;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import com.epam.cloudgantt.payload.UpdateProjectDTO;
import com.epam.cloudgantt.repository.ProjectRepository;
import com.epam.cloudgantt.repository.UserRepository;
import com.epam.cloudgantt.util.CSVConstants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ErrorData errorData;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;

    @Override
    public ApiResult<?> delete(UUID id, User user) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("Project does not exist."));
        if (!project.getUser().equals(user))
            throw RestException.restThrow("You are not allowed to rename.");
        projectRepository.deleteById(id);
        return ApiResult.successResponse("Project was successfully deleted.");
    }

    @Override
    public ApiResult<ProjectResponseDTO> createNewProject(CreateProjectDTO createProjectDTO, User user) {

        if (createProjectDTO == null) {
            throw RestException.restThrow("NAME_MUST_NOT_BE_NULL");
        }
        Project project = new Project();
        project.setName(createProjectDTO.getName());
        project.setUser(user);
        projectRepository.save(project);

        return ApiResult.successResponse("Project was successfully created");
    }


    @Override
    public ApiResult<ProjectResponseDTO> updateProjectName(UpdateProjectDTO updateProjectDTO, User user) {
        if (updateProjectDTO == null)
            throw RestException.restThrow("NAME_MUST_NOT_BE_NULL");
        Project project = projectRepository.findById(updateProjectDTO.getId()).orElseThrow(() -> RestException.restThrow("Project does not exist."));

        if (!project.getUser().equals(user))
            throw RestException.restThrow("You are not allowed to rename.");

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
        Project project = projectRepository.findById(id).orElseThrow(() -> RestException.restThrow("Project not found"));
        ProjectDTO projectDTO = mapProjectToProjectDTO(project);
        return ApiResult.successResponse(projectDTO);
    }

    private ProjectDTO mapProjectToProjectDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getName());
    }

    @Override
    @Transactional
    @SneakyThrows
    public ApiResult<ProjectResponseDTO> uploadCSV(MultipartFile file, User user) {
        if (file.getSize() > CSVConstants.MAX_FILE_SIZE) {
            throw new Exception("Uploaded file size exceeds 5MB!");
        }
        List<Task> tasks = new CsvParser(errorData).parseCsvFile(file.getInputStream());
        tasks = new CsvValidator(errorData).validateAll(tasks);
        String fileName = file.getOriginalFilename();
        int index = Objects.requireNonNull(fileName).indexOf(".");
        String projectName = "project";
        if (index != -1) {
            projectName = fileName.substring(0, index);
        }
        Project project = new Project();
        project.setListOfTasks(tasks);
        project.setName(projectName);
        project.setUser(user);

        tasks.forEach(task -> task.setProject(project));

        projectRepository.save(project);
        return ApiResult.successResponse(new ProjectResponseDTO(Collections.singletonList("Project loaded successfully!")));
    }
}

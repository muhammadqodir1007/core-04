package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.exceptions.ErrorData;
import com.epam.cloudgantt.payload.*;
import com.epam.cloudgantt.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class ProjectControllerImpl implements ProjectController {
    private final ProjectService projectService;
    private final com.epam.cloudgantt.exceptions.ErrorData errorData;

    public ProjectControllerImpl(ProjectService projectService, ErrorData errorData) {
        this.projectService = projectService;
        this.errorData = errorData;
    }

    @Override
    public ApiResult<?> deleteEmptyProject(UUID id, User user) {
        return projectService.delete(id, user);
    }

    @Override
    public ApiResult<ProjectResponseDTO> createNewProject(CreateProjectDTO createProjectDTO, User user) {
        return projectService.createNewProject(createProjectDTO, user);
    }

    @Override
    public ApiResult<ProjectResponseDTO> updateProjectName(UpdateProjectDTO updateProjectDTO, User user) {
        return projectService.updateProjectName(updateProjectDTO, user);
    }

    @Override
    public ApiResult<List<ProjectDTO>> myProjects(User user) {
        return projectService.myProjects(user);
    }

    @Override
    public ApiResult<ProjectDTO> myProjectById(UUID id, User user) {
        return projectService.myProjectById(id, user);
    }

    @Override
    public ApiResult<ProjectResponseDTO> uploadCSV(MultipartFile file, User user) {
        try {
            projectService.uploadCSVFileToCreateProject(file, user);
            return ApiResult.successResponse(new ProjectResponseDTO(errorData.getErrorMessages()));
        } catch (RuntimeException e) {
            return ApiResult.errorResponseWithData(new ProjectResponseDTO(Collections.singletonList(e.getMessage())));
        }
    }

}

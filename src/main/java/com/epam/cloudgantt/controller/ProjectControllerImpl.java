package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.*;
import com.epam.cloudgantt.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class ProjectControllerImpl implements ProjectController {
    private final ProjectService projectService;

    public ProjectControllerImpl(ProjectService projectService) {
        this.projectService = projectService;
    }


    @Override
    public ApiResult<?> deleteEmptyProject(UUID id) {
        return projectService.delete(id);
    }

    @Override

    public ApiResult<?> createNewProject(CreateProjectDTO createProjectDTO, User user) {
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
        return projectService.myProjectById(id,user);
    }
}

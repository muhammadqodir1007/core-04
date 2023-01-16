package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import com.epam.cloudgantt.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class ProjectControllerImpl implements ProjectController {
    private final ProjectService projectService;

    public ProjectControllerImpl(ProjectService projectService) {
        this.projectService = projectService;
    }


    @Override
    public ApiResult<ProjectResponseDTO> deleteEmptyProject(UUID id) {
        return projectService.delete(id);
    }

    @Override

    public String createNewProject(CreateProjectDTO createProjectDTO) {
        return projectService.createNewProject(createProjectDTO);
    }
}

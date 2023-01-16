package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProjectControllerImpl implements ProjectController{

    private final ProjectService projectService;
    @Override
    public String createNewProject(CreateProjectDTO createProjectDTO) {
        return projectService.createNewProject(createProjectDTO);
    }
}

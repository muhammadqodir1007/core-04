package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = ProjectController.BASE_PATH)
public interface ProjectController {

    String BASE_PATH = AppConstants.BASE_PATH + "project/";

    @PostMapping()
    String createNewProject(@RequestBody @Valid CreateProjectDTO createProjectDTO);

}

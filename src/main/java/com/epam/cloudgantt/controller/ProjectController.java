package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import com.epam.cloudgantt.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = ProjectController.BASE_PATH)
public interface ProjectController {
    String BASE_PATH = AppConstants.BASE_PATH + "project/";

    @DeleteMapping("delete-empty/{id}")
    ApiResult<ProjectResponseDTO> deleteEmptyProject(@PathVariable UUID id);

    @PostMapping()
    String createNewProject(@RequestBody @Valid CreateProjectDTO createProjectDTO);

}

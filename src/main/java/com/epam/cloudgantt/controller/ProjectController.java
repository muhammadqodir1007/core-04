package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import com.epam.cloudgantt.payload.UpdateProjectDTO;
import com.epam.cloudgantt.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = ProjectController.BASE_PATH)
public interface ProjectController {
    String BASE_PATH = AppConstants.BASE_PATH + "project/";
    String CREATE = "create";
    String UPDATE = "update";

    @DeleteMapping("delete-empty/{id}")
    ApiResult<ProjectResponseDTO> deleteEmptyProject(@PathVariable UUID id);

    @PostMapping(CREATE)
    ApiResult<ProjectResponseDTO> createNewProject(@RequestBody @Valid CreateProjectDTO createProjectDTO);

    @PutMapping(UPDATE)
    ApiResult<ProjectResponseDTO> updateProjectName(@RequestBody @Valid UpdateProjectDTO updateProjectDTO);

}

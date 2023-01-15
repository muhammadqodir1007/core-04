package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import com.epam.cloudgantt.util.AppConstants;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequestMapping(value = ProjectController.BASE_PATH)
public interface ProjectController {
    String BASE_PATH = AppConstants.BASE_PATH + "project/";

    @DeleteMapping("delete-empty/{id}")
    ApiResult<ProjectResponseDTO> deleteEmptyProject(@PathVariable UUID id);
}

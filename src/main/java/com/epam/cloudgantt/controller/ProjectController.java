package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.*;
import com.epam.cloudgantt.security.CurrentUser;
import com.epam.cloudgantt.util.AppConstants;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = ProjectController.BASE_PATH)
public interface ProjectController {
    String BASE_PATH = AppConstants.BASE_PATH + "project/";
    String CREATE_PATH = "create";
    String UPDATE_PATH = "update";
    String DELETE_PATH = "delete-empty/{id}";
    String MY_PROJECTS_PATH = "my-projects";

    @DeleteMapping(value = DELETE_PATH)
    ApiResult<ProjectResponseDTO> deleteEmptyProject(@PathVariable UUID id);

    @PostMapping(value = CREATE_PATH)
    ApiResult<ProjectResponseDTO> createNewProject(@RequestBody @Valid CreateProjectDTO createProjectDTO,
                                                   @CurrentUser User user);

    @PutMapping(value = UPDATE_PATH)
    ApiResult<ProjectResponseDTO> updateProjectName(@RequestBody @Valid UpdateProjectDTO updateProjectDTO,
                                                    @CurrentUser User user);

    @GetMapping(value = MY_PROJECTS_PATH)
    ApiResult<List<ProjectDTO>>myProjects(@CurrentUser User user);

}

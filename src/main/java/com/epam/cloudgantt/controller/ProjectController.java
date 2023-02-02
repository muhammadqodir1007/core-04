package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.*;
import com.epam.cloudgantt.security.CurrentUser;
import com.epam.cloudgantt.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.epam.cloudgantt.exceptions.ErrorData;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RequestMapping(value = ProjectController.BASE_PATH)
public interface ProjectController {
    String BASE_PATH = AppConstants.BASE_PATH + "project/";
    String CREATE_PATH = "create";
    String UPDATE_PATH = "update";
    String UPLOAD_CSV_PATH = "upload-csv";
    String DELETE_PATH = "delete-empty/{id}";
    String MY_PROJECTS_PATH = "my-projects";
    String MY_PROJECT_BY_ID_PATH = "my-project-by-id/{id}";

    @DeleteMapping(value = DELETE_PATH)
    ApiResult<?> deleteEmptyProject(@PathVariable UUID id, @CurrentUser User user);

    @PostMapping(value = CREATE_PATH)
    ApiResult<ProjectResponseDTO> createNewProject(@RequestBody @Valid CreateProjectDTO createProjectDTO,
                                                   @CurrentUser User user);

    @PutMapping(value = UPDATE_PATH)
    ApiResult<ProjectResponseDTO> updateProjectName(@RequestBody @Valid UpdateProjectDTO updateProjectDTO,
                                                    @CurrentUser User user);

    @GetMapping(value = MY_PROJECTS_PATH)
    ApiResult<List<ProjectDTO>> myProjects(@CurrentUser User user);


    @GetMapping(value = MY_PROJECT_BY_ID_PATH)
    ApiResult<ProjectDTO> myProjectById(@PathVariable UUID id, @CurrentUser User user) throws ParseException;

    @PostMapping(value = UPLOAD_CSV_PATH)
    ApiResult<ProjectResponseDTO> uploadCSV(@RequestParam("file") MultipartFile file, @CurrentUser User user);

}

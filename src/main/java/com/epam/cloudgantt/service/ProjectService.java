package com.epam.cloudgantt.service;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import com.epam.cloudgantt.payload.UpdateProjectDTO;

import java.util.UUID;

public interface ProjectService {

    ApiResult<ProjectResponseDTO> createNewProject(CreateProjectDTO createProjectDTO);

    ApiResult<ProjectResponseDTO> delete(UUID id);

    ApiResult<ProjectResponseDTO> updateProjectName(UpdateProjectDTO updateProjectDTO);
}

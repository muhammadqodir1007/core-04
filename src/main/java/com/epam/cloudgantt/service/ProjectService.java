package com.epam.cloudgantt.service;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.payload.ProjectResponseDTO;

import java.util.UUID;

public interface ProjectService {

      String createNewProject(CreateProjectDTO createProjectDTO);

    ApiResult<ProjectResponseDTO> delete(UUID id);
}

package com.epam.cloudgantt.service;

import com.epam.cloudgantt.payload.CreateProjectDTO;

public interface ProjectService {

      String createNewProject(CreateProjectDTO createProjectDTO);

    ApiResult<ProjectResponseDTO> delete(UUID id);
}

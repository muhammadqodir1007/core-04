package com.epam.cloudgantt.service;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.ProjectResponseDTO;

import java.util.UUID;

public interface ProjectService {
    ApiResult<ProjectResponseDTO> delete(UUID id);
}

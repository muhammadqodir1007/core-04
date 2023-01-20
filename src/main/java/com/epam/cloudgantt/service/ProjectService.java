package com.epam.cloudgantt.service;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.*;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    ApiResult<ProjectResponseDTO> createNewProject(CreateProjectDTO createProjectDTO, User user);

    ApiResult<ProjectResponseDTO> delete(UUID id);

    ApiResult<ProjectResponseDTO> updateProjectName(UpdateProjectDTO updateProjectDTO, User user);

    ApiResult<List<ProjectDTO>> myProjects(User user);
}

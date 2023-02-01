package com.epam.cloudgantt.service;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface ProjectService {

    ApiResult<ProjectResponseDTO> createNewProject(CreateProjectDTO createProjectDTO, User user);

    ApiResult<?> delete(UUID id, User user);

    ApiResult<ProjectResponseDTO> updateProjectName(UpdateProjectDTO updateProjectDTO, User user);

    ApiResult<List<ProjectDTO>> myProjects(User user);

    ApiResult<ProjectDTO> myProjectById(UUID id, User user) throws ParseException;

    ApiResult<String> uploadCSVFileToCreateProject(MultipartFile file, User user);
}

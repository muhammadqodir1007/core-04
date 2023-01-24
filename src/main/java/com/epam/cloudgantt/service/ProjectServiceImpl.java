package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.mapper.ProjectMapper;
import com.epam.cloudgantt.payload.*;
import com.epam.cloudgantt.repository.ProjectRepository;
import com.epam.cloudgantt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ApiResult<?> delete(UUID id) {
        if (!projectRepository.existsById(id))
            throw RestException.restThrow("Project does not exist.");
        projectRepository.deleteById(id);
        return ApiResult.successResponse("Project was successfully deleted.");
    }


    @Override
    public ApiResult<?> createNewProject(CreateProjectDTO createProjectDTO, User user) {

        if (Objects.isNull(createProjectDTO))
            throw RestException.restThrow(MessageByLang.getMessage("PARAMETER_MUST_NOT_BE_NULL"));

        Project project = projectMapper.mapCreateProjectDTOToProject(createProjectDTO);
        project.setName(createProjectDTO.getName());
        project.setUser(user);
        projectRepository.save(project);

        return ApiResult.successResponse("Project was successfully created");
    }

    @Override
    public ApiResult<ProjectResponseDTO> updateProjectName(UpdateProjectDTO updateProjectDTO, User user) {
        Project project = projectRepository.findById(updateProjectDTO.getId()).orElseThrow(() -> RestException.restThrow("Project does not exist."));

        if (!project.getUser().equals(user))
            throw RestException.restThrow("You are not allowed to rename.");

        project.setName(updateProjectDTO.getName());
        projectRepository.save(project);
        return ApiResult.successResponse(new ProjectResponseDTO("Project_Name was successfully edited."));
    }

    @Override
    public ApiResult<List<ProjectDTO>> myProjects(User user) {
        List<Project> projects = projectRepository.findAllByUser(user);
        return ApiResult.successResponse(projects.stream().map(this::mapProjectToProjectDTO).collect(Collectors.toList()));
    }

    @Override
    public ApiResult<ProjectDTO> myProjectById(UUID id, User user) {
        Project project = projectRepository.findById(id).orElseThrow(() -> RestException.restThrow("Project not found"));
        ProjectDTO projectDTO = mapProjectToProjectDTO(project);
        return ApiResult.successResponse(projectDTO);
    }

    private ProjectDTO mapProjectToProjectDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getName());
    }
}

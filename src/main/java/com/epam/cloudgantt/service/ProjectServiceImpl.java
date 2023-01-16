package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.mapper.ProjectMapper;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.AuthResDTO;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public String createNewProject(CreateProjectDTO createProjectDTO) {

        if (Objects.isNull(createProjectDTO))
            throw RestException.restThrow(MessageByLang.getMessage("NAME_MUST_NOT_BE_NULL"));

        Project project = projectMapper.mapCreateProjectDTOToProject(createProjectDTO);

        if (projectRepository.existsByName(createProjectDTO.getName())) {
            return "PROJECT_ALREADY_EXISTS";
        }
        project.setName(createProjectDTO.getName());
        projectRepository.save(project);

        return createProjectDTO.getName()+" has been successfully created!";
    }
}

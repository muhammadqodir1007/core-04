package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.mapper.ProjectMapper;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import com.epam.cloudgantt.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ApiResult<ProjectResponseDTO> delete(UUID id) {
        if (projectRepository.findById(id).isPresent()) {
            projectRepository.deleteById(id);
            return ApiResult.successResponse(new ProjectResponseDTO("Project was successfully deleted."));
        } else return ApiResult.errorResponseWithData(new ProjectResponseDTO("Project does not exist."));
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

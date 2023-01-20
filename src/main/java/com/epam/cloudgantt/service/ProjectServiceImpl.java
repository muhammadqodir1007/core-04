package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.mapper.ProjectMapper;
import com.epam.cloudgantt.mapper.UserMapper;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import com.epam.cloudgantt.payload.UpdateProjectDTO;
import com.epam.cloudgantt.repository.ProjectRepository;
import com.epam.cloudgantt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;

    @Override
    public ApiResult<ProjectResponseDTO> delete(UUID id) {
        if (projectRepository.findById(id).isPresent()) {
            projectRepository.deleteById(id);
            return ApiResult.successResponse(new ProjectResponseDTO("Project was successfully deleted."));
        } else return ApiResult.errorResponseWithData(new ProjectResponseDTO("Project does not exist."));
    }

    @Override
    public ApiResult<ProjectResponseDTO> updateProjectName(UpdateProjectDTO updateProjectDTO) {
        UUID projectId = updateProjectDTO.getId();
        Project project;
        Optional<Project> optionalProject= projectRepository.findById(projectId);
        if(optionalProject.isPresent()) {
            project= optionalProject.get();
            if (project.getUser().getId().equals(updateProjectDTO.getUserId())){
                project.setName(updateProjectDTO.getName());
            } else {
                return ApiResult.errorResponseWithData(new ProjectResponseDTO("You are not allowed to rename."));
            }
            projectRepository.save(project);
            return ApiResult.successResponse(new ProjectResponseDTO("Project_Name was successfully edited."));
        } else return ApiResult.errorResponseWithData(new ProjectResponseDTO("Project does not exist."));
    }


    @Override
    public ApiResult<ProjectResponseDTO> createNewProject(CreateProjectDTO createProjectDTO) {

        if (Objects.isNull(createProjectDTO)) {
            throw RestException.restThrow(MessageByLang.getMessage("PARAMETER_MUST_NOT_BE_NULL"));
        }

        Project project = projectMapper.mapCreateProjectDTOToProject(createProjectDTO);
        Optional<User> user =userRepository.findById(createProjectDTO.getUserId());

        project.setName(createProjectDTO.getName());
        if(user.isPresent()) {
        project.setUser(user.get());
        } else {
            return ApiResult.errorResponseWithData(new ProjectResponseDTO("User does not exist"));
        }
        projectRepository.save(project);

        return ApiResult.successResponse(new ProjectResponseDTO("Project was successfully created."));
    }


}

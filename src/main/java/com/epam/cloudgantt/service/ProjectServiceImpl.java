package com.epam.cloudgantt.service;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import com.epam.cloudgantt.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;


    @Override
    public ApiResult<ProjectResponseDTO> delete(UUID id) {
        if (projectRepository.findById(id).isPresent()) {
            projectRepository.deleteById(id);
            return ApiResult.successResponse(new ProjectResponseDTO("Project was successfully deleted."));
        } else return ApiResult.errorResponseWithData(new ProjectResponseDTO("Project does not exist."));
    }
}

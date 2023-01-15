package com.epam.cloudgantt.mapper;

import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectResponseDTO mapProjectToProjectResponseDTO(Project project);
}

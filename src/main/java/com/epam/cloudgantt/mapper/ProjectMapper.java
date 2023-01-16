package com.epam.cloudgantt.mapper;

import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    Project mapCreateProjectDTOToProject(CreateProjectDTO createProjectDTO);

    CreateProjectDTO mapProjectToCreateProjectDTO(Project project);
}


package com.epam.cloudgantt.service;

import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.mapper.ProjectMapper;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    static final String NAME = "Gantt";

    @InjectMocks
    ProjectServiceImpl projectService;

    Project project;

    CreateProjectDTO createProjectDTO;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        createProjectDTO = new CreateProjectDTO(NAME);
        project = new Project().setName(NAME);
    }

    @Test
    void createNewProject() {
        setUp();
        when(projectMapper.mapCreateProjectDTOToProject(createProjectDTO)).thenReturn(project);

        when(projectRepository.existsByName(NAME)).thenReturn(false);

        assertEquals("Gantt has been successfully created!", projectService.createNewProject(createProjectDTO));
    }
}
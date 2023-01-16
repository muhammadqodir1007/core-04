package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectControllerImplTest {

    static final String NAME = "1";

    @InjectMocks
    ProjectControllerImpl projectController;

    @Mock
    ProjectService projectService;

    CreateProjectDTO createProjectDTO;

    @BeforeEach
    void setUp() {
        createProjectDTO = new CreateProjectDTO(NAME);
    }
    @Test
    void createNewProjectTest() {
        when(projectController.createNewProject(createProjectDTO)).thenReturn("1 has been successfully created!");
        String response = projectController.createNewProject(createProjectDTO);
        assertEquals("1 has been successfully created!",response);
    }
}
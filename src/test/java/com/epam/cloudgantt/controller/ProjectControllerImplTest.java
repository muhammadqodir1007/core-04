package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import com.epam.cloudgantt.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectControllerImplTest {

    static final String NAME = "1";
    static final UUID USER_ID =UUID.fromString("3a8bba12-9750-11ed-a8fc-0242ac120002");

    @InjectMocks
    ProjectControllerImpl projectController;

    @Mock
    ProjectService projectService;

    CreateProjectDTO createProjectDTO;

    ProjectResponseDTO projectResponseDTO;

    @BeforeEach
    void setUp() {
        createProjectDTO = new CreateProjectDTO(NAME, USER_ID);
    }
    @Test
    void createNewProjectTest() {
//        ApiResult<ProjectResponseDTO>  response = projectController.createNewProject(createProjectDTO);
//        String message = response.getData().getMessage();
//        when(projectController.createNewProject(createProjectDTO)).thenReturn(response);
//        assertEquals("Project was successfully created.",message);
    }
}
package com.epam.cloudgantt.service;

import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import com.epam.cloudgantt.payload.UpdateProjectDTO;
import com.epam.cloudgantt.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProjectServiceImplTest {

    static final String NAME = "Gantt";
    static final String RENAME = "Renamed Gantt";

    static final String EMAIL = "test@gmail.com";

    static final String PASSWORD = "Test1234!";

    static final UUID PROJECT_ID = UUID.randomUUID();

    @InjectMocks
    ProjectServiceImpl projectService;

    @Mock
    Project project;

    @Mock
    ProjectRepository projectRepository;

    CreateProjectDTO createProjectDTO;

    UpdateProjectDTO updateProjectDTO;

    User user;

    @BeforeEach
    void setUp() {
        user = new User()
                .setEmail(EMAIL)
                .setPassword(PASSWORD);
        createProjectDTO = new CreateProjectDTO(NAME);
        project = new Project().setName(NAME)
                .setUser(user);
    }

    @Test
    void createNewProjectSuccessTest() {
        ApiResult<ProjectResponseDTO> response = projectService.createNewProject(createProjectDTO, user);
        assertEquals("Project was successfully created", response.getMessage());

    }

    @Test
    void createNewProjectExceptionTest() throws Exception {
        CreateProjectDTO createProjectDTO1 = new CreateProjectDTO();
        assertThrows(RestException.class, () -> projectService.createNewProject(null, user));
    }

    @Test
    void updateProjectNameSuccessTest() {
        updateProjectDTO = new UpdateProjectDTO(PROJECT_ID, RENAME);
        when(projectRepository.findById(updateProjectDTO.getId())).thenReturn(Optional.ofNullable(project));
        ApiResult<ProjectResponseDTO> result = projectService.updateProjectName(updateProjectDTO, user);
        assertEquals("Project_Name was successfully edited.", result.getMessage());
    }

    @Test
    void updateProjectNameExceptionTest() throws Exception {
        UpdateProjectDTO updateProjectDTO1 = new UpdateProjectDTO();
        assertThrows(RestException.class, () -> projectService.updateProjectName(updateProjectDTO1, user));
        updateProjectDTO = new UpdateProjectDTO(PROJECT_ID, RENAME);
        assertThrows(RestException.class, () -> projectService.updateProjectName(updateProjectDTO, user));
    }

    @Test
    void deleteProjectByIdSuccessTest() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.ofNullable(project));
        ApiResult<?> result = projectService.delete(PROJECT_ID, user);
        assertEquals("Project was successfully deleted.", result.getMessage());
    }

    @Test
    void deleteProjectByIdExceptionTest() throws Exception {
        assertThrows(RestException.class, () -> projectService.delete(PROJECT_ID, user));
    }

}
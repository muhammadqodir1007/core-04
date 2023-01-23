package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.CreateProjectDTO;
import com.epam.cloudgantt.repository.ProjectRepository;
import com.epam.cloudgantt.service.ProjectServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ContextConfiguration(classes = {ProjectController.class})
@ExtendWith(SpringExtension.class)
public class ProjectControllerImplTest {

    static final String NAME = "1";
    static final String EMAIL = "test@gmail.com";

    static final String PASSWORD = "Test1234!";

   @MockBean
    ProjectController projectController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    ProjectServiceImpl projectService;

    @MockBean
    MockMvc mockMvc;


    User user;

    @MockBean
    Project project;
    CreateProjectDTO createProjectDTO;

    @MockBean
    ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        createProjectDTO = new CreateProjectDTO(NAME);
        user = new User()
                .setEmail(EMAIL)
                .setPassword(PASSWORD);
    }
    @Test
    void createNewProjectTest() throws Exception {
        //given
//        ApiResult<ProjectResponseDTO> responseData = ApiResult.successResponse("Project was successfully created");
//        String content = objectMapper.writeValueAsString(responseData);
//        //when
//        when(projectService.createNewProject(createProjectDTO, user)).thenReturn(responseData);
//
//        //then
//        MockHttpServletRequestBuilder requestBuilder =
//                MockMvcRequestBuilders.post("/api/v1/project/create")
//                        .content(content);
//        MockMvcBuilders.standaloneSetup(projectController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.content().string(content));

    }
}
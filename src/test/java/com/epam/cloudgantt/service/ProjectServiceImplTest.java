package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.mapper.ProjectMapper;
import com.epam.cloudgantt.mapper.UserMapper;
import com.epam.cloudgantt.payload.*;
import com.epam.cloudgantt.repository.ProjectRepository;
import com.epam.cloudgantt.repository.UserRepository;
import com.epam.cloudgantt.security.JWTProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    static final String NAME = "Gantt";
    static final String EMAIL = "test@gmail.com";

    static final String PASSWORD = "Test1234!";
    static UUID USER_ID= UUID.randomUUID();

    @InjectMocks
    ProjectServiceImpl projectService;

    @InjectMocks
    AuthServiceImpl authService;

    Project project;

    CreateProjectDTO createProjectDTO;

    UpdateProjectDTO updateProjectDTO;

    SignUpDTO signUpDTO;

    User user;

    @Mock
    UserMapper userMapper;

    @Mock
    AuthServiceImplTest authServiceImplTest;
    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MailService mailService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTProvider jwtProvider;

    @BeforeEach
    void setUp() {
        signUpDTO = new SignUpDTO(EMAIL, PASSWORD, PASSWORD);
        user = new User()
                .setEmail(EMAIL)
                .setPassword(PASSWORD);
    }

    @Test
    void createNewProjectSuccessTest() {
//        setUp();
//
//        when(userMapper.mapSignUpDTOToUser(signUpDTO)).thenReturn(user);
//        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);
//        MockedStatic<MessageByLang> mocked = mockStatic(MessageByLang.class);
//        mocked.when(() -> MessageByLang.getMessage("OPEN_YOUR_EMAIL_TO_CONFORM_IT")).thenReturn("Success");

//        AuthResDTO data = authService.signUp(signUpDTO).getData();
//        when(userRepository.existsByEmail(EMAIL)).thenReturn(true);
//        assertEquals("Success", data.getMessage());
//        mocked.close();
//        USER_ID = userRepository.findByEmail(EMAIL).get().getId();
//        createProjectDTO = new CreateProjectDTO(NAME, USER_ID);
//
//        project = new Project().setName(NAME)
//                .setUser(userRepository.findById(USER_ID).get());
//        when(projectMapper.mapCreateProjectDTOToProject(createProjectDTO)).thenReturn(project);
//        when(projectRepository.existsByName(NAME)).thenReturn(false);
//        String response = projectService.createNewProject(createProjectDTO).getData().getMessage();
//        assertEquals("Project was successfully created.", response);
    }

    @Test
    void updateProjectNameSuccessTest() {
//        Optional<Project> project = projectRepository.findByName(createProjectDTO.getName());
//        UUID projectId = updateProjectDTO.getId();
        //when(projectRepository.findById());
    }
}
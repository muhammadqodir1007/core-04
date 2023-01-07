package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.mapper.UserMapper;
import com.epam.cloudgantt.payload.AuthResDTO;
import com.epam.cloudgantt.payload.SignInDTO;
import com.epam.cloudgantt.payload.SignUpDTO;
import com.epam.cloudgantt.repository.UserRepository;
import com.epam.cloudgantt.security.JWTProvider;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceImplTest {
    static final String EMAIL = "test@gmail.com";
    static final String PASSWORD = "Test1234!";

    SignUpDTO signUpDTO;
    User user;
    SignInDTO signInDTO;

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
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
        signInDTO = new SignInDTO()
                .setEmail(EMAIL)
                .setPassword(PASSWORD);
        user = new User()
                .setEmail(EMAIL)
                .setPassword(PASSWORD);
    }

    @Test
    public void signUpSuccessTest() {
        setUp();
        when(userMapper.mapSignUpDTOToUser(signUpDTO)).thenReturn(user);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);
        MockedStatic<MessageByLang> mocked = mockStatic(MessageByLang.class);
        mocked.when(() -> MessageByLang.getMessage("OPEN_YOUR_EMAIL_TO_CONFORM_IT")).thenReturn("Success");

        AuthResDTO data = authService.signUp(signUpDTO).getData();
        assertEquals("Success", data.getMessage());

        mocked.close();
    }



}
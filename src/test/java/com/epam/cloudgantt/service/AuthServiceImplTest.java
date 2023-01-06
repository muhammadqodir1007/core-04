package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.mapper.UserMapper;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.AuthResDTO;
import com.epam.cloudgantt.payload.SignInDTO;
import com.epam.cloudgantt.payload.SignUpDTO;
import com.epam.cloudgantt.repository.UserRepository;
import com.epam.cloudgantt.security.JWTProvider;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.MessageFormat;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceImplTest {
    static final String MESSAGE = "The confirmation link has been sent to your email address, you must open it and click on the confirmation link.";
    User user;
    SignInDTO signInDTO;
    AuthResDTO authResDTO;

    MessageByLang messageByLang;

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
        signInDTO = new SignInDTO()
                .setEmail("test@gmail.com")
                .setPassword("Test1234!");
        user = new User()
                .setEmail("test@gmail.com")
                .setPassword("Test1234!");


        MessageSource messageSource = new AbstractMessageSource() {
            protected MessageFormat resolveCode(String code, Locale locale) {
                return new MessageFormat("");
            }
        };

        messageByLang = new MessageByLang();
        messageByLang.setMessageSource(messageSource);
    }

//    @Test
//    public void signUpSuccessTest() {
//        when(userMapper.mapSignUpDTOToUser(signUpDTO)).thenReturn(user);
//        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);
//        when(MessageByLang.getMessage("OPEN_YOUR_EMAIL_TO_CONFORM_IT")).thenReturn("Success");
//
//        AuthResDTO data = authService.signUp(signUpDTO).getData();
//
//        assertEquals("Success", data.getMessage());
//    }

    @Test
    public void signInSuccessTest() {
        when(authenticationManager.authenticate(any())
        ).thenReturn(new UsernamePasswordAuthenticationToken(
                signInDTO.getEmail(),
                signInDTO.getPassword()));
//        Authentication authenticate = authenticationManager.authenticate(any());


    }

}
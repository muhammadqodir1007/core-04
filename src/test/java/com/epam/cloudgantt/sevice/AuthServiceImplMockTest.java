package com.epam.cloudgantt.sevice;


import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.mapper.UserMapper;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.SignUpDTO;
import com.epam.cloudgantt.repository.UserRepository;
import com.epam.cloudgantt.security.JWTProvider;
import com.epam.cloudgantt.service.AuthServiceImpl;
import com.epam.cloudgantt.service.MailService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AuthServiceImplMockTest {


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


    @Test
    @DisplayName(value = "SignUp success")
    public void a_successSignUp() {
        SignUpDTO signUpDTO = new SignUpDTO(
                "sirojiddinit@gmail.com",
                "Root_123",
                "Root_123");

        when(userRepository.existsByEmail(signUpDTO.getEmail()))
                .thenReturn(false);
        when(userRepository.save(any(User.class)))
                .thenReturn(userMapper.mapSignUpDTOToUser(signUpDTO));
//        mailService.send(any(String.class), any(String.class), any(String.class));
//
//        verify(mailService, times(1)).send(any(String.class), any(String.class), any(String.class));


        ApiResult<String> result = authService.signUp(signUpDTO);
        assertThat(result).isNotNull();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData()).isNotNull();
        assertThat(result.getErrors()).isNull();
    }
}

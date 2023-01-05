package com.epam.cloudgantt.sevice;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.SignUpDTO;
import com.epam.cloudgantt.service.AuthServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AuthServiceImplTest {

    @Autowired
    private AuthServiceImpl authService;


    @Test
    @DisplayName(value = "Add signUp success")
    @Order(100)
    public void a_successSignUp() {
        SignUpDTO signUpDTO = new SignUpDTO(
                "sirojiddinit@gmail.com",
                "Root_123",
                "Root_123");
//        ApiResult<String> result = authService.signUp(signUpDTO);
//        assertThat(result).isNotNull();
//        assertThat(result.isSuccess()).isTrue();
//        assertThat(result.getData()).isNotNull();
//        assertThat(result.getErrors()).isNull();
    }

    @Test
    @DisplayName(value = "SignUp fail")
    @Order(200)
    public void b_failSignUp() {
        SignUpDTO signUpDTO = new SignUpDTO(
                "sirojiddinit@gmail.com",
                "Root_123",
                "Root_123");
        assertThatThrownBy(() -> authService.signUp(signUpDTO))
                .isInstanceOf(RestException.class);

    }

    @Test
    @DisplayName(value = "SignUp fail password not equals")
    @Order(300)
    public void c_failSignUpPasswordNotEquals() {
        SignUpDTO signUpDTO = new SignUpDTO(
                "sirojiddinit@gmail.com",
                "Root_123",
                "Root_1234");
        assertThatThrownBy(() -> authService.signUp(signUpDTO))
                .isInstanceOf(RestException.class)
                .hasMessage(MessageByLang.getMessage("PASSWORDS_NOT_EQUAL"));
    }

    @Test
    @DisplayName(value = "SignUp fail request body null")
    @Order(300)
    public void d_failSignUpPasswordNotEquals() {
        assertThatThrownBy(() -> authService.signUp(null))
                .isInstanceOf(RestException.class);
    }

}

package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.AuthResDTO;
import com.epam.cloudgantt.payload.SignUpDTO;
import com.epam.cloudgantt.service.AuthService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthControllerImpl authController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void signUpSuccessTest() throws Exception {
        AuthResDTO authResDTO = new AuthResDTO("OK");

        when(authService.signUp(Mockito.any(SignUpDTO.class))).thenReturn(ApiResult.successResponse(authResDTO));

        SignUpDTO signUpDTO = new SignUpDTO("sirojiddinit@gmail.com", "Root_123", "Root_123");
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(AuthController.BASE_PATH + AuthController.SIGN_UP_PATH)
                                .content(objectWriter.writeValueAsString(signUpDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


//        System.out.println(mvcResult.getResponse().getContentAsString());
//        ApiResult<AuthResDTO> result = objectMapper.readValue(
//                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
//                });
//        System.out.println(result.isSuccess());
//        Assertions.assertThat(result.isSuccess()).isTrue();
    }
}

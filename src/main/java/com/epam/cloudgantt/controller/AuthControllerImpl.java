package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.SignUpDTO;
import com.epam.cloudgantt.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j

public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ApiResult<String> signUp(SignUpDTO signUpDTO) {
        log.info("signUp method entered: {}", signUpDTO);

        ApiResult<String> result = authService.signUp(signUpDTO);

        log.info("signUp method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<String> confirm(String verificationCode) {
        log.info("confirm method entered : ");
        return authService.confirmEmail(verificationCode);

    }


}

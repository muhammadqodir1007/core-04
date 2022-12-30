package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.payload.*;
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
    public ApiResult<?> signUp(SignUpDTO signUpDTO) {
        log.info("signUp method entered: {}", signUpDTO);

        ApiResult<?> result = authService.signUp(signUpDTO);

        log.info("signUp method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<TokenDTO> signIn(SignInDTO signInDTO) {
        log.info("signIn method entered: {}", signInDTO);

        ApiResult<TokenDTO> result = authService.signIn(signInDTO);

        log.info("signIn method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<String> confirm(String verificationCode) {
        log.info("confirm method entered : ");
        return authService.confirmEmail(verificationCode);
    }

    @Override
    public ApiResult<String> resendVerificationCode(String email) {
        log.info("Resend verification code method entered : {}", email);
        ApiResult<String> result = authService.resendVerificationCode(email);
        log.info("Resend verification code method exited : {}", result);
        return result;
    }
}
package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.payload.*;
import com.epam.cloudgantt.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@AllArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ApiResult<AuthResDTO> signUp(SignUpDTO signUpDTO) {
        log.info("signUp method entered: {}", signUpDTO);

        ApiResult<AuthResDTO> result = authService.signUp(signUpDTO);

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

    @Override
    public ApiResult<AuthResDTO> changePassword(ChangePasswordDTO changePasswordDTO) {
        log.info("Change password method entered : {}", changePasswordDTO);
        ApiResult<AuthResDTO> result = authService.changePassword(changePasswordDTO);
        log.info("Change password method exited : {}", changePasswordDTO);
        return result;
    }

    @Override
    public ApiResult<AuthResDTO> forgotPassword(String email) {
        log.info("Forgot password method entered : {}", email);
        ApiResult<AuthResDTO> result = authService.forgotPassword(email);
        log.info("Forgot password method exited : {}", result);
        return result;
    }

    @Override
    public ApiResult<AuthResDTO> resetForgottenPassword(ResetForgottenPasswordDTO resetForgottenPasswordDTO) {
        log.info("Reset forgotten password method entered.");
        ApiResult<AuthResDTO> result = authService.resetForgottenPassword(resetForgottenPasswordDTO);
        log.info("Reset forgotten password method exited.");
        return result;
    }

    @Override
    public void exportCSV(HttpServletResponse response) throws IOException {

    }

}

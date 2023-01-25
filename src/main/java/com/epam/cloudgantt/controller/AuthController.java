package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.payload.*;
import com.epam.cloudgantt.util.AppConstants;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping(value = AuthController.BASE_PATH)
public interface AuthController {

    String BASE_PATH = AppConstants.BASE_PATH + "auth/";
    String SIGN_UP_PATH = "sign-up";
    String SIGN_IN_PATH = "sign-in";
    String RESEND_VERIFY_ACCOUNT_CODE_PATH = "resend-verification-code";
    String FORGOT_PASSWORD_PATH = "forgot-password";
    String RESET_PASSWORD_PATH = "reset-password";
    String EXPORT_TEMPLATE="export-template";

    String RESET_FORGOTTEN_PASSWORD_PATH = "reset-forgotten-password";

    @PostMapping(value = SIGN_UP_PATH)
    ApiResult<AuthResDTO> signUp(@Valid @RequestBody SignUpDTO signUpDTO);


    @PostMapping(value = SIGN_IN_PATH)
    ApiResult<TokenDTO> signIn(@Valid @RequestBody SignInDTO signInDTO);




    @GetMapping("confirm-email/{verificationCode}")
    ApiResult<String> confirm(@PathVariable String verificationCode);

    @GetMapping(RESEND_VERIFY_ACCOUNT_CODE_PATH)
    ApiResult<String> resendVerificationCode(@RequestParam String email);

    @PostMapping(value = RESET_PASSWORD_PATH)
    ApiResult<AuthResDTO> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO);

    @GetMapping(value = FORGOT_PASSWORD_PATH)
    ApiResult<AuthResDTO> forgotPassword(@RequestParam String email);

    @PostMapping(value = RESET_FORGOTTEN_PASSWORD_PATH)
    ApiResult<AuthResDTO> resetForgottenPassword(@Valid @RequestBody ResetForgottenPasswordDTO resetForgottenPasswordDTO);

    @GetMapping(value = EXPORT_TEMPLATE)
    void exportCSV(HttpServletResponse response) throws IOException;
}

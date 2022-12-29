package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.SignInDTO;
import com.epam.cloudgantt.payload.SignUpDTO;
import com.epam.cloudgantt.payload.TokenDTO;
import com.epam.cloudgantt.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = AuthController.BASE_PATH)
public interface AuthController {

    String BASE_PATH = AppConstants.BASE_PATH + "auth";
    String SIGN_UP_PATH = "sign-up";
    String SIGN_IN_PATH = "sign-in";
    String FORGOT_PASSWORD_PATH = "forgot-password";
    String RESET_PASSWORD_PATH = "reset-password";

    @PostMapping(value = SIGN_UP_PATH)
    ApiResult<String> signUp(@Valid @RequestBody SignUpDTO signUpDTO);


    @PostMapping(value = SIGN_IN_PATH)
    ApiResult<TokenDTO> signIn(@Valid @RequestBody SignInDTO signInDTO);


    @GetMapping("confirm-email/{verificationCode}")
    ApiResult<String> confirm(@PathVariable String verificationCode);

}

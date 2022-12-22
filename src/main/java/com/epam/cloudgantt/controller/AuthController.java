package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.util.AppConstants;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = AuthController.BASE_PATH)
public interface AuthController {


    String BASE_PATH = AppConstants.BASE_PATH +"auth";
    String SIGN_UP_PATH = "sign-up";
    String SIGN_IN_PATH = "sign-in";
    String FORGOT_PASSWORD_PATH = "forgot-password";
    String RESET_PASSWORD_PATH = "reset-password";
}

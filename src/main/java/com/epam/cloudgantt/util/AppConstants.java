package com.epam.cloudgantt.util;

import com.epam.cloudgantt.controller.AuthController;

public interface AppConstants {
    String BASE_PATH = "/api/v1/";


    String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8,256}$";

    String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    String[] OPEN_PAGES = {
            AuthController.BASE_PATH + "/**",
    };
}

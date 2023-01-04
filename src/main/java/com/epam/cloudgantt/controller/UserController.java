package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.UserDTO;
import com.epam.cloudgantt.security.CurrentUser;
import com.epam.cloudgantt.util.AppConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = UserController.BASE_PATH)
public interface UserController {
    String BASE_PATH = AppConstants.BASE_PATH + "user";
    String USER_ME_PATH = "/me";

    @GetMapping(value = USER_ME_PATH)
    ApiResult<UserDTO> getUserMe(@CurrentUser User user);
}

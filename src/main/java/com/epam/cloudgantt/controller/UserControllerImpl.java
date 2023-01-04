package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.UserDTO;
import com.epam.cloudgantt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ApiResult<UserDTO> getUserMe(User user) {
        return userService.getUserMe(user);
    }
}

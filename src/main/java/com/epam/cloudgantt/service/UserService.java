package com.epam.cloudgantt.service;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.UserDTO;

public interface UserService {


    ApiResult<UserDTO> getUserMe(User user);
}

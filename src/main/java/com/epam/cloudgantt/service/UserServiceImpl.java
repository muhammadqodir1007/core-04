package com.epam.cloudgantt.service;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.mapper.UserMapper;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.UserDTO;
import com.epam.cloudgantt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    @Override
    public ApiResult<UserDTO> getUserMe(User user) {
        UserDTO userDTO = userMapper.mapUserToUserDTO(user);
        return ApiResult.successResponse(userDTO);
    }
}

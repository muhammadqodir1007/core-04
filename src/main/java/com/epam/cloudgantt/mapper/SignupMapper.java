package com.epam.cloudgantt.mapper;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.SignUpDTO;
import org.springframework.stereotype.Component;


@Component
public class SignupMapper {

    public User mapToEntity(SignUpDTO signUpDTO) {
        User user = new User();
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(signUpDTO.getPassword());
        return user;
    }

}

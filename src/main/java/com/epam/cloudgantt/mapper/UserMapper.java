package com.epam.cloudgantt.mapper;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.SignUpDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapSignUpDTOToUser(SignUpDTO signUpDTO);
}

package com.epam.cloudgantt.mapper;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.SignUpDTO;
import com.epam.cloudgantt.payload.UserDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    User mapSignUpDTOToUser(SignUpDTO signUpDTO);

    UserDTO mapUserToUserDTO(User user);
}

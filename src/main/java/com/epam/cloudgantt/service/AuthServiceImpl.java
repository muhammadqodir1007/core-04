package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.SignUpDTO;
import com.epam.cloudgantt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public ApiResult<String> signUp(SignUpDTO signUpDTO) {

        if (!Objects.equals(signUpDTO.getPassword(), signUpDTO.getPrePassword()))
            throw RestException.restThrow(MessageByLang.getMessage("PASSWORDS_NOT_EQUAL"));

        if (userRepository.existsByEmail(signUpDTO.getEmail()))
            throw RestException.restThrow(MessageByLang.getMessage("EMAIL_ALREADY_EXISTS"));

        if (signUpDTO.getPassword().isEmpty())
            throw RestException.restThrow(MessageByLang.getMessage("PASSWORD_MUST_BE_NOT_NULL"));

        if (!signUpDTO.getPassword().matches("^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8,256}$"))
            throw RestException.restThrow(MessageByLang.getMessage("PASSWORD_REGEX_MSG"));

        if (signUpDTO.getEmail().isEmpty())
            throw RestException.restThrow(MessageByLang.getMessage("EMAIL_MUST_BE_NOT_NULL"));

        if (!signUpDTO.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@((?!epam\\.com)[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))
            throw RestException.restThrow(MessageByLang.getMessage("EMAIL_MUST_BE_VALID"));
        //check this case
        if (signUpDTO.getEmail().endsWith("epam.com"))
            throw RestException.restThrow(MessageByLang.getMessage("EMAIL_MUST_BE_VALID_OUR_PATTERN"));

        return null;
    }
}

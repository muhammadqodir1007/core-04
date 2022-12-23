package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.SignUpDTO;
import com.epam.cloudgantt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Locale;
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



        return null;
    }
}

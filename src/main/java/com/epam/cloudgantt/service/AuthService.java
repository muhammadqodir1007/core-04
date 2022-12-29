package com.epam.cloudgantt.service;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.SignInDTO;
import com.epam.cloudgantt.payload.SignUpDTO;
import com.epam.cloudgantt.payload.TokenDTO;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    ApiResult<String> signUp(SignUpDTO signUpDTO);

    ApiResult<TokenDTO> signIn(SignInDTO signInDTO);

    @Transactional
    ApiResult<String> confirmEmail(String code);
}

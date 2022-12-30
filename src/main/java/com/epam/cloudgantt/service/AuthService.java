package com.epam.cloudgantt.service;

import com.epam.cloudgantt.payload.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    ApiResult<?> signUp(SignUpDTO signUpDTO);

    ApiResult<TokenDTO> signIn(SignInDTO signInDTO);

    @Transactional
    ApiResult<String> confirmEmail(String code);

    ApiResult<String> resendVerificationCode(String email);
}

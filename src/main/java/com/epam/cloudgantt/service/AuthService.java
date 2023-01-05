package com.epam.cloudgantt.service;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface AuthService extends UserDetailsService {

    ApiResult<AuthResDTO> signUp(SignUpDTO signUpDTO);

    ApiResult<TokenDTO> signIn(SignInDTO signInDTO);

    @Transactional
    ApiResult<String> confirmEmail(String code);

    ApiResult<String> resendVerificationCode(String email);
    ApiResult<AuthResDTO> changePassword(ChangePasswordDTO changePasswordDTO);

    User getUserById(UUID id);

    ApiResult<AuthResDTO> forgotPassword(String email);
}

package com.epam.cloudgantt.service;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.SignUpDTO;
import jakarta.transaction.Transactional;

public interface AuthService {


    ApiResult<String> signUp(SignUpDTO signUpDTO);

    @Transactional
    ApiResult<String> confirmEmail(String code);

}

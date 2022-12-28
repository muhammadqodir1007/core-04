package com.epam.cloudgantt.service;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.SignUpDTO;

public interface AuthService {


    ApiResult<String> signUp(SignUpDTO signUpDTO);

    ApiResult<String> confirmEmail(String string,String email);

}

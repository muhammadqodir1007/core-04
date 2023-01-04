package com.epam.cloudgantt.security;

import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.ErrorData;
import com.epam.cloudgantt.util.AppConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class JWTAuthProvider implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        Gson gson = new Gson();
        ApiResult<ErrorData> errorDataApiResult = ApiResult.errorResponse("Forbidden", 403);
        response.getWriter().write(gson.toJson(errorDataApiResult));
        response.setStatus(403);
        response.setContentType("application/json");

    }
}

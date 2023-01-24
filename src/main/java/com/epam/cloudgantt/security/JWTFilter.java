package com.epam.cloudgantt.security;


import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.service.AuthService;
import com.epam.cloudgantt.util.AppConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;
    private final AuthService authService;

    private static final String ALLOWED_HEADERS = "Content-Type, Content-Length, Authorization, withCredentials, credential, credentials, X-XSRF-TOKEN";
    private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS, PATCH";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String MAX_AGE = "7200"; //2 hours (2 * 60 * 60)


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {


//        if (request.getMethod().equals("OPTIONS")) {
//            response.setHeader("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
//            response.setHeader("Access-Control-Allow-Methods", ALLOWED_METHODS);
//            response.setHeader("Access-Control-Max-Age", MAX_AGE); //OPTION how long the results of a preflight request (that is the information contained in the Access-Control-Allow-Methods and Access-Control-Allow-Headers headers) can be cached.
//            response.setHeader("Access-Control-Allow-Headers", ALLOWED_HEADERS);
//            return;
//        }
            try {
                setSecurityContext(request);
            } catch (Exception ignored) {

            }
//        response.setCharacterEncoding("UTF-8");
        filterChain.doFilter(request, response);
    }

    private String getAuthHeader(HttpServletRequest request) {
        String headerAuth = request.getHeader(AppConstants.AUTH_HEADER);
        if (Objects.isNull(headerAuth) || !headerAuth.startsWith(AppConstants.TOKEN_TYPE))
            throw RestException.restThrow("Incorrect token");

        return headerAuth.substring(7);
    }

    private User getUserByAuthHeader(HttpServletRequest httpServletRequest) {
        String authHeader = getAuthHeader(httpServletRequest);
        if (jwtProvider.validateJwtToken(authHeader))
            return authService.getUserById(UUID.fromString(jwtProvider.getUserUUIDFromJwtToken(authHeader)));
        throw RestException.restThrow(MessageByLang.getMessage("AUTH_HEADER_NOT_GIVEN"));
    }

    private void setSecurityContext(HttpServletRequest request) {

        User user = getUserByAuthHeader(request);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}

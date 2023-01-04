package com.epam.cloudgantt.security.filter;


import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.ErrorData;
import com.epam.cloudgantt.repository.UserRepository;
import com.epam.cloudgantt.security.JWTProvider;
import com.epam.cloudgantt.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class AuthenticateTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String jwt = jwtProvider.parseJwt(request);
            if (jwt == null) {
                log.info("can not find  header");
            }else log.info("it exists");

            if (jwt != null && jwtProvider.validateJwtToken(jwt)) {

                String userUUID = jwtProvider.getUserUUIDFromJwtToken(jwt);
                log.info("username is " + userUUID);

                Optional<User> user = userRepository.findById(UUID.fromString(userUUID));

                UserDetails userDetails = authService.loadUserByUsername(user.get().getEmail());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (JwtException | IllegalArgumentException exception) {

            ErrorData errorData = new ErrorData(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            OutputStream out = response.getOutputStream();
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, errorData);
            out.flush();
        }
    }


}

package com.epam.cloudgantt.security;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.exceptions.RestException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jdk.jfr.consumer.RecordedStackTrace;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTProvider {
    @Value("${app.jwt.access.key}")
    private String accessTokenKey;

    @Value("${app.jwt.access.expiredAt}")
    private Long accessTokenExpired;

    @Value("${app.jwt.refresh.key}")
    private String refreshTokenKey;

    @Value("${app.jwt.refresh.expiredAt}")
    private Long refreshTokenExpired;

    public String generateAccessToken(User user) {
        if (user == null)
            throw RestException.restThrow("User must not be null");

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, accessTokenKey)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpired))
                .compact();
    }

    public String generateRefreshToken(User user) {
        if (user == null)
            throw RestException.restThrow("User must not be null");

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, refreshTokenKey)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpired))
                .compact();
    }



}

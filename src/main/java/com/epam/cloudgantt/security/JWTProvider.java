package com.epam.cloudgantt.security;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.exceptions.RestException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@Slf4j
public class JWTProvider {

    @Value("${jwt.secret}")
    private String secret;

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


    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (!StringUtils.hasText(headerAuth) && !headerAuth.startsWith("Bearer ")) {
            RestException.restThrow("Incorrect token");
        }
        return headerAuth.substring(7, headerAuth.length());
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(accessTokenKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: ", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: ", e.getMessage());
            throw e;
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: ", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: ", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: ", e.getMessage());
            throw e;
        }
    }


    public String getUserUUIDFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(accessTokenKey).parseClaimsJws(token).getBody().getSubject();
    }
}

package com.epam.cloudgantt.config;

import com.epam.cloudgantt.util.AppConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers(AppConstants.OPEN_PAGES).permitAll()
                                        .anyRequest().authenticated()

                );

        return http.build();
    }

}

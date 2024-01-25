package com.epam.cloudgantt.config;
import com.epam.cloudgantt.security.JWTAuthProvider;
import com.epam.cloudgantt.security.JWTFilter;
import com.epam.cloudgantt.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTFilter jwtFilter;
    private final JWTAuthProvider jwtAuthProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(AppConstants.OPEN_PAGES)
                                .permitAll()
                                .requestMatchers("/api/v1/upload")
                                .permitAll()
                                .requestMatchers("/*",
                                        "/robots.txt",
                                        "/sitemap.xml",
                                        "/favicon.ico",
                                        "/*/*.png",
                                        "/*/*.gif",
                                        "/*/*.svg",
                                        "/*/*.jpg",
                                        "/*/*.html",
                                        "/*/*.css",
                                        "/*/*.js",
                                        "/swagger-ui.html",
                                        "/swagger-resources/**",
                                        "/v2/**",
                                        "/csrf",
                                        "/webjars/*")
                                .permitAll()
                                .requestMatchers("/api/**")
                                .authenticated()
                )
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthProvider)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}

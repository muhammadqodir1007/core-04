package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.mapper.UserMapper;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.SignInDTO;
import com.epam.cloudgantt.payload.SignUpDTO;
import com.epam.cloudgantt.payload.TokenDTO;
import com.epam.cloudgantt.repository.UserRepository;
import com.epam.cloudgantt.security.JWTProvider;
import com.epam.cloudgantt.util.AppConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    @Value("${spring.mail.confirmLinkIPAndPort}")
    private String confirmLinkIPAndPort;

    public AuthServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           @Lazy PasswordEncoder passwordEncoder,
                           MailService mailService,
                           @Lazy AuthenticationManager authenticationManager,
                           JWTProvider jwtProvider) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found by email: " + email));
    }

    @Override
    public ApiResult<String> signUp(SignUpDTO signUpDTO) {
        if (Objects.isNull(signUpDTO))
            throw RestException.restThrow(MessageByLang.getMessage("REQUEST_DATA_BE_NOT_NULL"));

        if (!Objects.equals(signUpDTO.getPassword(), signUpDTO.getPrePassword()))
            throw RestException.restThrow(MessageByLang.getMessage("PASSWORDS_NOT_EQUAL"));

        if (userRepository.existsByEmail(signUpDTO.getEmail()))
            throw RestException.restThrow(MessageByLang.getMessage("EMAIL_ALREADY_EXISTS"));

        User user = userMapper.mapSignUpDTOToUser(signUpDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String verificationCode = String.valueOf(UUID.randomUUID());
        user.setVerificationCode(verificationCode);
        userRepository.save(user);
        sendVerificationCodeToEmail(user.getEmail(), verificationCode);
        return ApiResult.successResponse(MessageByLang.getMessage("OPEN_YOUR_EMAIL_TO_CONFORM_IT"));
    }

    @Override
    public ApiResult<TokenDTO> signIn(SignInDTO signInDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDTO.getEmail(),
                        signInDTO.getPassword()));


        User user = (User) authentication.getPrincipal();
        System.out.println(user);

        TokenDTO tokenDTO = TokenDTO.builder()
                .accessToken(jwtProvider.generateAccessToken(user))
                .refreshToken(jwtProvider.generateRefreshToken(user))
                .tokenType(AppConstants.TOKEN_TYPE)
                .build();


        return ApiResult.successResponse(tokenDTO);
    }

    @Override
    public ApiResult<String> confirmEmail(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode).orElseThrow(() -> RestException.restThrow(MessageByLang.getMessage("INVALID_VERIFICATION_CODE")));
        user.setEnabled(true);
        user.setVerificationCode(null);
        userRepository.save(user);

        return ApiResult.successResponse(MessageByLang.getMessage("USER_SUCCESSFULLY_ENABLED"));
    }


    /**
     * SEND TO EMAIL MESSAGE ABOUT VERIFICATION ACCOUNT
     *
     * @param email            String
     * @param verificationCode String
     */
    private void sendVerificationCodeToEmail(String email, String verificationCode) {
        if (email == null)
            throw RestException.restThrow("Email must not be null");

        if (verificationCode == null)
            throw RestException.restThrow("Verification code must not be null");

        String subject = MessageByLang.getMessage("HERE_IS_YOUR_VERIFICATION_CODE");
        String confirmLink = confirmLinkIPAndPort  + verificationCode;
        String mailText = MessageByLang.getMessage("PLEASE_CLICK_ON_THIS_LINK_TO_CONFORM_YOUR_EMAIL") + "\n" + confirmLink;
        mailService.send(email, subject, mailText);
    }


    public int add(int a, int b){
        return a+b;
    }
}

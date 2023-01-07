package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.mapper.UserMapper;
import com.epam.cloudgantt.payload.*;
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
import java.util.Optional;
import java.util.UUID;

import static com.epam.cloudgantt.util.AppConstants.EMAIL_REGEX;
import static com.epam.cloudgantt.util.AppConstants.PASSWORD_REGEX;

@Service

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    @Value("${spring.mail.conformEmailForResetForgottenPasswordURL}")
    private String conformEmailForResetForgottenPasswordURL;

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
    public ApiResult<AuthResDTO> signUp(SignUpDTO signUpDTO) {
        if (Objects.isNull(signUpDTO))
            throw RestException.restThrow(MessageByLang.getMessage("REQUEST_DATA_BE_NOT_NULL"));

        if (!Objects.equals(signUpDTO.getPassword(), signUpDTO.getPrePassword()))
            return ApiResult.errorResponseWithData(new AuthResDTO(false, MessageByLang.getMessage("PASSWORDS_NOT_EQUAL")));

        if (!signUpDTO.getPassword().matches(PASSWORD_REGEX))
            return ApiResult.errorResponseWithData(
                    new AuthResDTO(false, MessageByLang.getMessage("PASSWORD_REGEX_MSG")));

        if (!signUpDTO.getEmail().matches(EMAIL_REGEX))
            return ApiResult.errorResponseWithData(new AuthResDTO(true,
                    MessageByLang.getMessage("EMAIL_MUST_BE_VALID_OUR_PATTERN")));

        if (userRepository.existsByEmail(signUpDTO.getEmail()))
            return ApiResult.errorResponseWithData(
                    new AuthResDTO(
                            true,
                            MessageByLang.getMessage("EMAIL_ALREADY_EXISTS")));

        User user = userMapper.mapSignUpDTOToUser(signUpDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String verificationCode = String.valueOf(UUID.randomUUID());
        user.setVerificationCode(verificationCode);
        userRepository.save(user);


        String conformEmailForSignUpURL = "api/v1/auth/confirm-email/";
        String confirmLinkIPAndPort = "http://localhost:3000/";
        mailService.sendEmailForSignUpConfirmation(user.getEmail(), MessageByLang.getMessage("SUBJECT"), verificationCode);
        return ApiResult.successResponse(new AuthResDTO(MessageByLang.getMessage("OPEN_YOUR_EMAIL_TO_CONFORM_IT")));
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

    @Override
    public ApiResult<String> resendVerificationCode(String email) {
        if (Objects.isNull(email))
            throw RestException.restThrow(MessageByLang.getMessage("EMAIL_MUST_BE_NOT_NULL"));

        User user = userRepository.findByEmail(email).orElseThrow(() -> RestException.restThrow("User not registered"));
        String verificationCode = String.valueOf(UUID.randomUUID());
        user.setVerificationCode(verificationCode);
        userRepository.save(user);
        return ApiResult.successResponse("Successfully send new verification code");
    }


    @Override
    public ApiResult<AuthResDTO> changePassword(ChangePasswordDTO changePasswordDTO) {

        if (!changePasswordDTO.getPassword().matches(PASSWORD_REGEX))
            return ApiResult.errorResponseWithData(
                    new AuthResDTO(false, MessageByLang.getMessage("PASSWORD_REGEX_MSG")));

        if (!Objects.equals(changePasswordDTO.getPassword(), changePasswordDTO.getPrePassword()))
            return ApiResult.errorResponseWithData(
                    new AuthResDTO(false, MessageByLang.getMessage("PASSWORDS_NOT_EQUAL")));


        Optional<User> optionalUser = userRepository.findByEmail(changePasswordDTO.getEmail());

        if (optionalUser.isEmpty())
            return ApiResult.errorResponseWithData(new AuthResDTO(true, MessageByLang.getMessage("USER_NOT_REGISTERED")));


        User user = optionalUser.get();
        if (!Objects.equals(user.getVerificationCode(), changePasswordDTO.getVerificationCode()))
            return ApiResult.errorResponseWithData(AuthResDTO.wrongVerificationCode(MessageByLang.getMessage("INVALID_VERIFICATION_CODE")));

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
        user.setVerificationCode(null);
        userRepository.save(user);
        return ApiResult.successResponse(new AuthResDTO(MessageByLang.getMessage("PASSWORD_SUCCESSFULLY_CHANGED")));
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageByLang.getMessage("USER_NOT_FOUND")));
    }

    @Override
    public ApiResult<AuthResDTO> forgotPassword(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            return ApiResult.errorResponseWithData(new AuthResDTO(true, MessageByLang.getMessage("USER_NOT_REGISTERED")));

        User user = optionalUser.get();
        String verificationCode = UUID.randomUUID().toString();
        user.setVerificationCode(verificationCode);
        userRepository.save(user);

        mailService.sendEmailForForForgotPassword(email, verificationCode, conformEmailForResetForgottenPasswordURL);

        return ApiResult.successResponse(new AuthResDTO(MessageByLang.getMessage("SUCCESSFULLY_SEND_CODE_TO_EMAIL")));
    }

    @Override
    public ApiResult<AuthResDTO> resetForgottenPassword(ResetForgottenPasswordDTO resetForgottenPasswordDTO) {
        if (Objects.isNull(resetForgottenPasswordDTO))
            throw RestException.restThrow(MessageByLang.getMessage("REQUEST_DATA_BE_NOT_NULL"));

        if (!Objects.equals(resetForgottenPasswordDTO.getPassword(), resetForgottenPasswordDTO.getPrePassword()))
            return ApiResult.errorResponseWithData(new AuthResDTO(false, MessageByLang.getMessage("PASSWORDS_NOT_EQUAL")));

        if (!resetForgottenPasswordDTO.getPassword().matches(PASSWORD_REGEX))
            return ApiResult.errorResponseWithData(
                    new AuthResDTO(false, MessageByLang.getMessage("PASSWORD_REGEX_MSG")));

        Optional<User> userOptional = userRepository.findByVerificationCode(resetForgottenPasswordDTO.getVerificationCode());
        if (userOptional.isEmpty())
            return ApiResult.errorResponseWithData(AuthResDTO.wrongVerificationCode(MessageByLang.getMessage("INVALID_VERIFICATION_CODE")));

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(resetForgottenPasswordDTO.getPassword()));
        user.setVerificationCode(null);
        userRepository.save(user);
        return ApiResult.successResponse(new AuthResDTO(MessageByLang.getMessage("PASSWORD_SUCCESSFULLY_CHANGED")));
    }


    /**
     * SEND TO EMAIL MESSAGE ABOUT VERIFICATION ACCOUNT
     *
     * @param email            String
     * @param verificationCode String
     */


}

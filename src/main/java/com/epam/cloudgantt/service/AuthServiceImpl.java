package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.mapper.SignupMapper;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.SignUpDTO;
import com.epam.cloudgantt.repository.UserRepository;
import com.epam.cloudgantt.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final SignupMapper signupMapper;

    private final PasswordEncoder encoder;

    private final MailService mailService;

    @Value("${spring.mail.confirmLinkIPAndPort}")
    private String confirmLinkIPAndPort;


    @Override
    public ApiResult<String> signUp(SignUpDTO signUpDTO) {
        if (!Objects.equals(signUpDTO.getPassword(), signUpDTO.getPrePassword()))
            throw RestException.restThrow(MessageByLang.getMessage("PASSWORDS_NOT_EQUAL"));
        if (userRepository.existsByEmail(signUpDTO.getEmail()))
            throw RestException.restThrow(MessageByLang.getMessage("EMAIL_ALREADY_EXISTS"));

        User user = signupMapper.mapToEntity(signUpDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        String verificationCode = String.valueOf(UUID.randomUUID());
        user.setVerificationCode(verificationCode);
        userRepository.save(user);
        sendVerificationCodeToEmail(user.getEmail(), verificationCode);
        return ApiResult.successResponse(MessageByLang.getMessage("OPEN_YOUR_EMAIL_TO_CONFORM_IT"));
    }


    private void sendVerificationCodeToEmail(String email, String code) {
        String subject = MessageByLang.getMessage("HERE_IS_YOUR_VERIFICATION_CODE");
        String confirmLink = confirmLinkIPAndPort + AppConstants.BASE_PATH + "auth/" + "confirm-email/" + code;
        String mailText = MessageByLang.getMessage("PLEASE_CLICK_ON_THIS_LINK_TO_CONFORM_YOUR_EMAIL") + "\n" + confirmLink;
        mailService.send(email, subject, mailText);
    }

}

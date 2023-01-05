package com.epam.cloudgantt.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ResetForgottenPasswordDTO {

    @NotBlank(message = "{VERIFICATION_CODE_MUST_BE_NOT_NULL}")
    private String verificationCode;

    @NotBlank(message = "{PASSWORD_MUST_BE_NOT_NULL}")
    private String password;

    @NotBlank(message = "{PASSWORD_MUST_BE_NOT_NULL}")
    private String prePassword;

}

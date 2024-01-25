package com.epam.cloudgantt.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {

    @NotBlank(message = "{EMAIL_MUST_BE_NOT_NULL}")
    @Email(message = "{EMAIL_MUST_BE_VALID}")
//    @Pattern(message = "{EMAIL_MUST_BE_VALID_OUR_PATTERN}", regexp = AppConstants.EMAIL_REGEX)
    private String email;

    @NotBlank(message = "{PASSWORD_MUST_BE_NOT_NULL}")
//    @Pattern(message = "{PASSWORD_REGEX_MSG}", regexp = AppConstants.PASSWORD_REGEX)
    private String password;

    @NotBlank(message = "{PASSWORD_MUST_BE_NOT_NULL}")
//    @Pattern(message = "{PASSWORD_REGEX_MSG}", regexp = AppConstants.PASSWORD_REGEX)
    private String prePassword;


    @Override
    public String toString() {
        return "SignUpDTO{" +
                "email='" + email + '\'' +
                '}';
    }
}


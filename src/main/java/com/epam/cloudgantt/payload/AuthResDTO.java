package com.epam.cloudgantt.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResDTO {

    private boolean errorEmail;

    private boolean errorPassword;

    private boolean errorVerificationCode;

    private String message;

    public AuthResDTO(boolean errorEmail, String message) {
        if (errorEmail)
            this.errorEmail = true;
        else
            this.errorPassword = true;

        this.message = message;
    }

    public AuthResDTO(String message) {
        this.message = message;
    }

    private AuthResDTO() {
        this.errorVerificationCode = true;
    }


    public static AuthResDTO wrongVerificationCode(String message){
        AuthResDTO authResDTO = new AuthResDTO();
        authResDTO.setMessage(message);
        return authResDTO;
    }
}

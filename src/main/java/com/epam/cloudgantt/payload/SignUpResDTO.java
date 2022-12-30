package com.epam.cloudgantt.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpResDTO {

    private boolean errorEmail;

    private boolean errorPassword;

    private String message;
}

package com.epam.cloudgantt.payload;

import com.epam.cloudgantt.util.AppConstants;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDTO {

    private String accessToken;

    private String refreshToken;

    private String tokenType;
}

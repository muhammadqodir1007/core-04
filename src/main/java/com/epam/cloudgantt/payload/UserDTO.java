package com.epam.cloudgantt.payload;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserDTO {

    private UUID id;

    private String email;
}

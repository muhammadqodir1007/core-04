package com.epam.cloudgantt.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponseDTO {
    private String message;

    public ProjectResponseDTO(String message) {
        this.message = message;
    }
}

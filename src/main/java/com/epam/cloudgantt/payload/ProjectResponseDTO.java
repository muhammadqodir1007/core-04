package com.epam.cloudgantt.payload;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.service.UnknownUnwrapTypeException;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProjectResponseDTO {

    private List<String> message;

    public ProjectResponseDTO(List<String> message) {
        this.message = message;
    }
}

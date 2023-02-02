package com.epam.cloudgantt.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.service.UnknownUnwrapTypeException;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponseDTO {

    private UUID id;

    private List<String> message;
}

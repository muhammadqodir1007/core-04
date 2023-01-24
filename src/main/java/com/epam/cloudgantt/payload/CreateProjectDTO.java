package com.epam.cloudgantt.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectDTO {

    @NotBlank(message = "{NAME_MUST_BE_NOT_BLANK}")
    private String name;

}

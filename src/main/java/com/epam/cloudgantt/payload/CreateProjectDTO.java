package com.epam.cloudgantt.payload;

import com.epam.cloudgantt.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectDTO {

    @NotBlank(message = "{NAME_MUST_BE_NOT_BLANK}")
    private String name;

}

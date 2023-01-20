package com.epam.cloudgantt.payload;

import com.epam.cloudgantt.util.AppConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProjectDTO {

    private UUID id;

    @NotBlank(message = "{NAME_MUST_BE_NOT_NULL}")
    @Size(min = 1, max = 256)
    private String name;
}
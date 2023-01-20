package com.epam.cloudgantt.payload;

import com.epam.cloudgantt.util.AppConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProjectDTO {

    private UUID id;

    @Pattern(message = "{PROJECT_NAME_MUST_BE_VALID_TO_OUR_RESTRICTIONS}", regexp = AppConstants.PROJECT_NAME_REGEX)
    @NotBlank(message = "{NAME_MUST_BE_NOT_NULL}")
    private String name;

    private UUID userId;
}
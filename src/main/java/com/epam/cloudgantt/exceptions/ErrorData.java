package com.epam.cloudgantt.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class ErrorData extends RuntimeException {
    private final List<String> errorMessages;

    public ErrorData(List<String> alertMessages) {
        this.errorMessages = alertMessages;
    }

    public void addError(String errorMessage) {
        if (!errorMessages.contains(errorMessage)) {
            errorMessages.add(errorMessage);
        }
    }
}

package com.epam.cloudgantt.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class AlertData extends RuntimeException {
    private final List<String> alertMessages;
    public AlertData(List<String> alertMessages) {
        this.alertMessages = alertMessages;
    }
}

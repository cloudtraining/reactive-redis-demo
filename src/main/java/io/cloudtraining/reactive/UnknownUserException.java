package io.cloudtraining.reactive;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnknownUserException extends RuntimeException {
    public UnknownUserException(UUID userId) {
        super(String.format("Unknown user id: %s", userId));
    }
}

package com.github.adityagarde.cqrs.core.exceptions;

public class AggregateNotFoundException extends RuntimeException {

    public AggregateNotFoundException(String message) {
        super(message);
    }
}

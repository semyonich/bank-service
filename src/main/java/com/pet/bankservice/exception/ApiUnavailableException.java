package com.pet.bankservice.exception;

public class ApiUnavailableException extends RuntimeException {
    public ApiUnavailableException(String message) {
        super(message);
    }
}

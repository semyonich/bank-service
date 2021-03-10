package com.pet.bankservice.exception;

public class AccountIsNotActiveException extends RuntimeException {
    public AccountIsNotActiveException(String message) {
        super(message);
    }
}

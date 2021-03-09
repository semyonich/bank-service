package com.pet.bankservice.exception;

public class CurrencyIsNotAvailableException extends RuntimeException {
    public CurrencyIsNotAvailableException(String message) {
        super(message);
    }
}

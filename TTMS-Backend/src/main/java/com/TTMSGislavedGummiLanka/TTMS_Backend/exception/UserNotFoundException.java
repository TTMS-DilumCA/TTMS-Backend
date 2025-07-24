package com.TTMSGislavedGummiLanka.TTMS_Backend.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
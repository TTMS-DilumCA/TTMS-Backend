package com.TTMSGislavedGummiLanka.TTMS_Backend.exception;

public class MoldNotFoundException extends RuntimeException {
    public MoldNotFoundException(String id) {
        super("Mold with id " + id + " not found");
    }
}
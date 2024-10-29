package com.TTMSGislavedGummiLanka.TTMS_Backend.exception;

public class ProcessNotFoundException extends RuntimeException {
    public ProcessNotFoundException(String id) {
        super("Process with id " + id + " not found");
    }
}
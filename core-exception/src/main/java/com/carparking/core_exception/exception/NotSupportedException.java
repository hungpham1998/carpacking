package com.carparking.core_exception.exception;

public class NotSupportedException extends BadRequestException {
    public NotSupportedException() {
        super();
        setCode("com.carparking.exception.NotSupportedException");
    }
}
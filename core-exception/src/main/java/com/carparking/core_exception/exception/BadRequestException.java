package com.carparking.core_exception.exception;

public class BadRequestException extends RunException {
  public BadRequestException() {
    setStatus(400);
    setCode("com.carpacking.exception.BadRequestException");
  }
}

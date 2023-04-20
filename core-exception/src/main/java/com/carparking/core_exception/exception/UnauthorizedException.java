package com.carparking.core_exception.exception;

public class UnauthorizedException extends RunException {
  public UnauthorizedException() {
    setCode("com.carpacking.exception.UnauthorizedException");
    setStatus(401);
  }
}

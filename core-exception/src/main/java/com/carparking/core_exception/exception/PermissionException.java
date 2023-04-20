package com.carparking.core_exception.exception;

public class PermissionException extends RunException {
  public PermissionException() {
    setStatus(403);
    setCode("com.carpacking.exception.PermissionException");
  }
}

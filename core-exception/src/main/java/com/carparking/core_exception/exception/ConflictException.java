package com.carparking.core_exception.exception;

public class ConflictException extends RunException {

  public ConflictException() {
    setStatus(409);
  }

  public ConflictException(String type, String field, Object value) {
    setStatus(409);
    setCode("com.carpacking.exception.ConflictException");
    putParam("type", type);
    putParam("field", field);
    putParam("value", value);
  }
}

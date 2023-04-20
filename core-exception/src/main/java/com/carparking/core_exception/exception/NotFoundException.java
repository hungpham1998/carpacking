package com.carparking.core_exception.exception;

public class NotFoundException extends RunException {

  public NotFoundException() {
    setStatus(404);
  }

  public NotFoundException(Object id, String type) {
    setCode("com.carpacking.exception.NotFoundException");
    setStatus(404);
    putParam("id", id);
    putParam("type", type);
  }
}

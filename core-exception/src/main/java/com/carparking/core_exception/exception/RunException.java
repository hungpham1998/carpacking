package com.carparking.core_exception.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class RunException extends RuntimeException {
  private String code;
  private String message;
  private int status;
  private Map<String, Object> params = new HashMap<>();

  public RunException() {
    setStatus(500);
    setCode("com.carpacking.exception.RunException");
  }

  protected void putParam(String key, Object value) {
    params.put(key, value);
  }

  public void setCode(String code) {
    this.code = code;
  }
}

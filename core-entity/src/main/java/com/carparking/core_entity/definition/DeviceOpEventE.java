package com.carparking.core_entity.definition;

import java.util.Arrays;
import java.util.Objects;

public enum DeviceOpEventE {
  GPS_DATA("1"), STOP("2"), CMD_RESULT("3"), CMD_ID("4");

  String value;

  DeviceOpEventE(String value) {
    this.value = value;
  }

  public static DeviceOpEventE of(String value) {
    return Arrays.stream(values())
        .filter(evt -> Objects.equals(evt.value, value)).findFirst()
        .orElse(null);
  }

  public String getValue() {
    return value;
  }
}

package com.carparking.core_entity.definition;

import java.util.Arrays;
import java.util.Objects;

public enum CommandResultE {
  SUCCESS("0"), INVALID_CMDID("1"), SYNTAX_ERROR("2");

  String value;

  CommandResultE(String value) {
    this.value = value;
  }

  public static CommandResultE of(String value) {
    return Arrays.stream(values())
        .filter(evt -> Objects.equals(evt.value, value)).findFirst()
        .orElse(null);
  }

  public String getValue() {
    return value;
  }
}

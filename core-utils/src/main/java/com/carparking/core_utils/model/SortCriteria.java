package com.htsc.core_utils.model;

import lombok.Data;

@Data
public class SortCriteria {
  private String field;
  private SortDirection direction;

  public static SortCriteria of(String field, SortDirection direction) {
    var crit = new SortCriteria();
    crit.setField(field);
    crit.setDirection(direction);
    return crit;
  }
}

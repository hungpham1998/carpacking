package com.carparking.core_utils.model;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Criteria {
  private String field;
  private Object value;
  private Object min;
  private Object max;
  private List<@NotNull Object> values;

  @NotNull
  private CriteriaOperator operator;

  @Size(min = 2)
  private List<Criteria> children;
  private FieldType type;

  public Criteria(String field, CriteriaOperator operator) {
    this.field = field;
    this.value = value;
    this.operator = operator;
  }

  public Criteria(CriteriaOperator operator, List<Criteria> children) {
    this.operator = operator;
    this.children = children;
  }

  public Criteria(String field, CriteriaOperator operator,
      List<Criteria> children) {
    this.field = field;
    this.operator = operator;
    this.children = children;
  }

  public void dropCriteria(String field) {
    if (Objects.nonNull(children)) {
      children.removeIf(c -> Objects.equals(c.getField(), field));
      children.forEach(criteria -> criteria.dropCriteria(field));
    }
  }
}

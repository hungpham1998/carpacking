package com.carparking.core_utils.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilterRequest {

  @NotNull
  private List<@NotNull @Valid Criteria> criteria = new ArrayList<>();

  private List<SortCriteria> sort = new ArrayList<>();

  @Min(0)
  private int page = 0;

  @Min(1)
  private int size = 100;

  public static FilterRequest of(List<Criteria> criteria, int page, int size) {
    var filter = new FilterRequest();
    filter.setCriteria(criteria);
    filter.setPage(page);
    filter.setSize(size);

    return filter;
  }

  public static FilterRequest of(
      List<Criteria> criteria, List<SortCriteria> sort, int page, int size
  ) {
    var filter = new FilterRequest();
    filter.setCriteria(criteria);
    filter.setSort(sort);
    filter.setPage(page);
    filter.setSize(size);

    return filter;
  }

  public void dropCriteria(String field) {
    criteria.removeIf(c -> Objects.equals(c.getField(), field));
    criteria.forEach(c -> c.dropCriteria(field));
  }
}

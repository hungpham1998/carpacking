package com.htsc.core_utils.model;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IdListRequest {

  @NotEmpty
  private List<@NotNull String> ids;
}

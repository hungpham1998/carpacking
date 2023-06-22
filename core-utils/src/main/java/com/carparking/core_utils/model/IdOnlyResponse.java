package com.carparking.core_utils.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class IdOnlyResponse {
  private String id;
}

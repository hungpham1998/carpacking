package com.carparking.core_response_advice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WrappedListResponse extends WrappedResponse {
  private Meta meta;

  public WrappedListResponse(
      Object data, Meta meta, String message, int status
  ) {
    super(data, message, status);
    this.meta = meta;
  }
}

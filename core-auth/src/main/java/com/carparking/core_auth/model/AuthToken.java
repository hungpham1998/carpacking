package com.carparking.core_auth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class AuthToken {
  private String accessToken;
  private String refreshToken;
}

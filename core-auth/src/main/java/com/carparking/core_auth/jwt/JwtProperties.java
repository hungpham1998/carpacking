package com.carparking.core_auth.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.auth.jwt")
@Data
public class JwtProperties {
  private String accessSecret = "licensekey";

  private long accessExpire = 86400000;

  private String refreshSecret = "iw7B3OzFkeHdIw230tr6";

  private long refreshExpire = 86400000;

  private boolean refreshEnable = false;
}

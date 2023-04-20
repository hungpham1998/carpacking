package com.carparking.core_auth.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.auth.jwt")
@Data
public class JwtProperties {
  private String accessSecret = "xrTu7ACvn8fXewCdCjF1";

  private long accessExpire = 86400000;

  private String refreshSecret = "iw7B3OzFkeHdIw230tr6";

  private long refreshExpire = 86400000;

  private boolean refreshEnable = false;

  private String changePasswordSecret = "kDTcgsO359YtVa3lXvaM";

  private long changePasswordExpire = 86400000;
}

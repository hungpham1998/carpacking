package com.carparking.core_auth.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class EmailPasswordAuthentication extends
    UsernamePasswordAuthenticationToken {

  public EmailPasswordAuthentication(Object principal, Object credentials) {
    super(principal, credentials);
  }

  public EmailPasswordAuthentication(Object principal, Object credentials,
      Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }
}

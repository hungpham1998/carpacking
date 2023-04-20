package com.carparking.core_auth.util;

import com.carparking.core_auth.jwt.JwtTokenProvider;
import com.carparking.core_auth.model.UserPrincipal;
import com.carparking.core_auth.service.CustomUserDetailsService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class SecurityService {
  @Autowired
  CustomUserDetailsService customUserDetailsService;

  private JwtTokenProvider jwtProvider;

  public static Long getUserId() {
    return getPrincipal().getId();
  }

  public static String getUserName() {
    return getPrincipal().getUsername();
  }

  public static Object getAdditionalData(String key) {
    Map<String, Object> data = getPrincipal().getAdditionalData();
    return Objects.nonNull(data) ? data.getOrDefault(key, null) : null;
  }

  public static Set<String> getUserRoles() {
    return getPrincipal().getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toSet());
  }

  public static boolean hasRole(String role) {
    return getPrincipal().getAuthorities()
        .stream()
        .anyMatch(ga -> Objects.equals(ga.getAuthority(), role));
  }

  private static UserPrincipal getPrincipal() {
    return
        (UserPrincipal) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
  }
  public Authentication getAuthentication(String token) {
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtProvider.getUsernameFromToken(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

}

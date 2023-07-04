package com.carparking.core_auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.carparking.core_auth.model.AuthToken;
import com.carparking.core_auth.model.EmailPasswordAuthentication;
import com.carparking.core_auth.model.UsernamePasswordAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@RequiredArgsConstructor
@Slf4j
public class InitialAuthenticationFilter extends OncePerRequestFilter {

  private final AuthenticationManager authManager;

  private final JwtTokenProvider jwtTokenProvider;

  private final MessageSource messageSource;

  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws IOException {

    Map<String, String> body = objectMapper.readValue(
        request.getReader().lines().collect(
            Collectors.joining(System.lineSeparator())), Map.class);

    String email = body.get("email");
    String password = body.get("password");
    String username = body.get("username");

    UsernamePasswordAuthenticationToken credentials;
    try {
      if (Objects.isNull(email)) {
        credentials = (UsernamePasswordAuthenticationToken) authManager
            .authenticate(
                new UsernamePasswordAuthentication(username, password));
      } else if (Objects.isNull(username)) {
        credentials = (UsernamePasswordAuthenticationToken) authManager
            .authenticate(new EmailPasswordAuthentication(email, password));
      } else {
        Map<String, Object> error = new HashMap<>();
        error.put("message", "User or email is required.");
        error.put("status", 400);

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), error);
        return;
      }
    } catch (Exception ex) {
      log.warn("(doFilterInternal) ex: {}", getStackTrace(ex));
      Map<String, Object> error = new HashMap<>();
      log.info("(doFilterInternal) locale: {}", LocaleContextHolder.getLocale());
      error.put("message", messageSource.getMessage(
          "com.carparking.message.InvalidLogin", null,
          LocaleContextHolder.getLocale()));
      error.put("status", 401);

      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
      response.getWriter().println(objectMapper.writeValueAsString(error));
      return;
    }

    AuthToken token = jwtTokenProvider.generateAuthToken(credentials);

    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    objectMapper.writeValue(response.getWriter(), Map.of("data", token));
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return !request.getServletPath().equals("/auth/login");
  }
}

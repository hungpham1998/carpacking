package com.carparking.core_auth.service;

import com.carparking.core_auth.jwt.JwtProperties;
import com.carparking.core_auth.jwt.JwtTokenProvider;
import com.carparking.core_auth.model.AuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.naming.OperationNotSupportedException;

@Slf4j
@RequiredArgsConstructor
public class AuthService {
  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider jwtTokenProvider;

  private final JwtProperties jwtProperties;

  public AuthToken authenticateUser(String username, String password) {
    UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(username, password);

    RequestAttributes requestAttributes =
            RequestContextHolder.currentRequestAttributes();
    if (requestAttributes instanceof ServletRequestAttributes) {
      ServletRequestAttributes servletAttributes =
              (ServletRequestAttributes) requestAttributes;
      token.setDetails(
              new WebAuthenticationDetails(servletAttributes.getRequest())
      );
    }

//    SecurityContextHolder.getContext().setAuthentication(authentication);

    Authentication authentication = authenticationManager.authenticate(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return jwtTokenProvider.generateAuthToken(authentication);
  }

  public AuthToken refreshAccessToken(String refreshToken)
      throws OperationNotSupportedException {

    if (!jwtProperties.isRefreshEnable()) {
      throw new OperationNotSupportedException(
          "Please enable this operation by set application.auth.jwt.refresh-enable=true"
      );
    }
    Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();
    AuthToken token = jwtTokenProvider.generateAuthToken(
        authentication, false
    );
    token.setRefreshToken(refreshToken);
    return token;
  }
}

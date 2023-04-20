package com.carparking.application.websocket;

import com.carparking.core_auth.jwt.JwtTokenProvider;
import com.carparking.core_auth.util.SecurityService;
import io.jsonwebtoken.JwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
  private JwtTokenProvider jwtProvider;

  private final JwtTokenProvider jwtTokenProvider;


  SecurityService securityService;

  public JwtHandshakeInterceptor(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                 Map<String, Object> attributes) throws Exception {
    if (!(request instanceof ServletServerHttpRequest)) {
      return true;
    }

    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
    HttpSession session = servletRequest.getServletRequest().getSession();

    String token = (String) session.getAttribute("token");

    if (StringUtils.isBlank(token)) {
      return false;
    }

    if (!jwtTokenProvider.validateToken(token)) {
      return false;
    }

    Authentication authentication = securityService.getAuthentication(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return true;
  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                             Exception ex) {
  }

  private HttpSession getSession(ServerHttpRequest request) {
    if (request instanceof ServletServerHttpRequest) {
      ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
      return servletRequest.getServletRequest().getSession();
    }
    return null;
  }
}

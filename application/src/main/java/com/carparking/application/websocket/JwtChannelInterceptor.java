package com.carparking.application.websocket;

import com.carparking.core_auth.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class  JwtChannelInterceptor extends ChannelInterceptorAdapter {

  private final Map<String, Set<String>> userSessionsMap = new ConcurrentHashMap<>();
  private final Map<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();
  private JwtTokenProvider jwtTokenProvider;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
      String token = accessor.getFirstNativeHeader("token");
      if (!StringUtils.isEmpty(token) && jwtTokenProvider.validateToken(token)) {
        String username = jwtTokenProvider.getUserIdFromJWT(token);
        String sessionId = accessor.getSessionId();
        Set<String> userSessions = userSessionsMap.computeIfAbsent(username, k -> new HashSet<>());
        userSessions.add(sessionId);
        accessor.setUser(new UsernamePasswordAuthenticationToken(username, null));
      }
    } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
      String username = accessor.getUser().getName();
      String sessionId = accessor.getSessionId();
      Set<String> userSessions = userSessionsMap.get(username);
      if (userSessions != null) {
        userSessions.remove(sessionId);
        if (userSessions.isEmpty()) {
          userSessionsMap.remove(username);
        }
      }
    }
    return message;
  }

  @Override
  public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    String destination = accessor.getDestination();
    if (destination != null && destination.startsWith("/topic/")) {
      String[] destinationParts = destination.split("/");
      if (destinationParts.length == 3) {
        String username = destinationParts[2];
        Set<String> userSessions = userSessionsMap.get(username);
        if (userSessions != null) {
          List<String> sessionsToRemove = new ArrayList<>();
          userSessions.forEach(sessionId -> {
            WebSocketSession session = webSocketSessionMap.get(sessionId);
            if (session != null && session.isOpen()) {
              sessionsToRemove.add(sessionId);
            }
          });
          sessionsToRemove.forEach(sessionId -> userSessions.remove(sessionId));
          if (userSessions.isEmpty()) {
            userSessionsMap.remove(username);
          } else {
            accessor.setSessionId(userSessions.iterator().next());
          }
        }
      }
    }
  }

}

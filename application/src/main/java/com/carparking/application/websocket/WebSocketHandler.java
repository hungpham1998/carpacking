package com.carparking.application.websocket;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;
  @Autowired
  private SimpUserRegistry simpUserRegistry;

  private WebSocketSession session;
  private Timer timeoutTimer;



  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    JsonObject jsonObject = JsonParser.parseString(message.getPayload()).getAsJsonObject();

    String messageType = jsonObject.get("type").getAsString();
    String destination = jsonObject.get("destination").getAsString();
    String payload = jsonObject.get("payload").toString();

    switch (messageType) {
      case "SEND":
        String[] roles = {"ROLE_USER"};
        sendMessageToUsers(roles, destination, payload);
        break;
      case "BROADCAST":
        broadcast(destination, payload);
        break;
    }
  }



  private void broadcast(String destination, String message) {
    messagingTemplate.convertAndSend(destination, message);
  }

  public void sendMessageToUsers(String[] roles, String destination, Object message) {
    List<String> users = new ArrayList<>();
    for (String role : roles) {
      for (Object user : simpUserRegistry.getUsers()) {
        if (((WebSocketSession) user).getPrincipal() instanceof Authentication) {
          Authentication auth = (Authentication) ((WebSocketSession) user).getPrincipal();
          if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(role))) {
            users.add(((WebSocketSession) user).getId());
          }
        }
      }
    }
    messagingTemplate.convertAndSendToUser(users.toString(), destination, message);
  }
//
//  @Override
//  public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
//    String payload = message.getPayload();
//    JsonObject jsonObject = new Gson().fromJson(payload, JsonObject.class);
//    String messageType = jsonObject.get("messageType").getAsString();
//
//    if (messageType.equals("broadcast")) {
//      String content = jsonObject.get("content").getAsString();
//      messagingTemplate.convertAndSend("/topic/public", content);
//    } else if (messageType.equals("sendToUsers")) {
//      String content = jsonObject.get("content").getAsString();
//      JsonArray rolesJsonArray = jsonObject.get("roles").getAsJsonArray();
//      String[] roles = new Gson().fromJson(rolesJsonArray, String[].class);
//      sendMessageToUsers(roles, "/topic/messages", content);
//    }
//  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    this.session = session;
    startTimeoutTimer();
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    resetTimeoutTimer();
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    stopTimeoutTimer();
  }

  private void startTimeoutTimer() {
    timeoutTimer = new Timer();
    timeoutTimer.schedule(new TimerTask() {
      @Override
      public void run() {
        if (session != null && session.isOpen()) {
          try {
            session.close();
          } catch (Exception e) {
            System.out.println(e.getMessage());
            // Handle exception if needed
          }
        }
      }
    }, 30 * 60 * 1000);
  }

  private void resetTimeoutTimer() {
    if (timeoutTimer != null) {
      timeoutTimer.cancel();
      startTimeoutTimer();
    }
  }

  private void stopTimeoutTimer() {
    if (timeoutTimer != null) {
      timeoutTimer.cancel();
      timeoutTimer = null;
    }
  }
}
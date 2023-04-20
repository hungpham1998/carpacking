package com.carparking.application.websocket;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;
  @Autowired
  private SimpUserRegistry simpUserRegistry;



  public void SimpleWebSocketHandler(SimpUserRegistry simpUserRegistry, SimpMessagingTemplate messagingTemplate) {
    this.simpUserRegistry = simpUserRegistry;
    this.messagingTemplate = messagingTemplate;
  }

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
}
package com.carparking.application.websocket;

import com.carparking.core_auth.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Autowired
  private JwtChannelInterceptor jwtChannelInterceptor;
  @Autowired
  private WebSocketHandler webSocketHandler;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry
        .addEndpoint("/ws")
        .setAllowedOrigins("*")
        .withSockJS()
        .setInterceptors(jwtHandshakeInterceptor());

  }

  @Bean
  public JwtHandshakeInterceptor jwtHandshakeInterceptor() {
    return new JwtHandshakeInterceptor(jwtTokenProvider);
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/topic", "/queue");
    registry.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(jwtChannelInterceptor);
  }

  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(new WebSocketHandler(), "/topic/**");
  }
}
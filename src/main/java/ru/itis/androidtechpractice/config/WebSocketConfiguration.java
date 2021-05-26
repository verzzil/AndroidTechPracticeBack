package ru.itis.androidtechpractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import ru.itis.androidtechpractice.handlers.ChatEndpoint;

@EnableWebSocket
@Configuration
public class WebSocketConfiguration implements WebSocketConfigurer {
//    @Bean
//    public ChatEndpoint chatEndpoint() {
//        return new ChatEndpoint();
//    }
//
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

    }
}

package main;

import main.web.socket.handlers.ChatWebSocketHandler;
import main.web.socket.handlers.GameWebSocketHandler;
import main.web.socket.handlers.QueueWebSocketHandler;
import main.web.socket.handshake.WebSocketHandshakeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new ChatWebSocketHandler(), "/messages")
            .addHandler(new QueueWebSocketHandler(), "/queue")
            .addHandler(new GameWebSocketHandler(), "/game")
            .addInterceptors(webSocketHandshakeHandler());
    }

    @Bean
    public WebSocketHandshakeHandler webSocketHandshakeHandler(){
        return new WebSocketHandshakeHandler();
    }
}

package main;

import main.web.socket.data.PairOfWebSocketAuthorizedSession;
import main.web.socket.handlers.ChatWebSocketHandler;
import main.web.socket.handlers.GameWebSocketHandler;
import main.web.socket.handlers.QueueWebSocketHandler;
import main.web.socket.handshake.WebSocketHandshakeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.HashMap;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
    private ApplicationContext applicationContext;

    @Autowired
    public WebSocketConfig(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(applicationContext.getBean(ChatWebSocketHandler.class), "/messages")
            .addHandler(applicationContext.getBean(QueueWebSocketHandler.class), "/queue")
            .addHandler(applicationContext.getBean(GameWebSocketHandler.class), "/game")
            .addInterceptors(applicationContext.getBean(WebSocketHandshakeHandler.class));
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public HashMap<Integer, PairOfWebSocketAuthorizedSession> gameMatchHashMap(){
        return new HashMap<>();
    }
}

package main.web.socket.handlers;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class WebSocketHandler extends TextWebSocketHandler {
    private static final Logger logger = Logger.getLogger(WebSocketHandler.class);
    private List<WebSocketSession> sessions;

    public WebSocketHandler(){
        sessions = new ArrayList<>();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        logger.info("Principle: " + session.getPrincipal());
        TextMessage message = new TextMessage("Hello!");
        session.sendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}

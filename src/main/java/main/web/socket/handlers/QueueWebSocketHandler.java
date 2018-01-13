package main.web.socket.handlers;

import com.google.gson.Gson;
import main.web.socket.data.WebSocketAuthorizedSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.LinkedList;
import java.util.List;

public class QueueWebSocketHandler extends TextWebSocketHandler {
    private List<WebSocketAuthorizedSession> sessions;
    private Gson gson = new Gson();

    public QueueWebSocketHandler(){
        sessions = new LinkedList<>();
    }

    @Scheduled(fixedDelay = 2000)
    public void searchPairs(){

    }

    /**
     * Save webSocketAuthorizedSession to sessions set
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(new WebSocketAuthorizedSession((String) session.getAttributes().get("user"), session));
    }

    /**
     * Remove webSocketAuthorizedSession from sessions set
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(new WebSocketAuthorizedSession(session));
    }
}

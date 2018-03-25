package main.web.socket.handlers;

import com.google.gson.Gson;
import main.web.socket.data.Message;
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

    /**
     * Save webSocketAuthorizedSession to sessions set
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if(sessions.size() != 0){
            WebSocketAuthorizedSession secondSession = sessions.get(0);
            Message message = new Message((String) session.getAttributes().get("user"), secondSession.getUser());
            session.sendMessage(new TextMessage(gson.toJson(message, Message.class)));

            String tmp = message.getFromUser();
            message.setFromUser(message.getToUser());
            message.setToUser(tmp);

            secondSession.getSession().sendMessage(new TextMessage(gson.toJson(message, Message.class)));

            sessions.remove(secondSession);
            secondSession.getSession().close();
            session.close();
        } else {
            sessions.add(new WebSocketAuthorizedSession((String) session.getAttributes().get("user"), session));
        }
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

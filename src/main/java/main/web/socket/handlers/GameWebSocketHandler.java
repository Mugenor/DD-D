package main.web.socket.handlers;

import com.google.gson.Gson;
import main.web.socket.data.Message;
import main.web.socket.data.WebSocketAuthorizedSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.LinkedList;
import java.util.List;

public class GameWebSocketHandler extends TextWebSocketHandler {
    private List<WebSocketAuthorizedSession> sessions;
    private Gson gson = new Gson();

    public GameWebSocketHandler(){
        sessions = new LinkedList<>();
    }
    //TODO normal classes for game (GameMessage.class)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Message gameMessageToUser = gson.fromJson(message.getPayload(), Message.class);
        if(gameMessageToUser == null || gameMessageToUser.getToUser()==null){
            session.sendMessage(new TextMessage("Bad request"));
            return;
        }
        for (WebSocketAuthorizedSession webSocketAuthorizedSession: sessions){
            if(gameMessageToUser.getToUser().equals(webSocketAuthorizedSession.getUser())){
                gameMessageToUser.setFromUser((String)session.getAttributes().get("user"));
                webSocketAuthorizedSession.getSession().sendMessage(message);
                return;
            }
        }
        session.sendMessage(new TextMessage("No receiver online"));
    }

    /**
     * Save webSocketAuthorizedSession to sessions set
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(new WebSocketAuthorizedSession((String) session.getAttributes().get("user"), session));
    }

    /**
     * Remove webSocketAuthorizedSession from sessions set
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(new WebSocketAuthorizedSession(session));
    }
}

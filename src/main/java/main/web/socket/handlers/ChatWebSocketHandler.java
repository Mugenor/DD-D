package main.web.socket.handlers;

import com.google.gson.Gson;
import main.web.socket.data.ChatMessage;
import main.web.socket.data.WebSocketAuthorizedSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.LinkedList;
import java.util.List;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private static final Logger logger = Logger.getLogger(ChatWebSocketHandler.class);
    private List<WebSocketAuthorizedSession> sessions;
    private Gson gson = new Gson();

    public ChatWebSocketHandler(){
        sessions = new LinkedList<>();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            ChatMessage chatMessageToUser = gson.fromJson(message.getPayload(), ChatMessage.class);
            if(chatMessageToUser == null || chatMessageToUser.getToUser()==null || chatMessageToUser.getMessage()==null){
                session.sendMessage(new TextMessage("Bad request"));
                return;
            }
            for (WebSocketAuthorizedSession webSocketAuthorizedSession: sessions){
                if(chatMessageToUser.getToUser().equals(webSocketAuthorizedSession.getUser())){
                    chatMessageToUser.setFromUser((String)session.getAttributes().get("user"));
                    webSocketAuthorizedSession.getSession().sendMessage(new TextMessage(gson.toJson(chatMessageToUser)));
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

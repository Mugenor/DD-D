package main.web.socket.handlers;

import com.google.gson.Gson;
import main.web.socket.data.PairOfWebSocketAuthorizedSession;
import main.web.socket.handshake.WebSocketHandshakeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Component("gameWebSocketHandler")
public class GameWebSocketHandler extends TextWebSocketHandler {
    private static final String ERROR_MESSAGE = "{\"error\": \"You are not in game!\"}";
    private static final String GAME_MATCH_ATTRIBUTE_NAME = "gameMatchId";
    private HashMap<Integer, PairOfWebSocketAuthorizedSession> sessionPairs;
    private Gson gson;

    @Autowired
    public GameWebSocketHandler(@Qualifier("gameMatchHashMap") HashMap<Integer, PairOfWebSocketAuthorizedSession> sessionPairs) {
        gson = new Gson();
        this.sessionPairs = sessionPairs;
    }

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        int gameMatchId = (Integer) session.getAttributes().get(GAME_MATCH_ATTRIBUTE_NAME);
        String username = (String) session.getAttributes().get(WebSocketHandshakeHandler.USERNAME_ATTRIBUTE_NAME);
        PairOfWebSocketAuthorizedSession pairSession = sessionPairs.get(gameMatchId);
        pairSession.getOppositeSessionByUsername(username).getSession().sendMessage(message);
    }

    /**
     * Save webSocketAuthorizedSession to sessions set
     *
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = (String) session.getAttributes().get("user");

        for (Map.Entry<Integer, PairOfWebSocketAuthorizedSession> entry : sessionPairs.entrySet()) {
            if (entry.getValue().getFirstSession().getUser().equals(username)) {
                entry.getValue().getFirstSession().setSession(session);
                session.getAttributes().put(GAME_MATCH_ATTRIBUTE_NAME, entry.getKey());
                return;
            } else if (entry.getValue().getSecondSession().getUser().equals(username)) {
                entry.getValue().getSecondSession().setSession(session);
                session.getAttributes().put(GAME_MATCH_ATTRIBUTE_NAME, entry.getKey());
                return;
            }
        }
        session.close(new CloseStatus(CloseStatus.BAD_DATA.getCode(), ERROR_MESSAGE));
    }



    /**
     * Remove webSocketAuthorizedSession from sessions set
     *
     * @param session
     * @param status
     */
    // TODO save info about ended game match
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get(WebSocketHandshakeHandler.USERNAME_ATTRIBUTE_NAME);
        for (Map.Entry<Integer, PairOfWebSocketAuthorizedSession> entry : sessionPairs.entrySet()) {
            if (entry.getValue().getSessionByUsername(username) != null) {
                if (entry.getValue().getFirstSession().getSession() != null && entry.getValue().getFirstSession().getSession().isOpen()) {
                    entry.getValue().getFirstSession().getSession().close();
                }
                if (entry.getValue().getSecondSession().getSession() != null && entry.getValue().getSecondSession().getSession().isOpen()) {
                    entry.getValue().getSecondSession().getSession().close();
                }
                return;
            }
        }
    }

}

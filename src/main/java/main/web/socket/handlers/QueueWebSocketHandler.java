package main.web.socket.handlers;

import com.google.gson.Gson;
import main.entities.GameMatch;
import main.services.GameMatchService;
import main.web.socket.data.PairOfWebSocketAuthorizedSession;
import main.web.socket.data.WebSocketAuthorizedSession;
import main.web.socket.handshake.WebSocketHandshakeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@Component("queueWebSocketHandler")
public class QueueWebSocketHandler extends TextWebSocketHandler {
    private List<WebSocketAuthorizedSession> sessions;
    private HashMap<Integer, PairOfWebSocketAuthorizedSession> sessionPairs;
    private GameMatchService gameMatchService;

    private Gson gson;

    @Autowired
    public QueueWebSocketHandler(@Qualifier("gameMatchHashMap") HashMap<Integer, PairOfWebSocketAuthorizedSession> sessionPairs, GameMatchService gameMatchService) {
        gson = new Gson();
        sessions = new LinkedList<>();
        this.sessionPairs = sessionPairs;
        this.gameMatchService = gameMatchService;
    }

    /**
     * Save webSocketAuthorizedSession to sessions set
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = (String) session.getAttributes().get(WebSocketHandshakeHandler.USERNAME_ATTRIBUTE_NAME);
        WebSocketAuthorizedSession opponentSession;
        ListIterator<WebSocketAuthorizedSession> it = sessions.listIterator();
        while (it.hasNext()) {
            opponentSession = it.next();
            if (!username.equals(opponentSession.getUser())) {
                GameMatch gameMatch = gameMatchService.saveGameMatch(username, opponentSession.getUser());
                QueueMessage message = new QueueMessage(opponentSession.getUser(), true);
                WebSocketSession webSocketOpponentSession = opponentSession.getSession();
                opponentSession.setSession(null);
                PairOfWebSocketAuthorizedSession newPair = new PairOfWebSocketAuthorizedSession(new WebSocketAuthorizedSession(username), opponentSession);
                sessionPairs.put(gameMatch.getId(), newPair);

                int random = ((int) (Math.random() * 1000.0d)) % 2;
                if(random == 0) {
                    message.invertYourTurnFirst();
                }

                session.sendMessage(new TextMessage(gson.toJson(message, QueueMessage.class)));
                message.setEnemy(username);
                message.invertYourTurnFirst();

                webSocketOpponentSession.sendMessage(new TextMessage(gson.toJson(message, QueueMessage.class)));
                it.remove();

                webSocketOpponentSession.close();
                session.close();
                return;
            }
        }
        sessions.add(new WebSocketAuthorizedSession(username, session));
    }

    /**
     * Remove webSocketAuthorizedSession from sessions set
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(new WebSocketAuthorizedSession(session));
    }

    private class QueueMessage {
        private String enemy;
        private boolean yourTurnFirst;

        QueueMessage(String enemy, boolean yourTurnFirst){
            this.enemy = enemy;
            this.yourTurnFirst = yourTurnFirst;
        }

        String getEnemy() {
            return enemy;
        }

        void setEnemy(String enemy) {
            this.enemy = enemy;
        }

        boolean isYourTurnFirst() {
            return yourTurnFirst;
        }

        void setYourTurnFirst(boolean yourTurnFirst) {
            this.yourTurnFirst = yourTurnFirst;
        }

        void invertYourTurnFirst() {
            this.yourTurnFirst = !this.yourTurnFirst;
        }
    }
}

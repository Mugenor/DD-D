package main.web.socket.data;

import main.entities.User;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketAuthorizedSession {
    private User user;
    private WebSocketSession session;

    public WebSocketAuthorizedSession(User user, WebSocketSession session){

    }
}

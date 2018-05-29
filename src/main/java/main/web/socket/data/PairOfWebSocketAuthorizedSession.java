package main.web.socket.data;

public class PairOfWebSocketAuthorizedSession {
    private WebSocketAuthorizedSession firstSession;
    private WebSocketAuthorizedSession secondSession;

    public PairOfWebSocketAuthorizedSession(WebSocketAuthorizedSession firstSession, WebSocketAuthorizedSession secondSession) {
        this.firstSession = firstSession;
        this.secondSession = secondSession;
    }

    public WebSocketAuthorizedSession getFirstSession() {
        return firstSession;
    }

    public WebSocketAuthorizedSession getSecondSession() {
        return secondSession;
    }

    public WebSocketAuthorizedSession getSessionByUsername(String username) {
        if (username.equals(firstSession.getUser())) {
            return firstSession;
        } else if (username.equals(secondSession.getUser())) {
            return secondSession;
        } else return null;
    }
    public WebSocketAuthorizedSession getOppositeSessionByUsername(String username) {
        boolean eqFirstUser = username.equals(firstSession.getUser());
        boolean eqSecondUser = username.equals(secondSession.getUser());
        if(eqFirstUser && !eqSecondUser){
            return secondSession;
        } else if(!eqFirstUser && eqSecondUser) {
            return firstSession;
        } else return null;
    }
}

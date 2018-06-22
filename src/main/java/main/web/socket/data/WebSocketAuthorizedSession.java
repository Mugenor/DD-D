package main.web.socket.data;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketAuthorizedSession {
    private String user;
    private WebSocketSession session;

    public WebSocketAuthorizedSession(String user) {
        this.user = user;
    }

    public WebSocketAuthorizedSession(String user, WebSocketSession session){
        this.user = user;
        this.session = session;
    }

    public WebSocketAuthorizedSession(WebSocketSession session) {
        this.session = session;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    public boolean isAuthenticated(){
        return user!=null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebSocketAuthorizedSession that = (WebSocketAuthorizedSession) o;

        return session.equals(that.session);
    }

    @Override
    public int hashCode() {
        return session.hashCode();
    }
}

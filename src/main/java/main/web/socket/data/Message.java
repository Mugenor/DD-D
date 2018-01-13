package main.web.socket.data;

public class Message {
    protected String toUser;
    protected String fromUser;

    public Message(){}

    public Message(String toUser, String fromUser) {
        this.toUser = toUser;
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }
}

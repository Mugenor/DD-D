package main.web.socket.data;

public class ChatMessage extends Message{
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChatMessage(String toUser, String fromUser, String message) {
        super(toUser, fromUser);
        this.message = message;
    }
}

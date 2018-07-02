package main.controllers.util;

public class FeedbackRequest {
    private String message;

    public FeedbackRequest() {
    }

    public FeedbackRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package main.controllers.util;

public class FeedbackRequest {
    private boolean isCard;
    private String message;

    public FeedbackRequest() {
    }

    public FeedbackRequest(boolean isCard, String message) {
        this.isCard = isCard;
        this.message = message;
    }

    public boolean isCard() {
        return isCard;
    }

    public void setCard(boolean card) {
        isCard = card;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

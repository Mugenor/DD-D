package main.entities;

import javax.persistence.*;
import java.util.Date;

@Entity (name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "feedbackSeq")
    @SequenceGenerator(name = "feedbackSeq", sequenceName = "feedbackSeq")
    @Column
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn (name = "ourUser", nullable = false)
    private User user;
    @Column
    private String message;
    @Column (name = "is_card")
    private boolean isCard;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column
    private String response;

    public Long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isCard() {
        return isCard;
    }
    public void setCard(boolean card) {
        isCard = card;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getResponse() {
        return response;
    }
    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", user:" + user +
                ", message:\"" + message +
                "\", isCard:" + isCard +
                ", date:\"" + date +
                "\", response:\"" + response +
                "\"}";
    }

    public Feedback() {}
    public Feedback (User user, String message, boolean isCard, Date date) {
        this.user = user;
        this.message = message;
        this.isCard = isCard;
        this.date = date;
    }
    public Feedback (User user, String message, boolean isCard, Date date, String response) {
        this.user = user;
        this.message = message;
        this.isCard = isCard;
        this.date = date;
        this.response = response;
    }
}

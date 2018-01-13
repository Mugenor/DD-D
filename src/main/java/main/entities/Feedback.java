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
    private java.lang.String message;
    @Column (name = "is_card")
    private boolean isCard;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column
    private java.lang.String response;

    public Long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public java.lang.String getMessage() {
        return message;
    }
    public void setMessage(java.lang.String message) {
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
    public java.lang.String getResponse() {
        return response;
    }
    public void setResponse(java.lang.String response) {
        this.response = response;
    }

    @Override
    public java.lang.String toString() {
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
    public Feedback (User user, java.lang.String message, boolean isCard, Date date) {
        this.user = user;
        this.message = message;
        this.isCard = isCard;
        this.date = date;
    }
    public Feedback (User user, java.lang.String message, boolean isCard, Date date, java.lang.String response) {
        this.user = user;
        this.message = message;
        this.isCard = isCard;
        this.date = date;
        this.response = response;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback feedback = (Feedback) o;

        return id.equals(feedback.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

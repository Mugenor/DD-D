package main.entities;

import javax.persistence.*;
import java.util.Date;

@Entity (name = "news")
public class News {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column
    private long id;
    @Column
    private String news;
    @Column
    private String topic;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public long getId() {
        return id;
    }
    public String getNews() {
        return news;
    }
    public void setNews(String news) {
        this.news = news;
    }
    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "{" +
                "id=\"" + id +
                "\", news=\"" + news +
                "\", topic=\"" + topic +
                "\", date=\"" + date +
                "\"}";
    }

    public News() {}
    public News (String news, String topic, Date date) {
        this.news = news;
        this.topic = topic;
        this.date = date;
    }
}

package main.entities;

import javax.persistence.*;
import java.util.Date;

@Entity (name = "news")
public class News {
    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "newsSeq")
    @SequenceGenerator(name = "newsSeq", sequenceName = "newsSeq")
    @Column
    private Long id;
    @Column(nullable = false)
    private String news;
    @Column (nullable = false)
    private String topic;
    @Column(nullable = false)
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
                "id:" + id +
                ", news:\"" + news +
                "\", topic:\"" + topic +
                "\", date:\"" + date +
                "\"}";
    }

    public News() {}
    public News (String news, String topic, Date date) {
        this.news = news;
        this.topic = topic;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        return id.equals(news.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

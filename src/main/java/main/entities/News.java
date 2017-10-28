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
    private Date date;
}

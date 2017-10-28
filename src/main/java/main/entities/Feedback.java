package main.entities;

import javax.persistence.*;
import java.util.Date;

@Entity (name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "ourUser")
    private User user;
    @Column
    private String message;
    @Column
    private boolean isCard;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column
    private String response;
}

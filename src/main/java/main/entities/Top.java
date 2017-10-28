package main.entities;

import javax.persistence.*;

@Entity (name = "top")
public class Top {
    @Id
    @Column
    private Integer position;
    @OneToOne
    @JoinColumn (name = "users")
    private User user;
}

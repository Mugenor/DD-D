package main.entities;

import javax.persistence.*;

@Entity (name = "top")
public class Top {
    @Id
    @Column
    private Integer position;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "login")
    private User user;
}

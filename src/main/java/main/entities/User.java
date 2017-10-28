package main.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity (name = "user")
public class User {
    @Id
    @Column
    private String login;
    @Column
    private long password;
    @Column
    private String username;
    @Column
    private int status;
    @Column
    private double winrate;
    @Column
    private long amountGames;
    @Column
    private int amountWin;
    @ManyToMany
    @JoinTable (name = "friends",
            joinColumns = @JoinColumn (name = "user1",
                    referencedColumnName = "login"),
            inverseJoinColumns = @JoinColumn (name = "user2",
                    referencedColumnName = "login"))
    private Collection<User> friends;

}

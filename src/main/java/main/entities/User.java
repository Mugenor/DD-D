package main.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity (name = "ourUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private long amountWin;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable (name = "friends",
            joinColumns = @JoinColumn (name = "user1",
                    referencedColumnName = "login"),
            inverseJoinColumns = @JoinColumn (name = "user2",
                    referencedColumnName = "login"))
    private Collection<User> friends;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "canUse",
        joinColumns = @JoinColumn (name = "ourUser", referencedColumnName = "login"),
        inverseJoinColumns = @JoinColumn(name = "card", referencedColumnName = "id"))
    private Collection<Card> cardsCanUse;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "inUse",
        joinColumns = @JoinColumn(name = "ourUser", referencedColumnName = "login"),
        inverseJoinColumns = @JoinColumn(name = "card", referencedColumnName = "id"))
    private Collection<Card> cardsInUse;

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public long getPassword() {
        return password;
    }
    public void setPassword(long password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public double getWinrate() {
        return winrate;
    }
    public void setWinrate(double winrate) {
        this.winrate = winrate;
    }
    public long getAmountGames() {
        return amountGames;
    }
    public void setAmountGames(long amountGames) {
        this.amountGames = amountGames;
    }
    public long getAmountWin() {
        return amountWin;
    }
    public void setAmountWin(long amountWin) {
        this.amountWin = amountWin;
    }
    public Collection<User> getFriends() {
        return friends;
    }
    public void setFriends(Collection<User> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "{" +
                "login=\"" + login +
                "\", password=\"******\"" +
                "\", username=\"" + username +
                "\", status=\"" + status +
                "\", winrate=\"" + winrate +
                "\", amountGames=\"" + amountGames +
                "\", amountWin=\"" + amountWin +
                "\", friends=\"" + friends +
                "\"}";
    }

    public User() {}
    public User(String login, long password, String username, int status, double winrate, long amountGames, long amountWin, Collection<User> friends){
        this.login = login;
        this.password = password;
        this.username = username;
        this.status = status;
        this.winrate = winrate;
        this.amountGames = amountGames;
        this.amountWin = amountWin;
        this.friends = friends;
    }
}

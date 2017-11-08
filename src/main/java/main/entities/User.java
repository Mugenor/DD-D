package main.entities;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Collection;

@Entity (name = "ourUser")
public class User {
    @Id
    @Column
    private String login;
    @Column (unique = true, nullable = false)
    private long password;
    @Column (unique = true, nullable = false)
    private String username;
    @Column (nullable = false)
    private int status;
    @Column (nullable = false)
    private double winrate;
    @Column (nullable = false)
    private long amountGames;
    @Column (nullable = false)
    private long amountWin;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable (name = "friends",
            joinColumns = @JoinColumn (name = "user1", referencedColumnName = "login"),
            inverseJoinColumns = @JoinColumn (name = "user2", referencedColumnName = "login"))
    private Collection<User> friends;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "awaitingFriends", joinColumns = @JoinColumn(name = "user1", referencedColumnName = "login"),
        inverseJoinColumns = @JoinColumn(name = "user2", referencedColumnName = "login"))
    private Collection<User> awaitingFriends;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "canUse",
        joinColumns = @JoinColumn (name = "ourUser", referencedColumnName = "login"),
        inverseJoinColumns = @JoinColumn(name = "card", referencedColumnName = "id"))
    private Collection<Card> cardsCanUse;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "inUse",
        joinColumns = @JoinColumn(name = "ourUser", referencedColumnName = "login"),
        inverseJoinColumns = @JoinColumn(name = "card", referencedColumnName = "id"))
    private Collection<Card> cardsInUse;

    public String getLogin() {
        return login;
    }
    public void setLogin (String login) { this.login = login; }
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
    public Collection<Card> getCardsCanUse() {
        return cardsCanUse;
    }
    public void setCardsCanUse(Collection<Card> cardsCanUse) {
        this.cardsCanUse = cardsCanUse;
    }
    public Collection<Card> getCardsInUse() {
        return cardsInUse;
    }
    public void setCardsInUse(Collection<Card> cardsInUse) {
        this.cardsInUse = cardsInUse;
    }
    public Collection<User> getAwaitingFriends() {
        return awaitingFriends;
    }
    public void setAwaitingFriends(Collection<User> awaitingFriends) {
        this.awaitingFriends = awaitingFriends;
    }

    @Override
    public String toString() {
        String str = "{login:\"" + login + "\", password:\"******\"" + "\", username:\"" + username +
                "\", status:" + status + ", winrate:" + winrate + ", amountGames:" + amountGames + ", amountWin:" + amountWin;
        if (Hibernate.isInitialized(friends)) str = str + ", friends:" + friends;
        if (Hibernate.isInitialized(awaitingFriends)) str = str + ", awaitingFriends:" + awaitingFriends;
        if (Hibernate.isInitialized(cardsCanUse)) str = str + ", cardsCanUse:" + cardsCanUse;
        if (Hibernate.isInitialized(cardsInUse)) str = str + ", cardsInUse:" + cardsInUse;
        str = str + "}";
        return str;
    }

    public User() {}

    public User(String login, long password, String username, int status, double winrate, long amountGames, long amountWin,
                Collection<User> friends, Collection<User> awaitingFriends){
        this.login = login;
        this.password = password;
        this.username = username;
        this.status = status;
        this.winrate = winrate;
        this.amountGames = amountGames;
        this.amountWin = amountWin;
        this.friends = friends;
        this.awaitingFriends = awaitingFriends;
    }

    public User(String login, long password, String username, int status, double winrate, long amountGames, long amountWin,
                Collection<User> friends,  Collection<User> awaitingFriends, Collection<Card> canUse, Collection<Card> inUse){
        this.login = login;
        this.password = password;
        this.username = username;
        this.status = status;
        this.winrate = winrate;
        this.amountGames = amountGames;
        this.amountWin = amountWin;
        this.friends = friends;
        this.awaitingFriends = awaitingFriends;
        this.cardsCanUse = canUse;
        this.cardsInUse = inUse;
    }
}

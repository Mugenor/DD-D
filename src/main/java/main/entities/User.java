package main.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Collection;

@Entity (name = "ourUser")
public class User {
    @Id
    @Column (name = "username", nullable = false, unique = true)
    protected java.lang.String username;
    @Column (nullable = false, unique = true)
    @JsonIgnore
    private java.lang.String mail;
    @Column (nullable = false)
    @JsonIgnore
    private java.lang.String password;
    @Column (name = "status")
    @ColumnDefault("0.0")
    @JsonIgnore
    private java.lang.String status;
    @Column (nullable = false)
    @ColumnDefault("0.0")
    protected double winrate;
    @Column (nullable = false)
    @ColumnDefault("0.0")
    protected long amountGames;
    @Column (nullable = false)
    @ColumnDefault("0.0")
    protected long amountWin;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable (name = "friends",
            joinColumns = @JoinColumn (name = "user1", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn (name = "user2", referencedColumnName = "username"))
    protected Collection<User> friends;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "awaitingFriends", joinColumns = @JoinColumn(name = "user1", referencedColumnName = "username"),
        inverseJoinColumns = @JoinColumn(name = "user2", referencedColumnName = "username"))
    private Collection<User> awaitingFriends;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "canUse",
        joinColumns = @JoinColumn (name = "ourUser", referencedColumnName = "username"),
        inverseJoinColumns = @JoinColumn(name = "card", referencedColumnName = "id"))
    protected Collection<Card> cardsCanUse;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "inUse",
        joinColumns = @JoinColumn(name = "ourUser", referencedColumnName = "username"),
        inverseJoinColumns = @JoinColumn(name = "card", referencedColumnName = "id"))
    protected Collection<Card> cardsInUse;

    public java.lang.String getPassword() {
        return password;
    }
    public void setPassword(java.lang.String password) {
        this.password = password;
    }
    public java.lang.String getUsername() {
        return username;
    }
    public void setUsername(java.lang.String username) {
        this.username = username;
    }
    public java.lang.String getStatus() {
        return status;
    }
    public void setStatus(java.lang.String status) {
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

    public java.lang.String getMail() {
        return mail;
    }

    public void setMail(java.lang.String mail) {
        this.mail = mail;
    }



    @Override
    public java.lang.String toString() {
        java.lang.String str = "{password:\"******\"" + "\", username:\"" + username +
                "\", status:" + status + ", winrate:" + winrate + ", amountGames:" + amountGames + ", amountWin:" + amountWin;
        if (Hibernate.isInitialized(awaitingFriends)) str = str + ", awaitingFriends:" + awaitingFriends;
        if (Hibernate.isInitialized(cardsCanUse)) str = str + ", cardsCanUse:" + cardsCanUse;
        if (Hibernate.isInitialized(cardsInUse)) str = str + ", cardsInUse:" + cardsInUse;
        str = str + "}";
        return str;
    }

    public User() {}

    public User(java.lang.String username, java.lang.String password, java.lang.String status, double winrate, long amountGames, long amountWin,
                Collection<User> friends, Collection<User> awaitingFriends){
        this.password = password;
        this.username = username;
        this.status = status;
        this.winrate = winrate;
        this.amountGames = amountGames;
        this.amountWin = amountWin;
        this.friends = friends;
        this.awaitingFriends = awaitingFriends;
    }

    public User(java.lang.String username, java.lang.String password, java.lang.String status, double winrate, long amountGames, long amountWin,
                Collection<User> friends, Collection<User> awaitingFriends, Collection<Card> canUse, Collection<Card> inUse){
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!this.username.equals(user.getUsername())) return false;
        return username.equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + username.hashCode();
        return result;
    }
}

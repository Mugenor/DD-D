package main.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class GameMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gameMatchSeq")
    @SequenceGenerator(name = "gameMatchSeq", sequenceName = "gameMatchSeq")
    @Column
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn (name = "player1", nullable = false)
    private User player1;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn (name = "player2", nullable = false)
    private User player2;

    public GameMatch() {}

    public GameMatch(User player1, User player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getPlayer1() {
        return player1;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || (getClass() != o.getClass())) return false;
        GameMatch gameMatch = (GameMatch) o;
        return Objects.equals(id, gameMatch.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

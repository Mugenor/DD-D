package main.entities;

import javax.persistence.*;

@Entity (name = "top")
public class Top {
    @Id
    @Column
    private Integer position;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn (name = "ourUser")
    private User user;

    public Integer getPosition() {
        return position;
    }
    public void setPosition(Integer position) {
        this.position = position;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "{" +
                "position=\"" + position +
                "\", user=\"" + user +
                "\"}";
    }

    public Top() {}
    public Top(int position, User user ){
        this.position = position;
        this.user = user;
    }
}

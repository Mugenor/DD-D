package main.entities;

import javax.persistence.*;

@Entity (name = "participation")
public class Participation {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_person")
    private Person person;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_episode")
    private Episode episode;
    private String role;

    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }
    public Episode getEpisode() {
        return episode;
    }
    public void setEpisode(Episode episode) {
        this.episode = episode;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "{" +
                "person=\"" + person +
                "\", episode=\"" + episode +
                "\", role=\"" + role +
                "\"}";
    }

    public Participation() {}
    public Participation(Person person, Episode episode, String role){
        this.person = person;
        this.episode = episode;
        this.role = role;
    }
}

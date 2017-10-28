package main.entities;

import javax.persistence.*;

@Entity(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "place_id")
    private Place place;

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Place getPlace() {
        return place;
    }
    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "{" +
                "id=\"" + id +
                "\", name=\"" + name +
                "\", place=\"" + place +
                "\"}";
    }

    public Position() {}
    public Position(String name, Place place){
        this.name = name;
        this.place = place;
    }
}

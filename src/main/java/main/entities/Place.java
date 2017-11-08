package main.entities;

import javax.persistence.*;

@Entity(name = "place")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "placeSeq")
    @SequenceGenerator(name = "placeSeq", sequenceName = "placeSeq")
    @Column(name = "id")
    private Integer id;
    @Column(name = "place", unique = true, nullable = false)
    private String place;

    public Integer getId() {
        return id;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", place:\"" + place +
                "\"}";
    }

    public Place() {}
    public Place(String place){
        this.place = place;
    }
}

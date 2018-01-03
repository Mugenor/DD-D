package main.entities;

import javax.persistence.*;

@Entity(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "positionSeq")
    @SequenceGenerator(name = "positionSeq", sequenceName = "positionSeq")
    @Column(name = "id")
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
                "id:" + id +
                ", name:\"" + name +
                "\", place:" + place +
                "}";
    }

    public Position() {}
    public Position(String name, Place place){
        this.name = name;
        this.place = place;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return id.equals(position.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

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
}

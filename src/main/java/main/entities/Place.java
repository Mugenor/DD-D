package main.entities;

import javax.persistence.*;

@Entity(name = "place")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "place")
    private String place;

}

package main.entities;

import javax.persistence.*;

@Entity(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "shortDescription")
    private String shortDescription;
    @Column(name = "picture")
    private String picture;
    @Column(name = "distance")
    private int distance;
}

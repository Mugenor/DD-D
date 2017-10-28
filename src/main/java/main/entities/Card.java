package main.entities;

import javax.persistence.*;

@Entity(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Creature creature;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Skill skill;
    @Column(name = "cubeNumber")
    private int cubeNumber;
}

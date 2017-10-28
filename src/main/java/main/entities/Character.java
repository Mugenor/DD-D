package main.entities;

import javax.persistence.*;

@Entity(name = "character")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "health")
    private int health;
    @Column(name = "description")
    private String description;
    @OneToOne
    @JoinColumn(name = "id")
    private Skill skill;
    @OneToOne
    @JoinColumn(name = "id")
    private Person person;
}

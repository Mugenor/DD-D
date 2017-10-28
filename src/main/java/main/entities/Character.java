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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Skill skill;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;
}

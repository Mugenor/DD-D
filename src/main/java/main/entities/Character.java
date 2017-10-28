package main.entities;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity(name = "character")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String name;
    private int health;
    private String description;
    private Skill skill;
    private Person person;
}

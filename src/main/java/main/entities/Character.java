package main.entities;

import javax.persistence.*;

@Entity(name = "character")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CharacterSeq")
    @SequenceGenerator(name = "CharacterSeq", sequenceName = "CharacterSeq")
    @Column(name = "id")
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "health", nullable = false)
    private int health;
    @Column(name = "description", nullable = false)
    private String description;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", unique = true, nullable = false)
    private Person person;

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Skill getSkill() {
        return skill;
    }
    public void setSkill(Skill skill) {
        this.skill = skill;
    }
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", name:\"" + name +
                "\", health:" + health +
                ", description:\"" + description +
                "\", skill:" + skill +
                ", person:" + person +
                "}";
    }

    public Character() {}
    public Character(String name, int health, String description, Skill skill, Person person) {
        this.name = name;
        this.description = description;
        this.health = health;
        this.skill = skill;
        this.person = person;
    }
}

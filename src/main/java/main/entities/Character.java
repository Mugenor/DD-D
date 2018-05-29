package main.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity(name = "character")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "characterSeq")
    @SequenceGenerator(name = "characterSeq", sequenceName = "characterSeq")
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "health", nullable = false)
    private int health;
    @Column(name = "description", nullable = false)
    private String description;

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.EAGER)

    @JoinColumn(name = "skill_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Skill skill;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", unique = true, nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Person person;

    public Integer getId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Character character = (Character) o;

        return id.equals(character.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Character(String name, int health, String description, Skill skill, Person person) {
        this.name = name;
        this.description = description;
        this.health = health;
        this.skill = skill;
        this.person = person;
    }
}

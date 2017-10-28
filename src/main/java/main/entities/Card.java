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
    @OneToOne
    @JoinColumn(name = "id")
    private Creature creature;
    @OneToOne
    @JoinColumn(name = "id")
    private Skill skill;
    @Column(name = "cubeNumber")
    private int cubeNumber;

    public void setName (String name) {this.name = name;}
    public String getName () {return name;}
    public void setDescription (String description) {this.description = description;}
    public String getDescription () {return description;}
    public Creature getCreature() {
        return creature;
    }
    public void setCreature(Creature creature) {
        this.creature = creature;
    }
    public Skill getSkill() {
        return skill;
    }
    public void setSkill(Skill skill) {
        this.skill = skill;
    }
    public int getCubeNumber() {
        return cubeNumber;
    }
    public void setCubeNumber(int cubeNumber) {
        this.cubeNumber = cubeNumber;
    }
    public static void main(String [] args) {
        Card card = new Card("name", "desc", new Creature(), new Skill(), 5);
        System.out.println(card.toString());
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creature=" + creature +
                ", skill=" + skill +
                ", cubeNumber=" + cubeNumber +
                '}';
    }

    public Card() {}
    public Card (String name, String description, Creature creature, Skill skill, int cubeNumber) {
        this.name = name;
        this.description = description;
        this.creature = creature;
        this.skill = skill;
        this.cubeNumber = cubeNumber;
    }
}

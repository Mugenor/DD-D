package main.entities;

import javax.persistence.*;

@Entity(name = "creature")
public class Creature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "creatureSeq")
    @SequenceGenerator(name = "creatureSeq", sequenceName = "creatureSeq")
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private int amount;
    @Column(name = "picture")
    private String picture;
    @Column(name = "description")
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Diary getDiary() {
        return diary;
    }
    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    @Override
    public String toString() {
        return "{" +
                "id=\"" + id +
                "\", name=\"" + name +
                "\", amount=\"" + amount +
                "\", picture=\"" + picture +
                "\", description=\"" + description +
                "\", diary=\"" + diary +
                "\"}";
    }

    public Creature() {}
    public Creature (String name, int amount, String picture, String description, Diary diary) {
        this.name = name;
        this.amount = amount;
        this.picture = picture;
        this.description = description;
        this.diary = diary;
    }
}

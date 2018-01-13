package main.entities;

import org.hibernate.Hibernate;

import javax.persistence.*;

@Entity(name = "creature")
public class Creature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "creatureSeq")
    @SequenceGenerator(name = "creatureSeq", sequenceName = "creatureSeq")
    @Column(name = "id")
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "amount", nullable = false)
    private int amount;
    @Column(name = "picture", unique = true)
    private String picture;
    @Column(name = "description", unique = true, nullable = false)
    private String description;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "episode_id", unique = true, nullable = false)
    private Episode episode;


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
    public Episode getEpisode() {
        return episode;
    }
    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    @Override
    public String toString() {
        String str = "{" +
                "id:" + id +
                ", name:\"" + name +
                "\", amount:" + amount +
                ", picture:\"" + picture +
                "\", description:\"" + description +
                "\"";
        if (Hibernate.isInitialized(diary)) str = str + ", diary:" + diary;
        if (Hibernate.isInitialized(episode)) str = str + ", episode:" + episode;
        str = str + "}";
        return str;
    }

    public Creature() {}
    public Creature (String  name, int amount, String picture, String description, Diary diary, Episode episode) {
        this.name = name;
        this.amount = amount;
        this.picture = picture;
        this.description = description;
        this.diary = diary;
        this.episode = episode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Creature creature = (Creature) o;

        return id.equals(creature.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

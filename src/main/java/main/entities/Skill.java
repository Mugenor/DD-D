package main.entities;

import javax.persistence.*;

@Entity(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "skillSeq")
    @SequenceGenerator(name = "skillSeq", sequenceName = "skillSeq")
    @Column(name = "id")
    private Long id;
    @Column(name = "description", unique = true, nullable = false)
    private String description;
    @Column(name = "shortDescription", unique = true, nullable = false)
    private String shortDescription;
    @Column(name = "picture", unique = true, nullable = false)
    private String picture;
    @Column(name = "distance", nullable = false)
    private int distance;

    public Long getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public int getDistance() {
        return distance;
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", description:\"" + description +
                "\", shortDescription:\"" + shortDescription +
                "\", picture:\"" + picture +
                "\", distance:" + distance +
                "}";
    }

    public Skill() {}
    public Skill(String description, String shortDescription, String picture, int distance){
        this.description = description;
        this.shortDescription = shortDescription;
        this.picture = picture;
        this.distance = distance;
    }
}

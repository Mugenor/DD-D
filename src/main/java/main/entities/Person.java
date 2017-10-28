package ent;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "sex")
    private boolean sex;
    @Column(name = "age")
    private int age;
    @Column(name = "picture")
    private String picture;
    @ManyToMany
    @JoinTable(name = "pers_feat", joinColumns = @JoinColumn(name = "person", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "feature", referencedColumnName = "id"))
    private Collection<Feature> features;
    @ManyToOne
    @JoinColumn(name = "id")
    private Position position;


    public Person(String name, boolean sex, int age, String picture){
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

package main.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "person")
public class Person {
    @Transient
    public static final boolean MALE = true;
    @Transient
    public static final boolean FEMALE = false;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "personSeq")
    @SequenceGenerator(name = "personSeq", sequenceName = "personSeq")
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
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "pers_feat", joinColumns = @JoinColumn(name = "person", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "feature", referencedColumnName = "id"))
    private Collection<Feature> features;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

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
    public Collection<Feature> getFeatures() {
        return features;
    }
    public void setFeatures(Collection<Feature> features) {
        this.features = features;
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "{" +
                "id=\"" + id +
                "\", name=\"" + name +
                "\", sex=\"" + sex +
                "\", age=\"" + age +
                "\", picture=\"" + picture +
                "\", features=\"" + features +
                "\", position=\"" + position +
                "\"}";
    }

    public Person() {}
    public Person(String name, boolean sex, int age, String picture, Position position){
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.picture = picture;
        this.position = position;
    }
    public Person(String name, boolean sex, int age, String picture, Collection<Feature> features, Position position){
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.picture = picture;
        this.features = features;
        this.position = position;
    }
}

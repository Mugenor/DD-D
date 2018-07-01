package main.entities;


import javax.persistence.*;

@Entity(name = "card")
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cardSeq")
    @SequenceGenerator(name = "cardSeq", sequenceName = "cardSeq")
    @Column(name = "id")
    private Integer id;
    private Integer cubeNumber;
    private String name;
    private String image;
    private String description;
    private Integer damage;

    public Integer getCubeNumber() {
        return cubeNumber;
    }

    public void setCubeNumber(Integer cubeNumber) {
        this.cubeNumber = cubeNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }


}

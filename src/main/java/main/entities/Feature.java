package main.entities;

import javax.persistence.*;

@Entity(name = "feature")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "featureSeq")
    @SequenceGenerator(name = "featureSeq", sequenceName = "featureSeq")
    @Column(name = "id")
    private Long id;
    @Column(name = "description", unique = true, nullable = false)
    private String description;

    public Long getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", description:\"" + description +
                "\"}";
    }

    public Feature() {}
    public Feature (String description) {
        this.description = description;
    }
}

package main.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "episode")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "id")
    private Creature creature;
    @Column(name = "cause")
    private String cause;
    @Column(name = "problem")
    private String problem;
    @Column(name = "solution")
    private String solution;
    @OneToOne
    @JoinColumn(name = "id")
    private Person solver;
    @Column(name = "series")
    private int series;
    @ManyToMany
    @JoinTable(name = "epis_place", joinColumns = @JoinColumn(name = "episode", referencedColumnName = "id"), inverseJoinColumns =
        @JoinColumn(name = "place", referencedColumnName = "id"))
    private Collection<Place> place;
}

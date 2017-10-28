package main.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "episode")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creature_id")
    private Creature creature;
    @Column(name = "cause")
    private String cause;
    @Column(name = "problem")
    private String problem;
    @Column(name = "solution")
    private String solution;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "solver_id")
    private Person solver;
    @Column(name = "series")
    private int series;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "epis_place", joinColumns = @JoinColumn(name = "episode", referencedColumnName = "id"), inverseJoinColumns =
        @JoinColumn(name = "place", referencedColumnName = "id"))
    private Collection<Place> place;
}

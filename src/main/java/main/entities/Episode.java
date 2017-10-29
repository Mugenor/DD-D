package main.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "episode")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "episodeSeq")
    @SequenceGenerator(name = "episodeSeq", sequenceName = "episodeSeq")
    @Column(name = "id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "creature_id")
    private Creature creature;
    @Column(name = "cause")
    private String cause;
    @Column(name = "problem")
    private String problem;
    @Column(name = "solution")
    private String solution;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "solver_id")
    private Person solver;
    @Column(name = "series")
    private int series;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "epis_place", joinColumns = @JoinColumn(name = "episode", referencedColumnName = "id"), inverseJoinColumns =
        @JoinColumn(name = "places", referencedColumnName = "id"))
    private Collection<Place> places;

    public Collection<Person> getParticipants() {
        return participants;
    }

    public void setParticipants(Collection<Person> participants) {
        this.participants = participants;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "epis_person", joinColumns = @JoinColumn(name = "episode", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name="person", referencedColumnName = "id"))
    private Collection<Person> participants;

    public Long getId() {
        return id;
    }
    public Creature getCreature() {
        return creature;
    }
    public void setCreature(Creature creature) {
        this.creature = creature;
    }
    public String getCause() {
        return cause;
    }
    public void setCause(String cause) {
        this.cause = cause;
    }
    public String getProblem() {
        return problem;
    }
    public void setProblem(String problem) {
        this.problem = problem;
    }
    public String getSolution() {
        return solution;
    }
    public void setSolution(String solution) {
        this.solution = solution;
    }
    public Person getSolver() {
        return solver;
    }
    public void setSolver(Person solver) {
        this.solver = solver;
    }
    public int getSeries() {
        return series;
    }
    public void setSeries(int series) {
        this.series = series;
    }
    public Collection<Place> getPlaces() {
        return places;
    }
    public void setPlaces(Collection<Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "{" +
                "id=\"" + id +
                "\", creature=\"" + creature +
                "\", cause=\"" + cause +
                "\", problem=\"" + problem +
                "\", solution=\"" + solution +
                "\", solver=\"" + solver +
                "\", series=\"" + series +
                "\", places=\"" + places +
                "\"}";
    }

    public Episode() {}
    public Episode (Creature creature, String cause, String problem, String solution, Person solver, int series, Collection<Place> places) {
        this.creature = creature;
        this.cause = cause;
        this.problem = problem;
        this.solution = solution;
        this.solver = solver;
        this.series = series;
        this.places = places;
    }
}

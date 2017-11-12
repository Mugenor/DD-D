package main.entities;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "diarySeq")
    @SequenceGenerator(name = "diarySeq", sequenceName = "diarySeq")
    @Column(name = "id")
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Person author;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "where_id")
    private Place where;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "finder_id", unique = true, nullable = false)
    private Person finder;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "id")
    private Collection<Creature> creatures;

    public Integer getId() {
        return id;
    }
    public Person getAuthor() {
        return author;
    }
    public void setAuthor(Person author) {
        this.author = author;
    }
    public Place getWhere() {
        return where;
    }
    public void setWhere(Place where) {
        this.where = where;
    }
    public Person getFinder() {
        return finder;
    }
    public void setFinder(Person finder) {
        this.finder = finder;
    }
    public Collection<Creature> getCreatures() {
        return creatures;
    }
    public void setCreatures(Collection<Creature> creatures) {
        this.creatures = creatures;
    }

    @Override
    public String toString() {
        String str = "{id:" + id ;
        if (Hibernate.isInitialized(author)) str = str + ", author:" + author;
        if (Hibernate.isInitialized(where)) str = str + ", where:" + where;
        if (Hibernate.isInitialized(finder)) str = str + ", finder:" + finder;
        str = str + ", creatures:" + creatures.toString() + "}";
        return str;
    }

    public Diary() {}
    public Diary (Person author, Place where, Person finder) {
        this.author = author;
        this.where = where;
        this.finder = finder;
    }
    public Diary (Person author, Place where, Person finder, Collection<Creature> creatures) {
        this.author = author;
        this.where = where;
        this.finder = finder;
        this.creatures = creatures;
    }
}

package main.entities;

import javax.persistence.*;

@Entity(name = "diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Person author;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "where_id")
    private Place where;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "finder_id")
    private Person finder;

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

    @Override
    public String toString() {
        return "{" +
                "id=\"" + id +
                "\", author=\"" + author +
                "\", where=\"" + where +
                "\", finder=\"" + finder +
                "\"}";
    }

    public Diary() {}
    public Diary (Person author, Place where, Person finder) {
        this.author = author;
        this.where = where;
        this.finder = finder;
    }
}

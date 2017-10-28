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
}

package ent;

import javax.persistence.*;

@Entity(name = "diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "id")
    private Person author;
    @OneToOne
    @JoinColumn(name = "id")
    private Place where;
    @OneToOne
    @JoinColumn(name = "id")
    private Person finder;
}

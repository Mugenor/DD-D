package ent;

import javax.persistence.*;

@Entity(name = "creature")
public class Creature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private int amount;
    @Column(name = "picture")
    private String picture;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "id")
    private Diary diary;
}

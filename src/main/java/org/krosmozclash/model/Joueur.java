package org.krosmozclash.model;
import javax.persistence.*;

@Entity
public class Joueur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pseudo;
    private int age;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    // Getters and Setters
}

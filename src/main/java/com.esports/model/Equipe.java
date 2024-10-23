package com.esports.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
    private List<Joueur> joueurs;

    @ManyToMany(mappedBy = "equipes")
    private List<Tournoi> tournois;

    // Getters and Setters
}

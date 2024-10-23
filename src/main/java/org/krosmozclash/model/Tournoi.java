package org.krosmozclash.model;

import java.util.List;

public class Tournoi {
    private List<Equipe> equipes;
    private Jeu jeu;
    private long tempsPauseEntreMatchs;
    private long tempsCeremonie;

    // Getter for equipes
    public List<Equipe> getEquipes() {
        return equipes;
    }

    // Getter for jeu
    public Jeu getJeu() {
        return jeu;
    }

    // Getter for tempsPauseEntreMatchs
    public long getTempsPauseEntreMatchs() {
        return tempsPauseEntreMatchs;
    }

    // Getter for tempsCeremonie
    public long getTempsCeremonie() {
        return tempsCeremonie;
    }
}

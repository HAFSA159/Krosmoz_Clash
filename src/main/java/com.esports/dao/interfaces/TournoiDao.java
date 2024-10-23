package com.esports.dao.interfaces;

import org.esport.model.Equipe;
import org.esport.model.Tournoi;

import java.util.List;
import java.util.Optional;

public interface TournoiDao {
    Tournoi creer(Tournoi tournoi);

    Tournoi modifier(Tournoi tournoi);

    void supprimer(Long id);

    Optional<Tournoi> trouverParId(Long id);

    List<Tournoi> trouverTous();

    void ajouterEquipe(Long tournoiId, Equipe equipe);

    void retirerEquipe(Long tournoiId, Equipe equipe);

    int calculerdureeEstimeeTournoi(Long tournoiId);
}

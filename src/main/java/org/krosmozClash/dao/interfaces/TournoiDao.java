package org.krosmozClash.dao.interfaces;

import org.krosmozClash.model.Equipe;
import org.krosmozClash.model.Tournoi;

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

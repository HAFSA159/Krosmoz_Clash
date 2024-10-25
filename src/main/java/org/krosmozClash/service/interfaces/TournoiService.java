package org.krosmozClash.service.interfaces;

import org.krosmozClash.model.Tournoi;
import org.krosmozClash.model.enums.TournoiStatus;

import java.util.List;
import java.util.Optional;

public interface TournoiService {
    Tournoi creerTournoi(Tournoi tournoi);

    Tournoi modifierTournoi(Tournoi tournoi);

    void supprimerTournoi(Long id);

    Optional<Tournoi> obtenirTournoi(Long id);

    List<Tournoi> obtenirTousTournois();

    void ajouterEquipe(Long tournoiId, Long equipeId);

    void retirerEquipe(Long tournoiId, Long equipeId);

    int calculerdureeEstimeeTournoi(Long tournoiId);

    void modifierStatutTournoi(Long tournoiId, TournoiStatus nouveauStatut);
}

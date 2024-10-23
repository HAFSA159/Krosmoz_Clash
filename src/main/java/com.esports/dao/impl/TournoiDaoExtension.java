package com.esports.dao.impl;

import com.esports.model.Tournoi;

public class TournoiDaoExtension extends TournoiDaoImpl {

    @Override
    public long calculerdureeEstimeeTournoi(Long tournoiId) {
        Tournoi tournoi = super.entityManager.find(Tournoi.class, tournoiId); // No change needed here
        if (tournoi == null) return 0;

        long duree = tournoi.getEquipes().size() * tournoi.getJeu().getDureeMoyenneMatch() * tournoi.getJeu().getDifficulte();
        return duree + tournoi.getTempsPauseEntreMatchs() + tournoi.getTempsCeremonie();
    }
}

package com.esports.dao.impl;

import com.esports.model.Tournoi;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class TournoiDaoImpl implements TournoiDao {

    @PersistenceContext
    protected EntityManager entityManager; // Changed to protected

    @Override
    public long calculerdureeEstimeeTournoi(Long tournoiId) {
        Tournoi tournoi = entityManager.find(Tournoi.class, tournoiId);
        if (tournoi == null) return 0;

        long duree = tournoi.getEquipes().size() * tournoi.getJeu().getDureeMoyenneMatch();
        return duree + tournoi.getTempsPauseEntreMatchs();
    }

    // Other methods...
}

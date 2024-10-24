package org.krosmozClash.service.impl;

import org.krosmozClash.dao.interfaces.EquipeDao;
import org.krosmozClash.dao.interfaces.JoueurDao;
import org.krosmozClash.model.Equipe;
import org.krosmozClash.model.Joueur;
import org.krosmozClash.service.interfaces.EquipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class EquipeServiceImpl implements EquipeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipeServiceImpl.class);

    private final EquipeDao equipeDao;
    private final JoueurDao joueurDao;

    public EquipeServiceImpl(EquipeDao equipeDao, JoueurDao joueurDao) {
        this.equipeDao = equipeDao;
        this.joueurDao = joueurDao;
    }

    @Override
    public Equipe creerEquipe(Equipe equipe) {
        LOGGER.info("Création d'une nouvelle équipe");
        return equipeDao.creer(equipe);
    }

    @Override
    public Equipe modifierEquipe(Equipe equipe) {
        LOGGER.info("Modification de l'équipe avec l'ID: {}", equipe.getId());
        return equipeDao.modifier(equipe);
    }

    @Override
    public void supprimerEquipe(Long id) {
        LOGGER.info("Suppression de l'équipe avec l'ID: {}", id);
        equipeDao.supprimer(id);
    }

    @Override
    public Optional<Equipe> obtenirEquipe(Long id) {
        LOGGER.info("Recherche de l'équipe avec l'ID: {}", id);
        return equipeDao.trouverParId(id);
    }

    @Override
    public List<Equipe> obtenirToutesEquipes() {
        LOGGER.info("Récupération de toutes les équipes");
        return equipeDao.trouverTous();
    }

    @Override
    public void ajouterJoueur(Long equipeId, Long joueurId) {
        LOGGER.info("Ajout du joueur {} à l'équipe {}", joueurId, equipeId);
        Optional<Joueur> joueur = joueurDao.trouverParId(joueurId);
        if (joueur.isPresent()) {
            equipeDao.ajouterJoueur(equipeId, joueur.get());
        } else {
            LOGGER.warn("Joueur avec l'ID {} non trouvé", joueurId);
        }
    }

    @Override
    public void retirerJoueur(Long equipeId, Long joueurId) {
        LOGGER.info("Retrait du joueur {} de l'équipe {}", joueurId, equipeId);
        Optional<Joueur> joueur = joueurDao.trouverParId(joueurId);
        if (joueur.isPresent()) {
            equipeDao.retirerJoueur(equipeId, joueur.get());
        } else {
            LOGGER.warn("Joueur avec l'ID {} non trouvé", joueurId);
        }
    }

    @Override
    public List<Equipe> obtenirEquipesParTournoi(Long tournoiId) {
        LOGGER.info("Récupération des équipes pour le tournoi avec l'ID: {}", tournoiId);
        return equipeDao.trouverParTournoi(tournoiId);
    }
}

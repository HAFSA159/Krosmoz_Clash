package org.krosmozclash.presentation.presentation.menu;

import org.krosmozclash.service.interfaces.EquipeService;
import org.krosmozclash.service.interfaces.JeuService;
import org.krosmozclash.service.interfaces.JoueurService;
import org.krosmozclash.service.interfaces.TournoiService;

public class MainMenu {
    private JoueurService joueurService;
    private EquipeService equipeService;
    private TournoiService tournoiService;
    private JeuService jeuService;

    // Setters for dependency injection
    public void setJoueurService(JoueurService joueurService) {
        this.joueurService = joueurService;
    }

    public void setEquipeService(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    public void setTournoiService(TournoiService tournoiService) {
        this.tournoiService = tournoiService;
    }

    public void setJeuService(JeuService jeuService) {
        this.jeuService = jeuService;
    }

    public void start() {
        System.out.println("Welcome to the E-Sports Tournament Management System");
        // Add your menu logic here
    }
}

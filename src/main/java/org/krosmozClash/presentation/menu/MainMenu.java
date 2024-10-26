package org.krosmozClash.presentation.menu;

import org.krosmozClash.controller.EquipeController;
import org.krosmozClash.controller.JeuController;
import org.krosmozClash.controller.JoueurController;
import org.krosmozClash.controller.TournoiController;
import org.krosmozClash.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class MainMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainMenu.class);
    private final JoueurController joueurController;
    private final EquipeController equipeController;
    private final TournoiController tournoiController;
    private final JeuController jeuController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public MainMenu(JoueurController joueurController, EquipeController equipeController,
                    TournoiController tournoiController, JeuController jeuController,
                    ConsoleLogger consoleLogger) {
        this.joueurController = joueurController;
        this.equipeController = equipeController;
        this.tournoiController = tournoiController;
        this.jeuController = jeuController;
        this.consoleLogger = consoleLogger;
        this.scanner = new Scanner(System.in);
    }

    public void afficherMenuPrincipal() {
        boolean continuer = true;
        while (continuer) {
            consoleLogger.afficherMessage("Main Menu:");
            consoleLogger.afficherMessage("1. Player Management");
            consoleLogger.afficherMessage("2. Team Management");
            consoleLogger.afficherMessage("3. Tournament Management");
            consoleLogger.afficherMessage("4. Game Management");
            consoleLogger.afficherMessage("0. Quit");
            consoleLogger.afficherMessage("Choose an option:");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    new JoueurMenu(joueurController, consoleLogger, scanner).afficherMenu();
                    break;
                case 2:
                    new EquipeMenu(equipeController, joueurController, consoleLogger, scanner).afficherMenu();
                    break;
                case 3:
                    new TournoiMenu(tournoiController, equipeController, jeuController, consoleLogger, scanner)
                            .afficherMenu();
                    break;
                case 4:
                    new JeuMenu(jeuController, consoleLogger, scanner).afficherMenu();
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    consoleLogger.afficherErreur("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}

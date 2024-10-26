package org.krosmozClash.presentation.menu;

import org.krosmozClash.controller.JeuController;
import org.krosmozClash.model.Jeu;
import org.krosmozClash.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class JeuMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(JeuMenu.class);
    private final JeuController jeuController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public JeuMenu(JeuController jeuController, ConsoleLogger consoleLogger, Scanner scanner) {
        this.jeuController = jeuController;
        this.consoleLogger = consoleLogger;
        this.scanner = scanner;
    }
    public void afficherMenu() {
        boolean continuer = true;
        while (continuer) {
            consoleLogger.afficherMessage("Game Menu:");
            consoleLogger.afficherMessage("1. Create a game");
            consoleLogger.afficherMessage("2. Edit a game");
            consoleLogger.afficherMessage("3. Delete a game");
            consoleLogger.afficherMessage("4. View a game");
            consoleLogger.afficherMessage("5. View all games");
            consoleLogger.afficherMessage("0. Return to main menu");
            consoleLogger.afficherMessage("Choose an option:");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    creerJeu();
                    break;
                case 2:
                    modifierJeu();
                    break;
                case 3:
                    supprimerJeu();
                    break;
                case 4:
                    afficherJeu();
                    break;
                case 5:
                    afficherTousJeux();
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    consoleLogger.afficherErreur("Invalid option. Please try again.");
            }
        }
    }

    private void creerJeu() {
        consoleLogger.afficherMessage("Creating a new game");
        consoleLogger.afficherMessage("Enter the game name:");
        String nom = scanner.nextLine();
        consoleLogger.afficherMessage("Enter the game difficulty (1-10):");
        int difficulte = scanner.nextInt();
        consoleLogger.afficherMessage("Enter the average duration of a match (in minutes):");
        int dureeMoyenneMatch = scanner.nextInt();
        scanner.nextLine();

        Jeu nouveauJeu = jeuController.creerJeu(nom, difficulte, dureeMoyenneMatch);
        if (nouveauJeu != null) {
            consoleLogger.afficherMessage("Game created successfully. ID: " + nouveauJeu.getId());
        } else {
            consoleLogger.afficherErreur("Error while creating the game.");
        }
    }

    private void modifierJeu() {
        consoleLogger.afficherMessage("Editing a game");
        consoleLogger.afficherMessage("Enter the game ID to edit:");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Optional<Jeu> jeuOptional = jeuController.obtenirJeu(id);
        if (jeuOptional.isPresent()) {
            Jeu jeu = jeuOptional.get();
            consoleLogger.afficherMessage("Game found: " + jeu.getNom());
            consoleLogger.afficherMessage("Enter the new game name (or press Enter to keep the current one):");
            String nouveauNom = scanner.nextLine();
            if (nouveauNom.isEmpty()) {
                nouveauNom = jeu.getNom();
            }

            consoleLogger.afficherMessage("Enter the new game difficulty (1-10) (or -1 to keep the current one):");
            int nouvelleDifficulte = scanner.nextInt();
            if (nouvelleDifficulte == -1) {
                nouvelleDifficulte = jeu.getDifficulte();
            }

            consoleLogger.afficherMessage("Enter the new average match duration (in minutes) (or -1 to keep the current one):");
            int nouvelleDureeMoyenneMatch = scanner.nextInt();
            if (nouvelleDureeMoyenneMatch == -1) {
                nouvelleDureeMoyenneMatch = jeu.getDureeMoyenneMatch();
            }
            scanner.nextLine();

            Jeu jeuModifie = jeuController.modifierJeu(id, nouveauNom, nouvelleDifficulte, nouvelleDureeMoyenneMatch);
            if (jeuModifie != null) {
                consoleLogger.afficherMessage("Game updated successfully.");
            } else {
                consoleLogger.afficherErreur("Error while updating the game.");
            }
        } else {
            consoleLogger.afficherErreur("Game not found.");
        }
    }

    private void supprimerJeu() {
        consoleLogger.afficherMessage("Deleting a game");
        consoleLogger.afficherMessage("Enter the game ID to delete:");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Optional<Jeu> jeuOptional = jeuController.obtenirJeu(id);
        if (jeuOptional.isPresent()) {
            consoleLogger.afficherMessage("Are you sure you want to delete the game " + jeuOptional.get().getNom() + "? (Y/N)");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("Y")) {
                jeuController.supprimerJeu(id);
                consoleLogger.afficherMessage("Game deleted successfully.");
            } else {
                consoleLogger.afficherMessage("Deletion canceled.");
            }
        } else {
            consoleLogger.afficherErreur("Game not found.");
        }
    }

    private void afficherJeu() {
        consoleLogger.afficherMessage("Viewing a game");
        consoleLogger.afficherMessage("Enter the game ID to view:");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Optional<Jeu> jeuOptional = jeuController.obtenirJeu(id);
        if (jeuOptional.isPresent()) {
            Jeu jeu = jeuOptional.get();
            consoleLogger.afficherMessage("Game details:");
            consoleLogger.afficherMessage("ID: " + jeu.getId());
            consoleLogger.afficherMessage("Name: " + jeu.getNom());
            consoleLogger.afficherMessage("Difficulty: " + jeu.getDifficulte());
            consoleLogger.afficherMessage("Average match duration: " + jeu.getDureeMoyenneMatch() + " minutes");
        } else {
            consoleLogger.afficherErreur("Game not found.");
        }
    }

    private void afficherTousJeux() {
        consoleLogger.afficherMessage("List of all games:");
        List<Jeu> jeux = jeuController.obtenirTousJeux();
        if (!jeux.isEmpty()) {
            for (Jeu jeu : jeux) {
                consoleLogger.afficherMessage("ID: " + jeu.getId() + ", Name: " + jeu.getNom() + ", Difficulty: "
                        + jeu.getDifficulte() + ", Average duration: " + jeu.getDureeMoyenneMatch() + " minutes");
            }
        } else {
            consoleLogger.afficherMessage("No games found.");
        }
    }

}

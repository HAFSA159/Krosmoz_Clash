package org.krosmozClash.presentation.menu;

import org.krosmozClash.controller.JoueurController;
import org.krosmozClash.model.Joueur;
import org.krosmozClash.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class JoueurMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(JoueurMenu.class);
    private final JoueurController joueurController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public JoueurMenu(JoueurController joueurController, ConsoleLogger consoleLogger, Scanner scanner) {
        this.joueurController = joueurController;
        this.consoleLogger = consoleLogger;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        boolean continuer = true;
        while (continuer) {
            consoleLogger.afficherMessage("Player Menu:");
            consoleLogger.afficherMessage("1. Register a player");
            consoleLogger.afficherMessage("2. Edit a player");
            consoleLogger.afficherMessage("3. Delete a player");
            consoleLogger.afficherMessage("4. View a player");
            consoleLogger.afficherMessage("5. View all players");
            consoleLogger.afficherMessage("6. View players in a team");
            consoleLogger.afficherMessage("0. Return to main menu");
            consoleLogger.afficherMessage("Choose an option:");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consume the new line

            switch (choix) {
                case 1:
                    inscrireJoueur();
                    break;
                case 2:
                    modifierJoueur();
                    break;
                case 3:
                    supprimerJoueur();
                    break;
                case 4:
                    afficherJoueur();
                    break;
                case 5:
                    afficherTousJoueurs();
                    break;
                case 6:
                    afficherJoueursParEquipe();
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    consoleLogger.afficherErreur("Invalid option. Please try again.");
            }
        }
    }

    private void inscrireJoueur() {
        consoleLogger.afficherMessage("Enter the player's username:");
        String pseudo = scanner.nextLine();
        consoleLogger.afficherMessage("Enter the player's age:");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the new line

        Joueur nouveauJoueur = joueurController.inscrireJoueur(pseudo, age);
        if (nouveauJoueur != null) {
            consoleLogger.afficherMessage("Player successfully registered. ID: " + nouveauJoueur.getId());
        } else {
            consoleLogger.afficherErreur("Player registration failed.");
        }
    }

    private void modifierJoueur() {
        consoleLogger.afficherMessage("Enter the ID of the player to edit:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the new line

        consoleLogger.afficherMessage("Enter the new username of the player:");
        String nouveauPseudo = scanner.nextLine();
        consoleLogger.afficherMessage("Enter the new age of the player:");
        int nouvelAge = scanner.nextInt();
        scanner.nextLine(); // Consume the new line

        Joueur joueurModifie = joueurController.modifierJoueur(id, nouveauPseudo, nouvelAge);
        if (joueurModifie != null) {
            consoleLogger.afficherMessage("Player successfully updated.");
        } else {
            consoleLogger.afficherErreur("Player update failed.");
        }
    }

    private void supprimerJoueur() {
        consoleLogger.afficherMessage("Enter the ID of the player to delete:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the new line

        joueurController.supprimerJoueur(id);
        consoleLogger.afficherMessage("Player successfully deleted.");
    }

    private void afficherJoueur() {
        consoleLogger.afficherMessage("Enter the ID of the player to view:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the new line

        Optional<Joueur> joueurOptional = joueurController.obtenirJoueur(id);
        if (joueurOptional.isPresent()) {
            Joueur joueur = joueurOptional.get();
            consoleLogger.afficherMessage("Player details:");
            consoleLogger.afficherMessage("ID: " + joueur.getId());
            consoleLogger.afficherMessage("Username: " + joueur.getPseudo());
            consoleLogger.afficherMessage("Age: " + joueur.getAge());
        } else {
            consoleLogger.afficherErreur("Player not found.");
        }
    }

    private void afficherTousJoueurs() {
        List<Joueur> joueurs = joueurController.obtenirTousJoueurs();
        if (!joueurs.isEmpty()) {
            consoleLogger.afficherMessage("List of all players:");
            for (Joueur joueur : joueurs) {
                consoleLogger.afficherMessage(
                        "ID: " + joueur.getId() + ", Username: " + joueur.getPseudo() + ", Age: " + joueur.getAge());
            }
        } else {
            consoleLogger.afficherMessage("No players found.");
        }
    }

    private void afficherJoueursParEquipe() {
        consoleLogger.afficherMessage("Enter the team ID:");
        Long equipeId = scanner.nextLong();
        scanner.nextLine();

        List<Joueur> joueurs = joueurController.obtenirJoueursParEquipe(equipeId);
        if (!joueurs.isEmpty()) {
            consoleLogger.afficherMessage("Players in team (ID: " + equipeId + "):");
            for (Joueur joueur : joueurs) {
                consoleLogger.afficherMessage(
                        "ID: " + joueur.getId() + ", Username: " + joueur.getPseudo() + ", Age: " + joueur.getAge());
            }
        } else {
            consoleLogger.afficherMessage("No players found for this team.");
        }
    }
}

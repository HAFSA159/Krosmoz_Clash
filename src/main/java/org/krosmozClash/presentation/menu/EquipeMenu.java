package org.krosmozClash.presentation.menu;

import org.krosmozClash.controller.EquipeController;
import org.krosmozClash.controller.JoueurController;
import org.krosmozClash.model.Equipe;
import org.krosmozClash.model.Joueur;
import org.krosmozClash.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EquipeMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquipeMenu.class);
    private final EquipeController equipeController;
    private final JoueurController joueurController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public EquipeMenu(EquipeController equipeController, JoueurController joueurController, ConsoleLogger consoleLogger,
            Scanner scanner) {
        this.equipeController = equipeController;
        this.joueurController = joueurController;
        this.consoleLogger = consoleLogger;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        boolean continuer = true;
        while (continuer) {
            consoleLogger.afficherMessage("Team Menu:");
            consoleLogger.afficherMessage("1. Create a team");
            consoleLogger.afficherMessage("2. Edit a team");
            consoleLogger.afficherMessage("3. Delete a team");
            consoleLogger.afficherMessage("4. View a team");
            consoleLogger.afficherMessage("5. View all teams");
            consoleLogger.afficherMessage("6. Add a player to a team");
            consoleLogger.afficherMessage("7. Remove a player from a team");
            consoleLogger.afficherMessage("0. Return to main menu");
            consoleLogger.afficherMessage("Choose an option:");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    creerEquipe();
                    break;
                case 2:
                    modifierEquipe();
                    break;
                case 3:
                    supprimerEquipe();
                    break;
                case 4:
                    afficherEquipe();
                    break;
                case 5:
                    afficherToutesEquipes();
                    break;
                case 6:
                    ajouterJoueurAEquipe();
                    break;
                case 7:
                    retirerJoueurDeEquipe();
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    consoleLogger.afficherErreur("Invalid option. Please try again.");
            }
        }
    }

    private void creerEquipe() {
        consoleLogger.afficherMessage("Creating a new team");
        consoleLogger.afficherMessage("Enter the team name:");
        String nom = scanner.nextLine();

        Equipe equipeCreee = equipeController.creerEquipe(nom);

        if (equipeCreee != null) {
            consoleLogger.afficherMessage("Team created successfully. ID:  " + equipeCreee.getId());
        } else {
            consoleLogger.afficherErreur("Error while creating the team.");
        }
    }

    private void modifierEquipe() {
        consoleLogger.afficherMessage("Editing a team");
        consoleLogger.afficherMessage("Enter the team ID to edit:");
        Long id = scanner.nextLong();
        scanner.nextLine();
        consoleLogger.afficherMessage("Enter the new team name:");
        String nouveauNom = scanner.nextLine();

        Equipe equipeModifiee = equipeController.modifierEquipe(id, nouveauNom);
        if (equipeModifiee != null) {
            consoleLogger.afficherMessage("Team updated successfully.");
        } else {
            consoleLogger.afficherErreur("Error while updating the team.");
        }
    }

    private void supprimerEquipe() {
        consoleLogger.afficherMessage("Deleting a team");
        consoleLogger.afficherMessage("Enter the team ID to delete:");
        Long id = scanner.nextLong();
        scanner.nextLine();

        equipeController.supprimerEquipe(id);
        consoleLogger.afficherMessage("Team deleted successfully (if it existed).");
    }

    private void afficherEquipe() {
        consoleLogger.afficherMessage("Affichage d'une équipe");
        consoleLogger.afficherMessage("Entrez l'ID de l'équipe à afficher:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Optional<Equipe> equipeOptionnelle = equipeController.obtenirEquipe(id);
        if (equipeOptionnelle.isPresent()) {
            Equipe equipe = equipeOptionnelle.get();
            consoleLogger.afficherMessage("ID: " + equipe.getId());
            consoleLogger.afficherMessage("Nom: " + equipe.getNom());
            consoleLogger.afficherMessage("Classement: " + equipe.getClassement());
            consoleLogger.afficherMessage("Joueurs:");
            for (Joueur joueur : equipe.getJoueurs()) {
                consoleLogger.afficherMessage("  - " + joueur.getPseudo());
            }
        } else {
            consoleLogger.afficherErreur("Équipe non trouvée.");
        }
    }


    private void afficherToutesEquipes() {
        consoleLogger.afficherMessage("List of all teams:");
        List<Equipe> equipes = equipeController.obtenirToutesEquipes();
        if (!equipes.isEmpty()) {
            for (Equipe equipe : equipes) {
                consoleLogger.afficherMessage("ID: " + equipe.getId() + ", Name: " + equipe.getNom() + ", Ranking: "
                        + equipe.getClassement());
            }
        } else {
            consoleLogger.afficherMessage("No teams found.");
        }
    }

    private void ajouterJoueurAEquipe() {
        consoleLogger.afficherMessage("Adding a player to a team");
        consoleLogger.afficherMessage("Enter the team ID:");
        Long equipeId = scanner.nextLong();
        consoleLogger.afficherMessage("Enter the player ID to add:");
        Long joueurId = scanner.nextLong();
        scanner.nextLine();

        equipeController.ajouterJoueurAEquipe(equipeId, joueurId);
        consoleLogger.afficherMessage("Player added to the team successfully (if both existed).");
    }

    private void retirerJoueurDeEquipe() {
        consoleLogger.afficherMessage("Removing a player from a team");
        consoleLogger.afficherMessage("Enter the team ID:");
        Long equipeId = scanner.nextLong();
        consoleLogger.afficherMessage("Enter the player ID to remove:");
        Long joueurId = scanner.nextLong();
        scanner.nextLine();

        equipeController.retirerJoueurDeEquipe(equipeId, joueurId);
        consoleLogger.afficherMessage("Player removed from the team successfully (if both existed).");
    }
}

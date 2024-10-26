package org.krosmozClash.presentation.menu;

import org.krosmozClash.controller.EquipeController;
import org.krosmozClash.controller.JeuController;
import org.krosmozClash.controller.TournoiController;
import org.krosmozClash.model.Equipe;
import org.krosmozClash.model.Tournoi;
import org.krosmozClash.model.enums.TournoiStatus;
import org.krosmozClash.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TournoiMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournoiMenu.class);
    private final TournoiController tournoiController;
    private final EquipeController equipeController;
    private final JeuController jeuController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public TournoiMenu(TournoiController tournoiController, EquipeController equipeController,
            JeuController jeuController, ConsoleLogger consoleLogger, Scanner scanner) {
        this.tournoiController = tournoiController;
        this.equipeController = equipeController;
        this.jeuController = jeuController;
        this.consoleLogger = consoleLogger;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        int choix;
        do {
            System.out.println("\n--- Tournament Menu ---");
            System.out.println("1. Create a tournament");
            System.out.println("2. Edit a tournament");
            System.out.println("3. Delete a tournament");
            System.out.println("4. Display a tournament");
            System.out.println("5. Display all tournaments");
            System.out.println("6. Add a team to a tournament");
            System.out.println("7. Remove a team from a tournament");
            System.out.println("8. Calculate estimated tournament duration");
            System.out.println("9. Modify tournament status");
            System.out.println("0. Return to main menu");
            System.out.print("Your choice: ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    creerTournoi();
                    break;
                case 2:
                    modifierTournoi();
                    break;
                case 3:
                    supprimerTournoi();
                    break;
                case 4:
                    afficherTournoi();
                    break;
                case 5:
                    afficherTousTournois();
                    break;
                case 6:
                    ajouterEquipeATournoi();
                    break;
                case 7:
                    retirerEquipeDeTournoi();
                    break;
                case 8:
                    calculerDureeEstimeeTournoi();
                    break;
                case 9:
                    modifierStatutTournoi();
                    break;
                case 0:
                    System.out.println("Returning to the main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choix != 0);
    }

    private void creerTournoi() {
        consoleLogger.afficherMessage("Creating a new tournament");
        consoleLogger.afficherMessage("Enter the tournament title:");
        String titre = scanner.nextLine();

        consoleLogger.afficherMessage("Enter the game ID for this tournament:");
        Long jeuId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        consoleLogger.afficherMessage("Enter the start date (format: dd/MM/yyyy):");
        LocalDate dateDebut = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        consoleLogger.afficherMessage("Enter the end date (format: dd/MM/yyyy):");
        LocalDate dateFin = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        consoleLogger.afficherMessage("Enter the expected number of spectators:");
        int nombreSpectateurs = scanner.nextInt();
        scanner.nextLine();

        consoleLogger.afficherMessage("Enter the average match duration (in minutes):");
        int dureeMoyenneMatch = scanner.nextInt();
        scanner.nextLine();

        consoleLogger.afficherMessage("Enter the ceremony time (in minutes):");
        int tempsCeremonie = scanner.nextInt();
        scanner.nextLine();

        consoleLogger.afficherMessage("Enter the break time between matches (in minutes):");
        int tempsPauseEntreMatchs = scanner.nextInt();
        scanner.nextLine();

        Tournoi nouveauTournoi = tournoiController.creerTournoi(titre, jeuId, dateDebut, dateFin, nombreSpectateurs,
                dureeMoyenneMatch, tempsCeremonie, tempsPauseEntreMatchs);
        if (nouveauTournoi != null) {
            consoleLogger.afficherMessage("Tournament created successfully. ID: " + nouveauTournoi.getId());
        } else {
            consoleLogger.afficherErreur("Error creating tournament.");
        }
    }
    private void modifierTournoi() {
        consoleLogger.afficherMessage("Editing a tournament");
        consoleLogger.afficherMessage("Enter the ID of the tournament to edit:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Optional<Tournoi> tournoiOptional = tournoiController.obtenirTournoi(id);
        if (tournoiOptional.isPresent()) {
            Tournoi tournoi = tournoiOptional.get();
            consoleLogger.afficherMessage("Enter the new title (or press Enter to keep the current one):");
            String nouveauTitre = scanner.nextLine();
            if (nouveauTitre.isEmpty()) {
                nouveauTitre = tournoi.getTitre();
            }

            consoleLogger.afficherMessage("Enter the new start date (format: dd/MM/yyyy) (or press Enter to keep the current one):");
            String dateDebutStr = scanner.nextLine();
            LocalDate nouvelleDateDebut = dateDebutStr.isEmpty() ? tournoi.getDateDebut()
                    : LocalDate.parse(dateDebutStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            consoleLogger.afficherMessage("Enter the new end date (format: dd/MM/yyyy) (or press Enter to keep the current one):");
            String dateFinStr = scanner.nextLine();
            LocalDate nouvelleDateFin = dateFinStr.isEmpty() ? tournoi.getDateFin()
                    : LocalDate.parse(dateFinStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            consoleLogger.afficherMessage("Enter the new number of spectators (or -1 to keep the current one):");
            int nouveauNombreSpectateurs = scanner.nextInt();
            if (nouveauNombreSpectateurs == -1) {
                nouveauNombreSpectateurs = tournoi.getNombreSpectateurs();
            }
            scanner.nextLine(); // Consume newline

            Tournoi tournoiModifie = tournoiController.modifierTournoi(id, nouveauTitre, nouvelleDateDebut,
                    nouvelleDateFin, nouveauNombreSpectateurs);
            if (tournoiModifie != null) {
                consoleLogger.afficherMessage("Tournament updated successfully.");
            } else {
                consoleLogger.afficherErreur("Error updating tournament.");
            }
        } else {
            consoleLogger.afficherErreur("Tournament not found.");
        }
    }

    private void supprimerTournoi() {
        consoleLogger.afficherMessage("Deleting a tournament");
        consoleLogger.afficherMessage("Enter the ID of the tournament to delete:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Optional<Tournoi> tournoiOptional = tournoiController.obtenirTournoi(id);
        if (tournoiOptional.isPresent()) {
            consoleLogger.afficherMessage("Are you sure you want to delete the tournament " + tournoiOptional.get().getTitre() + "? (Y/N)");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("Y")) {
                tournoiController.supprimerTournoi(id);
                consoleLogger.afficherMessage("Tournament deleted successfully.");
            } else {
                consoleLogger.afficherMessage("Deletion canceled.");
            }
        } else {
            consoleLogger.afficherErreur("Tournament not found.");
        }
    }


    private void afficherTournoi() {
        consoleLogger.afficherMessage("Displaying tournament details");
        consoleLogger.afficherMessage("Enter the ID of the tournament to display:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Tournoi> tournoiOptional = tournoiController.obtenirTournoi(id);
        if (tournoiOptional.isPresent()) {
            Tournoi tournoi = tournoiOptional.get();
            consoleLogger.afficherMessage("Tournament details:");
            consoleLogger.afficherMessage("ID: " + tournoi.getId());
            consoleLogger.afficherMessage("Title: " + tournoi.getTitre());
            consoleLogger.afficherMessage("Game: " + tournoi.getJeu().getNom());
            consoleLogger.afficherMessage("Start Date: " + tournoi.getDateDebut());
            consoleLogger.afficherMessage("End Date: " + tournoi.getDateFin());
            consoleLogger.afficherMessage("Number of Spectators: " + tournoi.getNombreSpectateurs());
            consoleLogger.afficherMessage("Status: " + tournoi.getStatut());
            consoleLogger.afficherMessage("Participating Teams:");
            for (Equipe equipe : tournoi.getEquipes()) {
                consoleLogger.afficherMessage("- " + equipe.getNom());
            }
        } else {
            consoleLogger.afficherErreur("Tournament not found.");
        }
    }

    private void afficherTousTournois() {
        consoleLogger.afficherMessage("List of all tournaments:");
        List<Tournoi> tournois = tournoiController.obtenirTousTournois();
        if (!tournois.isEmpty()) {
            for (Tournoi tournoi : tournois) {
                String jeuNom = tournoi.getJeu() != null ? tournoi.getJeu().getNom() : "N/A";
                consoleLogger.afficherMessage("ID: " + tournoi.getId() + ", Titre: " + tournoi.getTitre() + ", Jeu: "
                        + jeuNom + ", Statut: " + tournoi.getStatut());
            }
        } else {
            consoleLogger.afficherMessage("No tournaments found.");
        }
    }

    private void ajouterEquipeATournoi() {
        consoleLogger.afficherMessage("Adding a team to a tournament");
        consoleLogger.afficherMessage("Enter the ID of the tournament:");
        Long tournoiId = scanner.nextLong();
        consoleLogger.afficherMessage("Enter the ID of the team to add:");
        Long equipeId = scanner.nextLong();
        scanner.nextLine();

        try {
            tournoiController.ajouterEquipeATournoi(tournoiId, equipeId);
            consoleLogger.afficherMessage("Team successfully added to the tournament.");
        } catch (Exception e) {
            consoleLogger.afficherErreur("Error adding the team to the tournament: " + e.getMessage());
        }
    }

    private void retirerEquipeDeTournoi() {
        consoleLogger.afficherMessage("Removing a team from a tournament");
        consoleLogger.afficherMessage("Enter the ID of the tournament:");
        Long tournoiId = scanner.nextLong();
        consoleLogger.afficherMessage("Enter the ID of the team to remove:");
        Long equipeId = scanner.nextLong();
        scanner.nextLine();

        try {
            tournoiController.retirerEquipeDeTournoi(tournoiId, equipeId);
            consoleLogger.afficherMessage("Team successfully removed from the tournament.");
        } catch (Exception e) {
            consoleLogger.afficherErreur("Error removing the team from the tournament: " + e.getMessage());
        }
    }

    private void calculerDureeEstimeeTournoi() {
        consoleLogger.afficherMessage("Calculating estimated duration of a tournament");
        consoleLogger.afficherMessage("Enter the ID of the tournament:");
        Long tournoiId = scanner.nextLong();
        scanner.nextLine();

        int dureeEstimee = tournoiController.obtenirDureeEstimeeTournoi(tournoiId);
        if (dureeEstimee > 0) {
            Optional<Tournoi> tournoiOptional = tournoiController.obtenirTournoi(tournoiId);
            if (tournoiOptional.isPresent()) {
                Tournoi tournoi = tournoiOptional.get();
                consoleLogger.afficherMessage(
                        "The estimated duration of the tournament is " + tournoi.getDureeEstimee() + " minutes.");
            } else {
                consoleLogger.afficherErreur("Tournament not found after calculation.");
            }
        } else {
            consoleLogger.afficherErreur("Unable to calculate the estimated duration of the tournament.");
        }
    }

    private void modifierStatutTournoi() {
        System.out.println("Enter the tournament ID:");
        Long tournoiId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Choose the new status:");
        System.out.println("1. PLANNED");
        System.out.println("2. IN_PROGRESS");
        System.out.println("3. COMPLETED");
        System.out.println("4. CANCELED");
        int choix = scanner.nextInt();
        scanner.nextLine();

        TournoiStatus nouveauStatut;
        switch (choix) {
            case 1:
                nouveauStatut = TournoiStatus.PLANIFIE;
                break;
            case 2:
                nouveauStatut = TournoiStatus.EN_COURS;
                break;
            case 3:
                nouveauStatut = TournoiStatus.TERMINE;
                break;
            case 4:
                nouveauStatut = TournoiStatus.ANNULE;
                break;
            default:
                System.out.println("Invalid choice. Operation canceled.");
                return;
        }

        tournoiController.modifierStatutTournoi(tournoiId, nouveauStatut);
        System.out.println("Tournament status successfully updated.");
    }
}

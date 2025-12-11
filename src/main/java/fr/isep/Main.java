// src/main/java/fr/isep/Main.java
package fr.isep;

import fr.isep.api.AmadeusAPI;
import fr.isep.utils.FileManager;
import fr.isep.model.personne.*;
import fr.isep.model.vol.*;
import fr.isep.model.reservation.Reservation;
import fr.isep.model.aeroport.Aeroport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    private static List<Vol> vols = new ArrayList<>();
    private static List<Passager> passagers = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static List<Aeroport> aeroports = new ArrayList<>();
    private static List<Employe> employes = new ArrayList<>();
    private static AmadeusAPI amadeusAPI = new AmadeusAPI();

    public static void main(String[] args) {
        System.out.println("PROJET COMPAGNIE AERIENNE - ISEP");
        System.out.println("Seance 2 : Fonctionnalites avancees et tests unitaires");
        System.out.println("--------------------------------------------------------\n");

        System.out.println("1. Initialisation des donnees...");
        initialiserDonneesTest();
        afficherDonnees();

        System.out.println("\n2. Fonctionnalites avancees - Statistiques");
        System.out.println("------------------------------------------");
        Statistiques.genererRapport(vols, reservations, passagers);

        System.out.println("\n3. Generation de rapports dans des fichiers");
        System.out.println("--------------------------------------------");
        String rapport = Statistiques.rapportTextuel(vols, reservations, passagers);
        System.out.println("Rapport textuel genere :");
        System.out.println(rapport);

        FileManager.genererRapportFichier(rapport, "statistiques_rapport.txt");

        System.out.println("\n4. Simulation d'operations");
        System.out.println("--------------------------");
        simulerOperationsSupplementaires();

        System.out.println("\n5. Statistiques finales");
        System.out.println("-----------------------");
        Statistiques.genererRapport(vols, reservations, passagers);

        System.out.println("\n6. Demonstration des fonctionnalites avancees");
        System.out.println("----------------------------------------------");
        demontrerFonctionnalitesAvancees();

        System.out.println("\nExecution terminee avec succes");
    }

    private static void demontrerFonctionnalitesAvancees() {
        System.out.println("\nA. Lecture/Ecriture de fichiers");
        System.out.println("--------------------------------");

        // 1. Lecture depuis fichier
        System.out.println("1. Lecture de vols depuis fichier CSV...");

        // Vérifier si le fichier existe
        java.io.File fichierVols = new java.io.File("vols.csv");
        if (!fichierVols.exists()) {
            System.out.println("Fichier vols.csv non trouvé. Création d'un fichier d'exemple...");
            FileManager.creerFichierExempleVols("vols.csv");
        }

        List<Vol> volsFichier = FileManager.lireVolsDepuisFichier("vols.csv");
        System.out.println(volsFichier.size() + " vols lus depuis le fichier");

        if (!volsFichier.isEmpty()) {
            vols.addAll(volsFichier);
            System.out.println("Vols intégrés au système");
        }

        // 2. Sauvegarde des reservations
        System.out.println("\n2. Sauvegarde des réservations dans fichier...");
        FileManager.sauvegarderReservations(reservations, "reservations_backup.csv");
        System.out.println("Réservations sauvegardées");

        // 3. Lecture des reservations depuis fichier
        System.out.println("\n3. Chargement des réservations depuis fichier...");
        List<Reservation> reservationsChargees = FileManager.chargerReservations("reservations_backup.csv");

        System.out.println("\nB. Integration API Amadeus");
        System.out.println("--------------------------");

        // 4. Recherche via API Amadeus
        System.out.println("4. Recherche de vols via API Amadeus...");
        List<Map<String, String>> volsDisponibles = amadeusAPI.rechercherVols(
                "CDG",    // Paris Charles de Gaulle
                "JFK",    // New York JFK
                "2024-12-15",
                2         // 2 adultes
        );

        amadeusAPI.afficherVolsDisponibles(volsDisponibles);

        System.out.println("\nC. Gestion des passagers via fichiers");
        System.out.println("--------------------------------------");

        // 5. Ajout d'un passager via fichier
        System.out.println("5. Ajout d'un passager via ecriture fichier...");
        if (!passagers.isEmpty()) {
            Passager nouveau = new Passager(
                    "PASS999",
                    "Martin Dubois",
                    "15 Rue de Paris",
                    "martin.dubois@email.com",
                    "FR999999"
            );
            FileManager.ecrirePassagerDansFichier(nouveau, "AF999", "passagers.csv");
            System.out.println("Passager ajouté au fichier");
        }

        // 6. Lecture des passagers depuis fichier
        System.out.println("\n6. Lecture des passagers depuis fichier...");
        List<Passager> passagersFichier = FileManager.lirePassagersDepuisFichier("passagers.csv");
        System.out.println(passagersFichier.size() + " passagers lus depuis fichier");

        System.out.println("\nD. Tests unitaires");
        System.out.println("---------------------------------------");
        System.out.println("Pour exécuter les tests unitaires : mvn test");
    }

    private static void initialiserDonneesTest() {
        // Employes
        Pilote pilote1 = new Pilote("P001", "Jean Dupont", "Paris", "jean.dupont@email.com",
                "EMP001", LocalDate.of(2020, 5, 15), "LIC-12345", 5000);

        Pilote pilote2 = new Pilote("P002", "Sophie Martin", "Lyon", "sophie.martin@email.com",
                "EMP002", LocalDate.of(2019, 8, 22), "LIC-67890", 7500);

        PersonnelCabine personnel1 = new PersonnelCabine("PC001", "Marie Lambert",
                "Marseille", "marie.lambert@email.com", "EMP003",
                LocalDate.of(2021, 3, 10), "Hotesse principale");

        employes.add(pilote1);
        employes.add(pilote2);
        employes.add(personnel1);

        // Passagers
        passagers.add(new Passager("PASS001", "Alice Dubois", "Paris",
                "alice.dubois@email.com", "FR123456"));
        passagers.add(new Passager("PASS002", "Bob Leroy", "Lyon",
                "bob.leroy@email.com", "FR234567"));
        passagers.add(new Passager("PASS003", "Clara Bernard", "Marseille",
                "clara.bernard@email.com", "FR345678"));

        // Vols
        vols.add(new Vol("AF100", "Paris CDG", "New York JFK",
                LocalDateTime.of(2024, 4, 15, 14, 30),
                LocalDateTime.of(2024, 4, 15, 17, 0)));

        vols.add(new Vol("AF200", "Paris CDG", "Londres LHR",
                LocalDateTime.of(2024, 4, 16, 10, 0),
                LocalDateTime.of(2024, 4, 16, 11, 30)));

        // Planifier vols
        for (Vol vol : vols) {
            vol.planifierVol();
        }

        // Reservations
        reservations.add(new Reservation("RES001", "PASS001", "AF100"));
        reservations.add(new Reservation("RES002", "PASS002", "AF100"));
        reservations.add(new Reservation("RES003", "PASS003", "AF200"));

        for (Reservation res : reservations) {
            res.confirmerReservation();
        }

        // Ajouter passagers aux vols
        vols.get(0).ajouterPassager("PASS001");
        vols.get(0).ajouterPassager("PASS002");
        vols.get(1).ajouterPassager("PASS003");

        // Aeroports
        aeroports.add(new Aeroport("Charles de Gaulle", "Paris",
                "Principal aeroport francais"));
        aeroports.add(new Aeroport("JFK", "New York",
                "Aeroport international New York"));

        // Avions
        Avion avion1 = new Avion("F-GABC", "Airbus A320", 180);
        avion1.affecterVol("AF100");

        Avion avion2 = new Avion("F-GDEF", "Boeing 737", 215);
        avion2.affecterVol("AF200");

        System.out.println("Donnees initialisees: " + vols.size() + " vols, " +
                passagers.size() + " passagers, " + reservations.size() + " reservations");
    }

    private static void afficherDonnees() {
        System.out.println("Donnees actuelles du systeme:");
        System.out.println("- Employes: " + employes.size());
        System.out.println("- Passagers: " + passagers.size());
        System.out.println("- Vols: " + vols.size());
        System.out.println("- Reservations: " + reservations.size());
        System.out.println("- Aeroports: " + aeroports.size());
    }

    private static void simulerOperationsSupplementaires() {
        System.out.println("Operations en cours...");

        // Annulation d'un vol
        if (!vols.isEmpty()) {
            vols.get(0).annulerVol();
            System.out.println("Vol " + vols.get(0).getNumeroVol() + " annule");
        }

        // Nouveau passager
        Passager nouveau = new Passager("PASS999", "Test Integration",
                "Test Ville", "test@email.com", "TEST999");
        passagers.add(nouveau);

        // Nouvelle reservation
        Reservation nouvelleRes = new Reservation("RES999", "PASS999", "AF300");
        nouvelleRes.confirmerReservation();
        reservations.add(nouvelleRes);

        System.out.println("Operations simulees terminees");
    }
}
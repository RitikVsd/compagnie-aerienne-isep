// src/main/java/fr/isep/Main.java
package fr.isep;

import fr.isep.model.personne.*;
import fr.isep.model.vol.*;
import fr.isep.model.reservation.Reservation;
import fr.isep.model.aeroport.Aeroport;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Projet Compagnie Aérienne - ISEP ===\n");

        // Création d'instances de test
        System.out.println("1. Création des personnes:");

        // Pilote
        Pilote pilote = new Pilote(
                "P001",
                "Jean Dupont",
                "Paris",
                "jean.dupont@email.com",
                "EMP001",
                LocalDate.of(2020, 5, 15),
                "LIC-12345",
                5000
        );
        System.out.println(pilote.obtenirInfos());
        System.out.println("Rôle: " + pilote.obtenirRole());

        // Personnel de cabine
        PersonnelCabine personnel = new PersonnelCabine(
                "PC001",
                "Marie Martin",
                "Lyon",
                "marie.martin@email.com",
                "EMP002",
                LocalDate.of(2021, 3, 10),
                "Hôtesse principale"
        );
        System.out.println("\n" + personnel.obtenirInfos());
        System.out.println("Rôle: " + personnel.obtenirRole());

        // Passager
        Passager passager = new Passager(
                "PASS001",
                "Thomas Leroy",
                "Marseille",
                "thomas.leroy@email.com",
                "FR123456"
        );
        System.out.println("\n" + passager.obtenirInfos());

        // Vol
        System.out.println("\n2. Création d'un vol:");
        Vol vol1 = new Vol(
                "AF123",
                "Paris CDG",
                "New York JFK",
                LocalDateTime.of(2024, 4, 15, 14, 30),
                LocalDateTime.of(2024, 4, 15, 17, 0)
        );
        vol1.planifierVol();

        // Avion
        System.out.println("\n3. Création d'un avion:");
        Avion avion = new Avion("F-GABC", "Airbus A320", 180);
        avion.affecterVol("AF123");

        // Réservation
        System.out.println("\n4. Création d'une réservation:");
        Reservation reservation = new Reservation("RES001", "PASS001", "AF123");
        reservation.confirmerReservation();

        // Passager réserve le vol
        passager.reserverVol("AF123", "RES001");

        // Aéroport
        System.out.println("\n5. Création d'un aéroport:");
        Aeroport cdg = new Aeroport("Charles de Gaulle", "Paris", "Principal aéroport français");
        cdg.affecterVol("AF123");

        System.out.println("\n=== Démonstration terminée ===");
    }
}
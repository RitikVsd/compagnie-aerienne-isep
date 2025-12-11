// src/main/java/fr/isep/model/personne/Passager.java
package fr.isep.model.personne;

import java.util.ArrayList;
import java.util.List;

public class Passager extends Personne {
    private String passeport;
    private List<String> reservations; // Liste des numéros de réservation

    public Passager(String identifiant, String nom, String adresse, String contact,
                    String passeport) {
        super(identifiant, nom, adresse, contact);
        this.passeport = passeport;
        this.reservations = new ArrayList<>();
    }

    @Override
    public String obtenirInfos() {
        return String.format("Passager: %s (ID: %s) - Passeport: %s - Réservations: %d",
                nom, identifiant, passeport, reservations.size());
    }

    // Méthodes spécifiques
    public void reserverVol(String numeroVol, String numeroReservation) {
        reservations.add(numeroReservation);
        System.out.println("Réservation " + numeroReservation + " pour le vol " +
                numeroVol + " créée pour " + nom);
    }

    public void annulerReservation(String numeroReservation) {
        if (reservations.remove(numeroReservation)) {
            System.out.println("Réservation " + numeroReservation + " annulée pour " + nom);
        } else {
            System.out.println("Réservation " + numeroReservation + " non trouvée");
        }
    }

    public List<String> obtenirReservations() {
        return new ArrayList<>(reservations); // Retourne une copie
    }

    // Getters et Setters
    public String getPasseport() { return passeport; }
    public void setPasseport(String passeport) { this.passeport = passeport; }
}
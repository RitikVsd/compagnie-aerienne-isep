// src/main/java/fr/isep/model/reservation/Reservation.java
package fr.isep.model.reservation;

import java.time.LocalDateTime;

public class Reservation {
    private String numeroReservation;
    private LocalDateTime dateReservation;
    private String statut; // "Confirmée", "En attente", "Annulée"
    private String idPassager;
    private String numeroVol;

    public Reservation(String numeroReservation, String idPassager, String numeroVol) {
        this.numeroReservation = numeroReservation;
        this.dateReservation = LocalDateTime.now();
        this.statut = "En attente";
        this.idPassager = idPassager;
        this.numeroVol = numeroVol;
    }

    public void confirmerReservation() {
        this.statut = "Confirmée";
        System.out.println("Réservation " + numeroReservation + " confirmée");
    }

    public void annulerReservation() {
        this.statut = "Annulée";
        System.out.println("Réservation " + numeroReservation + " annulée");
    }

    public void modifierReservation(String nouveauVol) {
        this.numeroVol = nouveauVol;
        System.out.println("Réservation " + numeroReservation + " modifiée pour le vol " + nouveauVol);
    }

    // Getters et Setters
    public String getNumeroReservation() { return numeroReservation; }
    public void setNumeroReservation(String numeroReservation) {
        this.numeroReservation = numeroReservation;
    }

    public LocalDateTime getDateReservation() { return dateReservation; }
    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getIdPassager() { return idPassager; }
    public void setIdPassager(String idPassager) { this.idPassager = idPassager; }

    public String getNumeroVol() { return numeroVol; }
    public void setNumeroVol(String numeroVol) { this.numeroVol = numeroVol; }
}
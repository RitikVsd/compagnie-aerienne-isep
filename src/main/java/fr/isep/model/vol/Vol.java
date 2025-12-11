// src/main/java/fr/isep/model/vol/Vol.java
package fr.isep.model.vol;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Vol {
    private String numeroVol;
    private String origine;
    private String destination;
    private LocalDateTime dateHeureDepart;
    private LocalDateTime dateHeureArrivee;
    private String etat; // "Planifié", "Confirmé", "Annulé", "Terminé"
    private List<String> passagers; // Liste des ID des passagers

    public Vol(String numeroVol, String origine, String destination,
               LocalDateTime dateHeureDepart, LocalDateTime dateHeureArrivee) {
        this.numeroVol = numeroVol;
        this.origine = origine;
        this.destination = destination;
        this.dateHeureDepart = dateHeureDepart;
        this.dateHeureArrivee = dateHeureArrivee;
        this.etat = "Planifié";
        this.passagers = new ArrayList<>();
    }

    // Méthodes CRUD-like
    public void planifierVol() {
        this.etat = "Planifié";
        System.out.println("Vol " + numeroVol + " planifié");
    }

    public void annulerVol() {
        this.etat = "Annulé";
        System.out.println("Vol " + numeroVol + " annulé");
    }

    public void modifierVol(String nouvelleOrigine, String nouvelleDestination,
                            LocalDateTime nouvelleDateDepart, LocalDateTime nouvelleDateArrivee) {
        this.origine = nouvelleOrigine;
        this.destination = nouvelleDestination;
        this.dateHeureDepart = nouvelleDateDepart;
        this.dateHeureArrivee = nouvelleDateArrivee;
        System.out.println("Vol " + numeroVol + " modifié");
    }

    public List<String> listingPassager() {
        return new ArrayList<>(passagers);
    }

    public void ajouterPassager(String idPassager) {
        passagers.add(idPassager);
    }

    public void retirerPassager(String idPassager) {
        passagers.remove(idPassager);
    }

    // Getters et Setters
    public String getNumeroVol() { return numeroVol; }
    public void setNumeroVol(String numeroVol) { this.numeroVol = numeroVol; }

    public String getOrigine() { return origine; }
    public void setOrigine(String origine) { this.origine = origine; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDateTime getDateHeureDepart() { return dateHeureDepart; }
    public void setDateHeureDepart(LocalDateTime dateHeureDepart) {
        this.dateHeureDepart = dateHeureDepart;
    }

    public LocalDateTime getDateHeureArrivee() { return dateHeureArrivee; }
    public void setDateHeureArrivee(LocalDateTime dateHeureArrivee) {
        this.dateHeureArrivee = dateHeureArrivee;
    }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
}
// src/main/java/fr/isep/model/vol/Avion.java
package fr.isep.model.vol;

import java.util.ArrayList;
import java.util.List;

public class Avion {
    private String immatriculation;
    private String modele;
    private int capacite;
    private List<String> volsAssignes; // Liste des numéros de vol

    public Avion(String immatriculation, String modele, int capacite) {
        this.immatriculation = immatriculation;
        this.modele = modele;
        this.capacite = capacite;
        this.volsAssignes = new ArrayList<>();
    }

    public void affecterVol(String numeroVol) {
        if (verifierDisponibilite()) {
            volsAssignes.add(numeroVol);
            System.out.println("Avion " + immatriculation + " affecté au vol " + numeroVol);
        } else {
            System.out.println("Avion " + immatriculation + " non disponible");
        }
    }

    public boolean verifierDisponibilite() {
        // Logique simplifiée : disponible si moins de 2 vols assignés
        return volsAssignes.size() < 2;
    }

    // Getters et Setters
    public String getImmatriculation() { return immatriculation; }
    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getModele() { return modele; }
    public void setModele(String modele) { this.modele = modele; }

    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }

    public List<String> getVolsAssignes() { return new ArrayList<>(volsAssignes); }
}
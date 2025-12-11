// src/main/java/fr/isep/model/personne/Pilote.java
package fr.isep.model.personne;

import java.time.LocalDate;

public class Pilote extends Employe {
    private String licence;
    private int heuresDeVol;

    public Pilote(String identifiant, String nom, String adresse, String contact,
                  String numeroEmployee, LocalDate dateEmbauche,
                  String licence, int heuresDeVol) {
        super(identifiant, nom, adresse, contact, numeroEmployee, dateEmbauche);
        this.licence = licence;
        this.heuresDeVol = heuresDeVol;
    }

    @Override
    public String obtenirRole() {
        return "Pilote";
    }

    @Override
    public String obtenirInfos() {
        return super.obtenirInfos() + String.format(" - Licence: %s - Heures de vol: %d",
                licence, heuresDeVol);
    }

    // Méthodes spécifiques
    public void affecterVol(String numeroVol) {
        System.out.println("Pilote " + nom + " affecté au vol " + numeroVol);
    }

    public String obtenirVol() {
        return "Vols assignés au pilote " + nom;
    }

    // Getters et Setters
    public String getLicence() { return licence; }
    public void setLicence(String licence) { this.licence = licence; }

    public int getHeuresDeVol() { return heuresDeVol; }
    public void setHeuresDeVol(int heuresDeVol) { this.heuresDeVol = heuresDeVol; }
}
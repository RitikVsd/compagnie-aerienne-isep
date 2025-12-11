// src/main/java/fr/isep/model/personne/PersonnelCabine.java
package fr.isep.model.personne;

import java.time.LocalDate;

public class PersonnelCabine extends Employe {
    private String qualification;

    public PersonnelCabine(String identifiant, String nom, String adresse, String contact,
                           String numeroEmployee, LocalDate dateEmbauche, String qualification) {
        super(identifiant, nom, adresse, contact, numeroEmployee, dateEmbauche);
        this.qualification = qualification;
    }

    @Override
    public String obtenirRole() {
        return "Personnel de Cabine";
    }

    @Override
    public String obtenirInfos() {
        return super.obtenirInfos() + String.format(" - Qualification: %s", qualification);
    }

    // Méthodes spécifiques
    public void affecterVol(String numeroVol) {
        System.out.println("Personnel de cabine " + nom + " affecté au vol " + numeroVol);
    }

    public String obtenirVol() {
        return "Vols assignés au personnel " + nom;
    }

    // Getters et Setters
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
}
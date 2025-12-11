// src/main/java/fr/isep/model/personne/Employe.java
package fr.isep.model.personne;

import java.time.LocalDate;

public abstract class Employe extends Personne {
    protected String numeroEmployee;
    protected LocalDate dateEmbauche;

    public Employe(String identifiant, String nom, String adresse, String contact,
                   String numeroEmployee, LocalDate dateEmbauche) {
        super(identifiant, nom, adresse, contact);
        this.numeroEmployee = numeroEmployee;
        this.dateEmbauche = dateEmbauche;
    }

    // Méthode abstraite pour obtenir le rôle
    public abstract String obtenirRole();

    // Implémentation de obtenirInfos pour les employés
    @Override
    public String obtenirInfos() {
        return String.format("Employé: %s (ID: %s) - %s - Embauche: %s",
                nom, identifiant, numeroEmployee, dateEmbauche);
    }

    // Getters et Setters
    public String getNumeroEmployee() { return numeroEmployee; }
    public void setNumeroEmployee(String numeroEmployee) { this.numeroEmployee = numeroEmployee; }

    public LocalDate getDateEmbauche() { return dateEmbauche; }
    public void setDateEmbauche(LocalDate dateEmbauche) { this.dateEmbauche = dateEmbauche; }
}
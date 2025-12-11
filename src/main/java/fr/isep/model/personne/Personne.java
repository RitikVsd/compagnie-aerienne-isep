// src/main/java/fr/isep/model/personne/Personne.java
package fr.isep.model.personne;

public abstract class Personne {
    protected String identifiant;
    protected String nom;
    protected String adresse;
    protected String contact;

    public Personne(String identifiant, String nom, String adresse, String contact) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.adresse = adresse;
        this.contact = contact;
    }

    // Méthode abstraite à implémenter par les sous-classes
    public abstract String obtenirInfos();

    // Getters et Setters
    public String getIdentifiant() { return identifiant; }
    public void setIdentifiant(String identifiant) { this.identifiant = identifiant; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
}
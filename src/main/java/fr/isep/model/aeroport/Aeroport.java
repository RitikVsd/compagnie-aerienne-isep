// src/main/java/fr/isep/model/aeroport/Aeroport.java
package fr.isep.model.aeroport;

import java.util.ArrayList;
import java.util.List;

public class Aeroport {
    private String nom;
    private String ville;
    private String description;
    private List<String> vols; // Liste des numéros de vol

    public Aeroport(String nom, String ville, String description) {
        this.nom = nom;
        this.ville = ville;
        this.description = description;
        this.vols = new ArrayList<>();
    }

    public void affecterVol(String numeroVol) {
        vols.add(numeroVol);
        System.out.println("Vol " + numeroVol + " affecté à l'aéroport " + nom);
    }

    // Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getVols() { return new ArrayList<>(vols); }
}
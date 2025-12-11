// fr/isep/Statistiques.java
package fr.isep;

import fr.isep.model.vol.Vol;
import fr.isep.model.reservation.Reservation;
import fr.isep.model.personne.Passager;
import java.util.*;

public class Statistiques {

    // Méthode pour compter le nombre total de vols
    public static int nombreTotalVols(List<Vol> vols) {
        return vols.size();
    }

    // Méthode pour compter le nombre total de passagers transportés
    public static int nombreTotalPassagers(List<Vol> vols) {
        int total = 0;
        for (Vol vol : vols) {
            total += vol.listingPassager().size();
        }
        return total;
    }

    // Méthode pour calculer les revenus totaux (simulation)
    public static double revenusTotaux(List<Reservation> reservations) {
        // Simulation : prix fixe par réservation
        return reservations.size() * 150.0;
    }

    // Méthode pour obtenir les destinations les plus populaires
    public static List<Map.Entry<String, Integer>> destinationsPopulaires(List<Vol> vols) {
        Map<String, Integer> destinations = new HashMap<>();

        for (Vol vol : vols) {
            String destination = vol.getDestination();
            int compteur = destinations.getOrDefault(destination, 0);
            compteur += vol.listingPassager().size();
            destinations.put(destination, compteur);
        }

        // Trier par valeur décroissante
        List<Map.Entry<String, Integer>> listeTriee = new ArrayList<>(destinations.entrySet());
        listeTriee.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        return listeTriee;
    }

    // Méthode pour afficher un rapport complet
    public static void genererRapport(List<Vol> vols, List<Reservation> reservations,
                                      List<Passager> passagers) {
        System.out.println("\n -Rapport Statistiques- ");

        System.out.println("\nStatistiques générales :");
        System.out.println("- Nombre total de vols : " + nombreTotalVols(vols));
        System.out.println("- Nombre total de passagers inscrits : " + passagers.size());
        System.out.println("- Nombre total de passagers transportés : " + nombreTotalPassagers(vols));
        System.out.println("- Revenus générés estimés : " + String.format("%.2f", revenusTotaux(reservations)) + " €");

        System.out.println("\nDestinations les plus populaires :");
        List<Map.Entry<String, Integer>> destinations = destinationsPopulaires(vols);

        if (destinations.isEmpty()) {
            System.out.println("Aucune destination avec des passagers.");
        } else {
            for (int i = 0; i < Math.min(destinations.size(), 5); i++) {
                Map.Entry<String, Integer> entry = destinations.get(i);
                System.out.printf("  %d. %s : %d passager(s)%n",
                        i + 1, entry.getKey(), entry.getValue());
            }
        }

        // Statistiques supplémentaires
        System.out.println("\nStatistiques détaillées :");
        System.out.println("Vols par statut :");

        Map<String, Integer> volsParStatut = new HashMap<>();
        for (Vol vol : vols) {
            String statut = vol.getEtat();
            volsParStatut.put(statut, volsParStatut.getOrDefault(statut, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : volsParStatut.entrySet()) {
            System.out.println("  - " + entry.getKey() + " : " + entry.getValue() + " vol(s)");
        }

        System.out.println("\nRéservations par statut :");
        Map<String, Integer> reservationsParStatut = new HashMap<>();
        for (Reservation reservation : reservations) {
            String statut = reservation.getStatut();
            reservationsParStatut.put(statut, reservationsParStatut.getOrDefault(statut, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : reservationsParStatut.entrySet()) {
            System.out.println("  - " + entry.getKey() + " : " + entry.getValue() + " réservation(s)");
        }

        System.out.println("\n------------------------------------------");
    }

    // Méthode pour afficher un rapport simple (version texte)
    public static String rapportTextuel(List<Vol> vols, List<Reservation> reservations,
                                        List<Passager> passagers) {
        StringBuilder sb = new StringBuilder();

        sb.append("Rapport statistiques compagnie aérienne\n\n");
        sb.append("Statistiques générales\n");
        sb.append("---------------------\n");
        sb.append("Vols : ").append(nombreTotalVols(vols)).append("\n");
        sb.append("Passagers inscrits : ").append(passagers.size()).append("\n");
        sb.append("Passagers transportés : ").append(nombreTotalPassagers(vols)).append("\n");
        sb.append("Revenus estimés : ").append(String.format("%.2f", revenusTotaux(reservations))).append(" €\n\n");

        sb.append("Destinations populaires\n");
        sb.append("----------------------\n");
        List<Map.Entry<String, Integer>> destinations = destinationsPopulaires(vols);

        for (int i = 0; i < Math.min(destinations.size(), 3); i++) {
            Map.Entry<String, Integer> entry = destinations.get(i);
            sb.append(String.format("%d. %s (%d passagers)%n",
                    i + 1, entry.getKey(), entry.getValue()));
        }

        return sb.toString();
    }
}
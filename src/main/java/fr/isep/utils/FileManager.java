// src/main/java/fr/isep/utils/FileManager.java
package fr.isep.utils;

import fr.isep.model.vol.Vol;
import fr.isep.model.personne.Passager;
import fr.isep.model.reservation.Reservation;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM HHmm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Lecture des vols depuis un fichier CSV
    public static List<Vol> lireVolsDepuisFichier(String filePath) {
        List<Vol> vols = new ArrayList<>();

        // Vérifier si le fichier existe
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Fichier " + filePath + " non trouvé.");
            return vols;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);
                if (parts.length >= 5) {
                    try {
                        String code = parts[0].trim();
                        String origine = parts[1].trim();
                        String destination = parts[2].trim();
                        String dateStr = parts[3].trim();
                        String heureStr = parts[4].trim();

                        // Ajouter un 0 devant si l'heure a 3 chiffres
                        if (heureStr.length() == 3) {
                            heureStr = "0" + heureStr;
                        }

                        String dateHeureStr = dateStr + " " + heureStr;

                        LocalDateTime depart = LocalDateTime.parse(
                                dateHeureStr,
                                DateTimeFormatter.ofPattern("dd-MM HHmm")
                        );

                        LocalDateTime arrivee = depart.plusHours(2);

                        Vol vol = new Vol(code, origine, destination, depart, arrivee);
                        vol.planifierVol();
                        vols.add(vol);

                    } catch (DateTimeParseException e) {
                        System.out.println("Erreur format date ligne : " + line);
                    } catch (Exception e) {
                        System.out.println("Erreur ligne : " + line);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé : " + filePath);
        } catch (IOException e) {
            System.out.println("Erreur lecture fichier : " + e.getMessage());
        }

        return vols;
    }

    // Écriture des passagers dans un fichier CSV
    public static void ecrirePassagerDansFichier(Passager passager, String numeroVol, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath, true), StandardCharsets.UTF_8))) {

            String ligne = String.format("%s|%s|%s",
                    passager.getPasseport(),
                    passager.getNom(),
                    numeroVol);

            writer.write(ligne);
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Erreur écriture fichier : " + e.getMessage());
        }
    }

    // Sauvegarde des réservations dans un fichier
    public static void sauvegarderReservations(List<Reservation> reservations, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {

            writer.write("NumeroReservation|DateReservation|Statut|IDPassager|NumeroVol");
            writer.newLine();

            for (Reservation res : reservations) {
                String ligne = String.format("%s|%s|%s|%s|%s",
                        res.getNumeroReservation(),
                        res.getDateReservation().format(DATE_TIME_FORMATTER),
                        res.getStatut(),
                        res.getIdPassager(),
                        res.getNumeroVol());

                writer.write(ligne);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Erreur sauvegarde réservations : " + e.getMessage());
        }
    }

    // Chargement des réservations depuis un fichier
    public static List<Reservation> chargerReservations(String filePath) {
        List<Reservation> reservations = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Fichier " + filePath + " non trouvé.");
            return reservations;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;
            boolean firstLine = true;
            int reservationsLues = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (firstLine) {
                    firstLine = false;
                    if (line.startsWith("NumeroReservation")) {
                        continue;
                    }
                }

                String[] parts = line.split("\\|", -1);
                if (parts.length >= 5) {
                    String numeroReservation = parts[0].trim();

                    // Vérifier si c'est bien une réservation (commence par RES)
                    if (!numeroReservation.startsWith("RES")) {
                        continue;
                    }

                    try {
                        String dateStr = parts[1].trim();
                        String statut = parts[2].trim();
                        String idPassager = parts[3].trim();
                        String numeroVol = parts[4].trim();

                        Reservation res = new Reservation(numeroReservation, idPassager, numeroVol);
                        res.setStatut(statut);

                        LocalDateTime dateReservation = LocalDateTime.parse(dateStr, DATE_TIME_FORMATTER);
                        res.setDateReservation(dateReservation);

                        reservations.add(res);
                        reservationsLues++;

                    } catch (DateTimeParseException e) {
                        // Ignorer la ligne si la date n'est pas au bon format
                        continue;
                    } catch (Exception e) {
                        // Ignorer les autres erreurs
                        continue;
                    }
                }
            }

            System.out.println(reservationsLues + " réservations chargées depuis " + filePath);

        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé : " + filePath);
        } catch (IOException e) {
            System.out.println("Erreur chargement réservations : " + e.getMessage());
        }

        return reservations;
    }

    // Génération de rapport statistique dans un fichier
    public static void genererRapportFichier(String contenu, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {

            writer.write(contenu);

        } catch (IOException e) {
            System.out.println("Erreur génération rapport : " + e.getMessage());
        }
    }

    // Lecture des passagers depuis un fichier CSV
    public static List<Passager> lirePassagersDepuisFichier(String filePath) {
        List<Passager> passagers = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Fichier " + filePath + " non trouvé.");
            return passagers;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;
            boolean firstLine = true;
            int passagersLus = 0;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split("\\|", -1);
                if (parts.length >= 3) {
                    try {
                        String passeport = parts[0].trim();
                        String nom = parts[1].trim();

                        String id = "PASS" + System.currentTimeMillis() % 10000;
                        String adresse = "Non spécifiée";
                        String contact = "Non spécifié";

                        Passager passager = new Passager(id, nom, adresse, contact, passeport);
                        passagers.add(passager);
                        passagersLus++;

                    } catch (Exception e) {
                        // Ignorer les erreurs
                    }
                }
            }

            System.out.println(passagersLus + " passagers lus depuis " + filePath);

        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé : " + filePath);
        } catch (IOException e) {
            System.out.println("Erreur lecture passagers : " + e.getMessage());
        }

        return passagers;
    }

    // Méthode utilitaire pour créer un fichier CSV de vols d'exemple
    public static void creerFichierExempleVols(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {

            writer.write("Code|Dép|Arriv|Date|Heure");
            writer.newLine();
            writer.write("AF123|Paris CDG|New York JFK|15-04|1430");
            writer.newLine();
            writer.write("AF456|Paris CDG|Londres LHR|16-04|1000");
            writer.newLine();
            writer.write("AF789|Paris CDG|Tokyo NRT|17-04|1600");
            writer.newLine();
            writer.write("AF101|Lyon LYS|Barcelone BCN|18-04|0915");
            writer.newLine();
            writer.write("AF202|Marseille MRS|Rome FCO|19-04|1145");

        } catch (IOException e) {
            System.out.println("Erreur création fichier exemple : " + e.getMessage());
        }
    }
}
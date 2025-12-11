// src/main/java/fr/isep/api/AmadeusAPI.java
package fr.isep.api;

import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.*;

public class AmadeusAPI {

    // Clés API de test - À configurer selon votre compte Amadeus
    private static final String CLIENT_ID = "testClientIdAmadeusAPI";
    private static final String CLIENT_SECRET = "testClientSecretAmadeusAPI";
    private static final String FLIGHT_SEARCH_URL = "https://test.api.amadeus.com/v2/shopping/flight-offers";
    private static final String AUTH_URL = "https://test.api.amadeus.com/v1/security/oauth2/token";

    private String accessToken;
    private boolean modeDemo;

    public AmadeusAPI() {
        System.out.println("Initialisation de l'API Amadeus...");
        this.accessToken = obtenirTokenAccess();
        this.modeDemo = (this.accessToken == null);

        if (modeDemo) {
            System.out.println("Mode démo activé - Utilisation de données simulées");
        } else {
            System.out.println("Connecté à l'API Amadeus avec succès");
        }
    }

    private String obtenirTokenAccess() {
        // Vérification si les clés sont configurées
        if (CLIENT_ID.contains("test") || CLIENT_SECRET.contains("test")) {
            System.out.println("Clés API non configurées. Utilisation du mode démo.");
            System.out.println("Pour utiliser l'API réelle, configurez CLIENT_ID et CLIENT_SECRET");
            return null;
        }

        OkHttpClient client = new OkHttpClient();

        String auth = Credentials.basic(CLIENT_ID, CLIENT_SECRET);
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();

        Request request = new Request.Builder()
                .url(AUTH_URL)
                .post(body)
                .addHeader("Authorization", auth)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(jsonResponse);
                return node.get("access_token").asText();
            } else {
                System.err.println("Erreur d'authentification Amadeus: " + response.code());
                return null;
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'obtention du token: " + e.getMessage());
            return null;
        }
    }

    public List<Map<String, String>> rechercherVols(String origine, String destination,
                                                    String dateDepart, int adultes) {
        System.out.println("\nRecherche de vols :");
        System.out.println("- Origine: " + origine);
        System.out.println("- Destination: " + destination);
        System.out.println("- Date: " + dateDepart);
        System.out.println("- Passagers: " + adultes);

        // Mode démo par défaut
        return rechercherVolsDemo(origine, destination, dateDepart, adultes);
    }

    // Méthode pour rechercher des vols en mode réel (si configuré)
    public List<Map<String, String>> rechercherVolsAPI(String origine, String destination,
                                                       String dateDepart, int adultes) {
        if (accessToken == null) {
            System.out.println("Token non disponible - Retour au mode démo");
            return rechercherVolsDemo(origine, destination, dateDepart, adultes);
        }

        List<Map<String, String>> vols = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        try {
            HttpUrl url = HttpUrl.parse(FLIGHT_SEARCH_URL).newBuilder()
                    .addQueryParameter("originLocationCode", origine)
                    .addQueryParameter("destinationLocationCode", destination)
                    .addQueryParameter("departureDate", dateDepart)
                    .addQueryParameter("adults", String.valueOf(adultes))
                    .addQueryParameter("max", "10")
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(jsonResponse);

                // Traitement de la réponse
                JsonNode dataNode = rootNode.get("data");
                if (dataNode != null && dataNode.isArray()) {
                    for (JsonNode volNode : dataNode) {
                        Map<String, String> volInfo = traiterDonneesVol(volNode);
                        vols.add(volInfo);
                    }
                }
            } else {
                System.err.println("Erreur API: " + response.code() + " - " + response.message());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel API: " + e.getMessage());
        }

        return vols;
    }

    private Map<String, String> traiterDonneesVol(JsonNode volNode) {
        Map<String, String> volInfo = new HashMap<>();

        try {
            // Informations de base
            volInfo.put("id", volNode.get("id").asText());
            volInfo.put("type", "Vol commercial");

            // Itinéraire
            JsonNode itineraries = volNode.get("itineraries");
            if (itineraries != null && itineraries.isArray() && itineraries.size() > 0) {
                JsonNode premierItineraire = itineraries.get(0);
                JsonNode segments = premierItineraire.get("segments");

                if (segments != null && segments.isArray() && segments.size() > 0) {
                    JsonNode segment = segments.get(0);
                    volInfo.put("compagnie", segment.get("carrierCode").asText());
                    volInfo.put("numeroVol", segment.get("number").asText());
                    volInfo.put("depart", segment.get("departure").get("iataCode").asText());
                    volInfo.put("arrivee", segment.get("arrival").get("iataCode").asText());
                    volInfo.put("dateDepart", segment.get("departure").get("at").asText());
                    volInfo.put("dateArrivee", segment.get("arrival").get("at").asText());
                }
            }

            // Prix
            JsonNode price = volNode.get("price");
            if (price != null) {
                volInfo.put("prix", price.get("total").asText());
                volInfo.put("devise", price.get("currency").asText());
            }

            // Sièges disponibles
            volInfo.put("siegesDisponibles", "150");

        } catch (Exception e) {
            System.err.println("Erreur lors du traitement des données du vol: " + e.getMessage());
        }

        return volInfo;
    }

    // Version démonstration avec données simulées
    private List<Map<String, String>> rechercherVolsDemo(String origine, String destination,
                                                         String dateDepart, int adultes) {
        List<Map<String, String>> vols = new ArrayList<>();

        // Données de démonstration réalistes
        String[] compagnies = {"Air France", "KLM", "British Airways", "Lufthansa", "Emirates"};
        String[] avions = {"Airbus A320", "Boeing 737", "Airbus A330", "Boeing 777", "Airbus A350"};
        double[] prixBase = {250.0, 280.0, 320.0, 450.0, 520.0};

        Random rand = new Random();

        for (int i = 1; i <= 5; i++) {
            Map<String, String> vol = new HashMap<>();

            int indexCompagnie = rand.nextInt(compagnies.length);
            int indexAvion = rand.nextInt(avions.length);

            double prix = prixBase[rand.nextInt(prixBase.length)] * (0.8 + rand.nextDouble() * 0.4);

            // Calcul des heures de vol aléatoires mais réalistes
            int heureDepart = 6 + rand.nextInt(12); // Entre 6h et 18h
            int minuteDepart = rand.nextInt(4) * 15; // 0, 15, 30 ou 45
            int dureeVol = 60 + rand.nextInt(240); // 1 à 5 heures

            String heureDepartStr = String.format("%02d:%02d", heureDepart, minuteDepart);
            int heureArrivee = heureDepart + (dureeVol / 60);
            int minuteArrivee = minuteDepart + (dureeVol % 60);
            if (minuteArrivee >= 60) {
                heureArrivee++;
                minuteArrivee -= 60;
            }
            String heureArriveeStr = String.format("%02d:%02d", heureArrivee, minuteArrivee);

            vol.put("id", "DEMO" + String.format("%03d", i));
            vol.put("compagnie", compagnies[indexCompagnie]);
            vol.put("numeroVol", "AF" + (1000 + rand.nextInt(9000)));
            vol.put("depart", origine);
            vol.put("arrivee", destination);
            vol.put("dateDepart", dateDepart + "T" + heureDepartStr + ":00");
            vol.put("dateArrivee", dateDepart + "T" + heureArriveeStr + ":00");
            vol.put("prix", String.format("%.2f", prix));
            vol.put("devise", "EUR");
            vol.put("typeAvion", avions[indexAvion]);
            vol.put("siegesDisponibles", String.valueOf(150 + rand.nextInt(100)));
            vol.put("dureeVol", dureeVol + " minutes");
            vol.put("escales", String.valueOf(rand.nextInt(2)));
            vol.put("classe", rand.nextBoolean() ? "Economique" : "Affaires");

            vols.add(vol);
        }

        return vols;
    }

    public void afficherVolsDisponibles(List<Map<String, String>> vols) {
        if (vols.isEmpty()) {
            System.out.println("Aucun vol disponible pour ces critères");
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("VOLS DISPONIBLES");
        System.out.println("=".repeat(80));

        if (modeDemo) {
            System.out.println("Mode démonstration - Données simulées");
        }

        for (int i = 0; i < vols.size(); i++) {
            Map<String, String> vol = vols.get(i);

            System.out.println("\n" + (i + 1) + ". " + vol.get("compagnie") +
                    " - Vol " + vol.get("numeroVol"));
            System.out.println("   " + vol.get("depart") + " → " + vol.get("arrivee"));

            // Formatage des dates
            String dateDepart = vol.get("dateDepart");
            String dateArrivee = vol.get("dateArrivee");

            if (dateDepart != null && dateDepart.length() >= 16) {
                String heureDepart = dateDepart.substring(11, 16);
                String heureArrivee = dateArrivee.substring(11, 16);
                System.out.println("   Départ: " + heureDepart + " | Arrivée: " + heureArrivee);
            }

            System.out.println("   Avion: " + vol.get("typeAvion"));
            System.out.println("   Classe: " + vol.get("classe") +
                    " | Sièges disponibles: " + vol.get("siegesDisponibles"));
            System.out.println("   Durée: " + vol.get("dureeVol") +
                    " | Escales: " + vol.get("escales"));
            System.out.println("   PRIX: " + vol.get("prix") + " " + vol.get("devise"));
            System.out.println("-".repeat(80));
        }

        System.out.println("\n" + vols.size() + " vols trouvés");
        System.out.println("=".repeat(80));
    }

    // Nouvelle méthode pour intégrer un vol de l'API dans notre système
    public void integrerVolDansSysteme(Map<String, String> volInfo, List<fr.isep.model.vol.Vol> volsSysteme) {
        try {
            String numeroVol = volInfo.get("numeroVol");
            String origine = volInfo.get("depart");
            String destination = volInfo.get("arrivee");
            String dateDepartStr = volInfo.get("dateDepart");

            // Conversion de la date
            java.time.LocalDateTime dateDepart = java.time.LocalDateTime.parse(
                    dateDepartStr.replace("T", " "),
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );

            // Calcul de la date d'arrivée
            String dureeStr = volInfo.get("dureeVol").replace(" minutes", "");
            int dureeMinutes = Integer.parseInt(dureeStr);
            java.time.LocalDateTime dateArrivee = dateDepart.plusMinutes(dureeMinutes);

            // Création du vol
            fr.isep.model.vol.Vol nouveauVol = new fr.isep.model.vol.Vol(
                    numeroVol, origine, destination, dateDepart, dateArrivee
            );

            volsSysteme.add(nouveauVol);
            System.out.println("Vol " + numeroVol + " intégré au système avec succès");

        } catch (Exception e) {
            System.err.println("Erreur lors de l'intégration du vol: " + e.getMessage());
        }
    }

    public boolean estModeDemo() {
        return modeDemo;
    }
}
// src/test/java/fr/isep/model/vol/VolTest.java
package fr.isep.model.vol;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class VolTest {

    @Test
    void testCreationVol() {
        LocalDateTime depart = LocalDateTime.of(2024, 4, 15, 14, 30);
        LocalDateTime arrivee = LocalDateTime.of(2024, 4, 15, 16, 30);

        Vol vol = new Vol("AF123", "Paris", "Londres", depart, arrivee);

        assertEquals("AF123", vol.getNumeroVol());
        assertEquals("Paris", vol.getOrigine());
        assertEquals("Londres", vol.getDestination());
        assertEquals("Planifié", vol.getEtat());
    }

    @Test
    void testPlanifierVol() {
        LocalDateTime depart = LocalDateTime.now();
        LocalDateTime arrivee = depart.plusHours(2);

        Vol vol = new Vol("AF123", "Paris", "Londres", depart, arrivee);

        assertEquals("Planifié", vol.getEtat());
    }

    @Test
    void testAnnulerVol() {
        LocalDateTime depart = LocalDateTime.now();
        LocalDateTime arrivee = depart.plusHours(2);

        Vol vol = new Vol("AF123", "Paris", "Londres", depart, arrivee);
        vol.annulerVol();

        assertEquals("Annulé", vol.getEtat());
    }

    @Test
    void testAjouterPassager() {
        LocalDateTime depart = LocalDateTime.now();
        LocalDateTime arrivee = depart.plusHours(2);

        Vol vol = new Vol("AF123", "Paris", "Londres", depart, arrivee);
        vol.ajouterPassager("P001");
        vol.ajouterPassager("P002");

        assertEquals(2, vol.listingPassager().size());
        assertTrue(vol.listingPassager().contains("P001"));
        assertTrue(vol.listingPassager().contains("P002"));
    }
}
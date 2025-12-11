// src/test/java/fr/isep/model/personne/PassagerTest.java
package fr.isep.model.personne;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PassagerTest {

    @Test
    void testCreationPassager() {
        Passager passager = new Passager("P001", "Dupont", "Paris", "0102030405", "FR123456");

        assertEquals("P001", passager.getIdentifiant());
        assertEquals("Dupont", passager.getNom());
        assertEquals("FR123456", passager.getPasseport());
    }

    @Test
    void testReserverVol() {
        Passager passager = new Passager("P001", "Dupont", "Paris", "0102030405", "FR123456");
        passager.reserverVol("AF123", "RES001");

        assertEquals(1, passager.obtenirReservations().size());
        assertTrue(passager.obtenirReservations().contains("RES001"));
    }

    @Test
    void testAnnulerReservation() {
        Passager passager = new Passager("P001", "Dupont", "Paris", "0102030405", "FR123456");
        passager.reserverVol("AF123", "RES001");
        passager.reserverVol("AF456", "RES002");

        assertEquals(2, passager.obtenirReservations().size());

        passager.annulerReservation("RES001");
        assertEquals(1, passager.obtenirReservations().size());
        assertFalse(passager.obtenirReservations().contains("RES001"));
    }
}
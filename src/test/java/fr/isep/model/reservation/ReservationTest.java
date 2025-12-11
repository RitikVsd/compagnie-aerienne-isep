// src/test/java/fr/isep/model/reservation/ReservationTest.java
package fr.isep.model.reservation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Test
    void testCreationReservation() {
        Reservation reservation = new Reservation("RES001", "P001", "AF123");

        assertEquals("RES001", reservation.getNumeroReservation());
        assertEquals("P001", reservation.getIdPassager());
        assertEquals("AF123", reservation.getNumeroVol());
        assertEquals("En attente", reservation.getStatut());
    }

    @Test
    void testConfirmerReservation() {
        Reservation reservation = new Reservation("RES001", "P001", "AF123");
        reservation.confirmerReservation();

        assertEquals("Confirmée", reservation.getStatut());
    }

    @Test
    void testAnnulerReservation() {
        Reservation reservation = new Reservation("RES001", "P001", "AF123");
        reservation.confirmerReservation();
        reservation.annulerReservation();

        assertEquals("Annulée", reservation.getStatut());
    }
}
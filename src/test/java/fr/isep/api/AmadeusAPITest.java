// src/test/java/fr/isep/api/AmadeusAPITest.java
package fr.isep.api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;

class AmadeusAPITest {

    @Test
    void testCreationAmadeusAPI() {
        AmadeusAPI api = new AmadeusAPI();
        assertNotNull(api);
    }

    @Test
    void testRechercherVolsDemo() {
        AmadeusAPI api = new AmadeusAPI();
        List<Map<String, String>> vols = api.rechercherVols("CDG", "JFK", "2024-12-15", 2);

        assertNotNull(vols);
        assertFalse(vols.isEmpty());
        assertTrue(vols.size() > 0);
    }

    @Test
    void testStructureDonneesVol() {
        AmadeusAPI api = new AmadeusAPI();
        List<Map<String, String>> vols = api.rechercherVols("CDG", "JFK", "2024-12-15", 1);

        if (!vols.isEmpty()) {
            Map<String, String> premierVol = vols.get(0);

            assertTrue(premierVol.containsKey("id"));
            assertTrue(premierVol.containsKey("compagnie"));
            assertTrue(premierVol.containsKey("depart"));
            assertTrue(premierVol.containsKey("arrivee"));
            assertTrue(premierVol.containsKey("prix"));

            assertNotNull(premierVol.get("id"));
            assertNotNull(premierVol.get("compagnie"));
        }
    }
}
package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

import java.time.LocalDate;

class testEmploye 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();

@Test
void testDatesValides() throws SauvegardeImpossible
{
    Ligue ligue = gestionPersonnel.addLigue("Ligue Dates");

    Employe employe = ligue.addEmploye(
        "Dupont", "Jean",
        "jean@test.fr", "pass123"
    );

    LocalDate arrivee = LocalDate.of(2026, 9, 1);
    LocalDate depart = LocalDate.of(2026, 9, 30);

    employe.setDateArrivee(arrivee);
    employe.setDateDepart(depart);

    assertEquals(arrivee, employe.getDateArrivee());
    assertEquals(depart, employe.getDateDepart());
}

@Test
void testDatesIncoherentes() throws SauvegardeImpossible
{
    Ligue ligue = gestionPersonnel.addLigue("Ligue Erreur");

    Employe employe = ligue.addEmploye(
        "Martin", "Paul",
        "paul@test.fr", "pass123"
    );

    employe.setDateArrivee(LocalDate.of(2026, 9, 1));

    assertThrows(DatesIncoherentes.class, () ->
        employe.setDateDepart(LocalDate.of(2026, 8, 1))
    );
}
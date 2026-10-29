package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import personnel.*;

class testEmployeDates
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();

	private Employe nouvelEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Ligue dates");
		return ligue.addEmploye("Dupont", "Marie", "m.dupont@gmail.com", "azerty");
	}

	@Test
	void departVideAccepte() throws SauvegardeImpossible
	{
		Employe employe = nouvelEmploye();
		employe.setDateArrivee(LocalDate.of(2026, 1, 10));
		employe.setDateDepart(null);
		assertNull(employe.getDateDepart());
	}

	@Test
	void departApresArriveeAccepte() throws SauvegardeImpossible
	{
		Employe employe = nouvelEmploye();
		employe.setDateArrivee(LocalDate.of(2026, 1, 10));
		employe.setDateDepart(LocalDate.of(2026, 6, 30));
		assertEquals(LocalDate.of(2026, 6, 30), employe.getDateDepart());
	}

	@Test
	void departEgalArriveeAccepte() throws SauvegardeImpossible
	{
		Employe employe = nouvelEmploye();
		employe.setDateArrivee(LocalDate.of(2026, 1, 10));
		employe.setDateDepart(LocalDate.of(2026, 1, 10));
		assertEquals(LocalDate.of(2026, 1, 10), employe.getDateDepart());
	}

	@Test
	void departAvantArriveeRefuse() throws SauvegardeImpossible
	{
		Employe employe = nouvelEmploye();
		employe.setDateArrivee(LocalDate.of(2026, 1, 10));
		assertThrows(DatesIncoherentes.class,
			() -> employe.setDateDepart(LocalDate.of(2026, 1, 5)));
	}

	@Test
	void arriveeApresDepartRefusee() throws SauvegardeImpossible
	{
		Employe employe = nouvelEmploye();
		employe.setDateArrivee(LocalDate.of(2026, 1, 10));
		employe.setDateDepart(LocalDate.of(2026, 6, 30));
		assertThrows(DatesIncoherentes.class,
			() -> employe.setDateArrivee(LocalDate.of(2026, 7, 1)));
	}
}
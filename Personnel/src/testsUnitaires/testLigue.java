package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty"); 
		assertEquals(employe, ligue.getEmployes().first());
	}

	@Test
	void testSetNom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		ligue.setNom("Tennis");
		assertEquals("Tennis", ligue.getNom());
	}

	@Test
	void testSupprimerLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Badminton");
		ligue.remove();
		assertFalse(gestionPersonnel.getLigues().contains(ligue));
	}

	@Test
	void testChangerAdministrateur() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Football");
		Employe employe1 = ligue.addEmploye("Dupont", "Jean", "j.dupont@mail.fr", "pass1");
		Employe employe2 = ligue.addEmploye("Martin", "Sophie", "s.martin@mail.fr", "pass2");
		
		// Attribution du premier admin
		ligue.setAdministrateur(employe1);
		assertEquals(employe1, ligue.getAdministrateur());
		
		// Changement d'admin vers le second
		ligue.setAdministrateur(employe2);
		assertEquals(employe2, ligue.getAdministrateur());
	}

	@Test
	void testSuppressionAdministrateur() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Natation");
		Employe employe = ligue.addEmploye("Durand", "Paul", "p.durand@mail.fr", "pass");
		
		ligue.setAdministrateur(employe);
		assertEquals(employe, ligue.getAdministrateur());
		
		// Suppression de l'employé qui était administrateur
		employe.remove();
		
	
		assertNull(ligue.getAdministrateur());
	}
}
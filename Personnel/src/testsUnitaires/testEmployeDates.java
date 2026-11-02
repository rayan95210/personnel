package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

class testEmploye 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();

	@Test
	void testGettersEtSettersEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Ligue Sport");
		Employe employe = ligue.addEmploye("Dupont", "Jean", "jean.dupont@gmail.com", "pass123");
		
		
		assertEquals("Dupont", employe.getNom());
		assertEquals("Jean", employe.getPrenom());
		assertEquals("jean.dupont@gmail.com", employe.getMail());
		assertEquals(ligue, employe.getLigue());
		
		
		employe.setNom("Martin");
		employe.setPrenom("Sophie");
		employe.setMail("sophie.martin@gmail.com");
		employe.setPassword("newpass");
		
		assertEquals("Martin", employe.getNom());
		assertEquals("Sophie", employe.getPrenom());
		assertEquals("sophie.martin@gmail.com", employe.getMail());
	}

	@Test
	void testSupprimerEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Ligue Test Suppression");
		Employe employe = ligue.addEmploye("Durand", "Paul", "paul.durand@gmail.com", "azerty");
		
	
		assertTrue(ligue.getEmployes().contains(employe));
		
		
		employe.remove();
		
		
		assertFalse(ligue.getEmployes().contains(employe));
	}
}
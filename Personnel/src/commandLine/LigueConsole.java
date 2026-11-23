package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.util.ArrayList;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;

import personnel.*;

public class LigueConsole 
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;

	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
	}

	Menu menuLigues()
	{
		Menu menu = new Menu("Gérer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> {System.out.println(gestionPersonnel.getLigues());});
	}

	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "l", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administrée par " + ligue.getAdministrateur());
				}
		);
	}
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "l", () -> {System.out.println(ligue.getEmployes());});
	}

	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> 
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}
	
	private Menu editerLigue(Ligue ligue)
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
		menu.add(changerAdministrateur(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> {ligue.setNom(getString("Nouveau nom : "));});
	}

	private List<Ligue> selectionnerLigue()
	{
		return new List<Ligue>("Sélectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element)
				);
	}
	

private Option ajouterEmploye(final Ligue ligue)
{
    return new Option("ajouter un employé", "a",
            () ->
            {
                String nom = getString("nom : ");
                String prenom = getString("prenom : ");
                String mail = getString("mail : ");
                String password = getString("password : ");

                LocalDate dateArrivee = saisirDate(
                    "Date d'arrivée (AAAA-MM-JJ) : ", true
                );

                LocalDate dateDepart;

                // On vérifie que la date de départ est cohérente
                while (true)
                {
                    dateDepart = saisirDate(
                        "Date de départ (AAAA-MM-JJ, vide si inconnue) : ",
                        false
                    );

                    try
                    {
                        if (dateDepart != null &&
                            dateDepart.isBefore(dateArrivee))
                        {
                            throw new DatesIncoherentes();
                        }

                        break;
                    }
                    catch (DatesIncoherentes exception)
                    {
                        System.err.println(
                            "Erreur : la date de départ doit être après la date d'arrivée."
                        );
                    }
                }

                Employe employe = ligue.addEmploye(
                    nom, prenom, mail, password
                );

                employe.setDateArrivee(dateArrivee);

                if (dateDepart != null)
                {
                    employe.setDateDepart(dateDepart);
                }
            }
    );
}

private LocalDate saisirDate(String message, boolean obligatoire)
{
    while (true)
    {
        String saisie = getString(message).trim();

        // Une date vide est autorisée si elle n'est pas obligatoire
        if (saisie.isEmpty() && !obligatoire)
        {
            return null;
        }

        try
        {
            return LocalDate.parse(saisie);
        }
        catch (DateTimeParseException exception)
        {
            System.err.println(
                "Format incorrect. Utilisez AAAA-MM-JJ (exemple : 2026-09-01)."
            );
        }
    }
}
private Menu gererEmployes(Ligue ligue)
{
    Menu menu = new Menu("Gérer les employés de " + ligue.getNom(), "e");
    menu.add(afficherEmployes(ligue));
    menu.add(ajouterEmploye(ligue));
    menu.add(selectionnerEmploye(ligue));
    menu.addBack("q");
    return menu;
}
private List<Employe> selectionnerEmploye(final Ligue ligue)
{
    return new List<>("Sélectionner un employé", "s",
            () -> new ArrayList<>(ligue.getEmployes()),
            (index, employe) -> menuEmploye(employe)
    );
}
private Menu menuEmploye(final Employe employe)
{
    Menu menu = new Menu("Gérer " + employe.getNom());

    menu.add(employeConsole.editerEmploye(employe));

    menu.add(new Option("Supprimer cet employé", "s",
            () -> {employe.remove();}
    ));

    menu.addBack("q");

    return menu;
}

private List<Employe> changerAdministrateur(final Ligue ligue)
{
	return new List<Employe>("Changer d'administrateur", "c",
			() -> administrateursPossibles(ligue),
			(index, employe) -> {ligue.setAdministrateur(employe);}
			);
}

private ArrayList<Employe> administrateursPossibles(final Ligue ligue)
{
	ArrayList<Employe> candidats = new ArrayList<>();
	candidats.add(gestionPersonnel.getRoot());
	candidats.addAll(ligue.getEmployes());
	return candidats;
}

	private Option supprimer(Ligue ligue)
	{
		return new Option("Supprimer", "d", () -> {ligue.remove();});
	}
	
}

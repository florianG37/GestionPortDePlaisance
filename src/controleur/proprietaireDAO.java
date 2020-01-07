package controleur;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import modele.Proprietaire;

public class proprietaireDAO {
	
	/**
	 * Creer un proprietaire dans la BDD
	 * @param nom nom du proprio
	 * @param adresse adresse du proprio
	 */
	public static void ajouterProprietaire(String nom, String adresse){
		Proprietaire proprio = new Proprietaire(nom, adresse, new ArrayList());
		 EntityManager em =SetupEM.getEm();
	     em.getTransaction().begin();
	     //Ajout du rayon dans la bdd
	     em.persist(proprio);
	     em.getTransaction().commit();
	}
	/**
	 * Modifier un proprietaire
	 * @param proprio le proprietaire modifie
	 */
	public static void modifierProprietaire(Proprietaire proprio){
		   EntityManager em =SetupEM.getEm();

	        em.getTransaction().begin();

	        em.merge(proprio);

	        em.getTransaction().commit();
	}
	/**
	 * Supprimer un proprietaire
	 * @param idProprioASupprimer
	 */
	public static void supprimerProprietaire(Proprietaire proprio){
		EntityManager em =SetupEM.getEm();
        em.getTransaction().begin();

        em.remove(proprio);

        em.getTransaction().commit();
	}
	/**
	 * Trouver proprietaire avec son id
	 * @param idProprio
	 * @return
	 */
	public static Proprietaire trouverProprietaireAvecSonId(int idProprio){
		EntityManager em =SetupEM.getEm();
        em.getTransaction().begin();

        Proprietaire proprio = em.find(Proprietaire.class, idProprio);

        em.getTransaction().commit();
        
        return proprio;
	}
}

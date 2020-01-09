package controleur.dao;


import java.util.List;

import javax.persistence.EntityManager;


import javax.persistence.Query;

import modele.Bateau;
import modele.Emplacement;
import modele.Quai;

public class EmplacementDAO {
	/**
	 * Creer un emplacement 
	 * @param code code de l'emplacement
	 * @param taille taille de l'emplacement
	 * @param bateau bateau associe a l'emplacement 
	 * @param quai quai de l'emplacement
	 */
	public static void ajouterEmplacement(int code, double taille,Bateau bateau, Quai quai){
		Emplacement emplacement = new Emplacement(code, taille, bateau, quai);
		EntityManager em = SetupEM.getEm();
		em.getTransaction().begin();
		em.persist(emplacement);
		em.getTransaction().commit();
		
	}
	/**
	 * Supprimer un emplacement
	 * @param quai Emplacement a supprimer
	 */
	public static void supprimerEmplacement(Emplacement emplacement){
		EntityManager em = SetupEM.getEm();
		em.getTransaction().begin();
		em.remove(emplacement);
		em.getTransaction().commit();
		
	}
	/**
	 * Recuperer tous les emplacements d'un quai
	 * @param quai quai selectione
	 * @return liste d'emplacement
	 */
	public static List<Emplacement> recupererLesEmplacementsDunQuai(Quai quai){
		Query requete = SetupEM.getEm().createQuery("from Emplacement e where e.quai_code = ?1");
		requete.setParameter(1, quai.getCode());
		List<Emplacement> listeEmplacement = requete.getResultList();
		return listeEmplacement;		
	}
	

}
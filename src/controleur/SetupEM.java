package controleur;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SetupEM {

    private static EntityManager em;

    public SetupEM() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("portDePlaisance");
        em = emf.createEntityManager();
    }

    public static EntityManager getEm() {
        return em;
    }

    public static void setEm(EntityManager em) {
        SetupEM.em = em;
    }
}
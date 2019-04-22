package entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory emf;
    static{
        // Application Managed EntityManager
        emf = Persistence.createEntityManagerFactory("leanJPA");
    }

    public static EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}  

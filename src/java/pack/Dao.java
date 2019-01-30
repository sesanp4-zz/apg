/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import entities.Status;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author centricgateway
 */
public class Dao {
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("cgw_apgPU");
    EntityManager em = emf.createEntityManager();
    
    public String postStatus(Status status){
        
        em.getTransaction().begin();
        em.persist(status);
        em.getTransaction().commit();
        return "Transaction Status Posted Successfully";   
    }
    
    public Status getStatusByOperationId(String id){       
        em.getTransaction().begin();
        Query query = em.createQuery("select status from StatusEntity status where status.operationId=:id");
        query.setParameter("id", id);
        Status status =(Status)query.getSingleResult();
        em.getTransaction().commit();
        return status;
    } 
    
}

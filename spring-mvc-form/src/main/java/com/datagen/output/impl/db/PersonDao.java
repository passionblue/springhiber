package com.datagen.output.impl.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("personDao")
@Transactional(propagation = Propagation.REQUIRED)
public class PersonDao {

    private static final String SELECT_QUERY = "select p from Person p";

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void executeSql(String sql) {
        
        EntityManager manager = getEntityManager(); 
        Query q = manager.createNativeQuery(sql);
        q.executeUpdate();
        
    }
    
}

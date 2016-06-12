package com.datagen.source.adapter.db;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("dbInDao")
@Transactional(propagation = Propagation.REQUIRED)
public class DBInDao {

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
        List list = q.getResultList();
        
        System.out.println(list);
    }
    
}

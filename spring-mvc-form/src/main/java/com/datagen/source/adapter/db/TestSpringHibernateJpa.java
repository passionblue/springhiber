package com.datagen.source.adapter.db;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringHibernateJpa {

    public static void main(String[] args) {
        System.out.println("************** BEGINNING PROGRAM **************");

        ApplicationContext context = new ClassPathXmlApplicationContext("datagen/adapter/db/spring-config-mysql-in.xml");
        DBInService dbOutService = (DBInService) context.getBean("DBInService");

        dbOutService.getDbInDao().executeSql("select * from chur_payee");
        
        System.out.println("************** ENDING PROGRAM *****************");
    }
}

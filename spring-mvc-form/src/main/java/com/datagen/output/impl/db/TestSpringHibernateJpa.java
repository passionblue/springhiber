package com.datagen.output.impl.db;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringHibernateJpa {

    public static void main(String[] args) {
        System.out.println("************** BEGINNING PROGRAM **************");

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        DBOutService dbOutService = (DBOutService) context.getBean("personService");

//        List<Person> persons = personService.fetchAllPersons();
//        System.out.println("The list of all persons = " + persons);

        System.out.println("************** ENDING PROGRAM *****************");
    }
}

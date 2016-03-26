package com.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringHibernateJpa {

    public static void main(String[] args) {
        System.out.println("************** BEGINNING PROGRAM **************");

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        PersonService personService = (PersonService) context.getBean("personService");

        for (int i = 0; i < 5; i++) {

            Person person = new Person();
            person.setName("Alba");
            person.setEmail("alba@gmail.com");
            // person.setId(3);
            personService.addPerson(person);
            System.out.println("Person : " + person + " added successfully");
        }


        List<Person> persons = personService.fetchAllPersons();
        System.out.println("The list of all persons = " + persons);

        System.out.println("************** ENDING PROGRAM *****************");
    }
}

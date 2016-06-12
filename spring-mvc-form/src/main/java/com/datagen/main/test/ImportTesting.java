package com.datagen.main.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.datagen.main.AutositeDataGenerator;

public class ImportTesting {

    private static Logger m_logger = LoggerFactory.getLogger(ImportTesting.class);

    public static void main(String[] args) throws Exception {
        
        ApplicationContext c = new ClassPathXmlApplicationContext("applicationContext-datagen.xml");
        AutositeDataGenerator gen = (AutositeDataGenerator) c.getBean("dataGenerator");

        long start = System.currentTimeMillis();
        gen.generate("datagen/configuration-main-import-testing.xml");
        long end = System.currentTimeMillis();
        
        System.out.println("Time Taken " + (end-start));
        
//        System.gc();
//
//        for (int i = 0; i < 10000; i++) {
//            Thread.sleep(1000);
//            System.gc();
//        }
        
    }

}

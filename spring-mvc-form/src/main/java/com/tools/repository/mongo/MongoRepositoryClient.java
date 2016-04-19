package com.tools.repository.mongo;

import org.apache.commons.lang3.RandomStringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoRepositoryClient {

    private static Logger m_logger = LoggerFactory.getLogger(MongoRepositoryClient.class);
    
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    
    public MongoRepositoryClient() throws Exception {
        mongoClient = new MongoClient("192.168.1.16", 27017);
        
        mongoDatabase = mongoClient.getDatabase("x");
        
        
    }
    
    public void test(int count) throws Exception {
        
        MongoCollection<Document> collection = mongoDatabase.getCollection("x");

        for (int j = 0; j < count; j++) {
            Document doc = new Document("name", "MongoDB");
            
            for (int i = 0; i < 30; i++) {
                doc.append(String.valueOf(i),  RandomStringUtils.randomAlphanumeric(30));
            }
            
            m_logger.debug(doc + "");
            collection.insertOne(doc);
        }
        
    }
    
    public void testFind() {
        FindIterable<Document> iterable = mongoDatabase.getCollection("x").find(Filters.text("inQUQ4URv5pnhnsYYWYitctQgoKLVs")); // exact search
        
        for (Document iterable_element : iterable) {
            System.out.println("--->" + iterable_element);
        }
        
    }
    
    public void testFind2() {
        FindIterable<Document> iterable = mongoDatabase.getCollection("x").find(Filters.regex("*", "inQUQ4URv5pnhnsYYWYitctQgoKLVs"));
        
        for (Document iterable_element : iterable) {
            System.out.println("--->" + iterable_element);
        }
        
    }
    
    
    
    public static void main(String[] args) throws Exception  {
//        new MongoRepositoryClient().test(100);;
        new MongoRepositoryClient().testFind2();;
    }
}

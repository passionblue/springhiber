package com.mkyong.form.util.json;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.mkyong.form.model.Phone;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class JSONUtil {

    
    
    public static <T> List<T> loadObjectFromJSONFile(String filename, Class<T> clazz) throws Exception {
        
        String json = MyStringUtil.readFile(filename, Charset.defaultCharset());
        ObjectMapper mapper = new ObjectMapper();
        List<T> myObjects = mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
        
        return myObjects;
    }
    
    
    
    public static String  marshalize(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(os, obj);
        String json = new String(os.toByteArray());
        return json;
    }

    public static <T> T demarshalize(String json, Class<T> clazz) throws Exception {
        
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json.getBytes());
        T obj = objectMapper.treeToValue(rootNode, clazz);
        
        return obj;
    }
    
    public static void main(String[] args) throws Exception {
        
        List<Phone> myObjects = loadObjectFromJSONFile("phones.json", Phone.class);

        for (Phone phone : myObjects) {
            System.out.println("############# " + phone);
        }
    }
    
    public static void main2(String[] args) throws Exception {
        JsonFactory jfactory = new JsonFactory();

        /*** read from file ***/
        JsonParser jParser = jfactory.createParser(new File("C:/Users/joshua/git/springhiber/spring-mvc-form/src/main/webapp/phones/phones.json"));
        
        String json = MyStringUtil.readFile("phones.json", Charset.defaultCharset());
        
        ObjectMapper mapper = new ObjectMapper();
        List<Phone> myObjects = mapper.readValue(json, new TypeReference<List<Phone>>(){});

        for (Phone phone : myObjects) {
            
            System.out.println("############# " + phone);
        }
        
    }
}

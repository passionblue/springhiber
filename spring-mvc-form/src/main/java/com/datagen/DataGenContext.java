package com.datagen;

import java.util.UUID;

import org.apache.commons.configuration2.XMLConfiguration;

import com.datagen.util.ConfigUtils;

public class DataGenContext {

    private String      id;
    private String      name; //Not unique
    private String      fileName;
    private long        timestamp;
    private XMLConfiguration   xmlConfig;
    

    public DataGenContext(String id, String name, String fileName, long timestamp, XMLConfiguration xmlConfig) {
        super();
        this.id = id;
        this.name = name;
        this.fileName = fileName;
        this.timestamp = timestamp;
        this.xmlConfig = xmlConfig;
    }

    //Default Constructor
    public DataGenContext() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public XMLConfiguration getXmlConfig() {
        return xmlConfig;
    }

    public void setXmlConfig(XMLConfiguration xmlConfig) {
        this.xmlConfig = xmlConfig;
    }

    public static DataGenContext getNewToken(String fileName) throws Exception {
        
        XMLConfiguration xmlConfig = ConfigUtils.loadConfiguration(fileName);
        String name = xmlConfig.getString("Name");
        DataGenContext context =  new DataGenContext(UUID.randomUUID().toString()   , name, fileName, System.currentTimeMillis(), xmlConfig);
        
        return context;
    }
    
}

package com.datagen;

import java.util.UUID;

import org.apache.commons.configuration2.XMLConfiguration;

import com.datagen.util.ConfigUtils;

public class DataGenContext {

    private String              id;
    private String              name; //Not unique
    private String              fileName;
    private long                timestamp;
    private XMLConfiguration    xmlConfig;
    private DataGenContext      mainContext;// if it is child
    private int                 dataCount;
    private boolean             preLoadContext;
    
    public DataGenContext(String id, String name, String fileName, long timestamp, XMLConfiguration xmlConfig) {
        super();
        this.id = id;
        this.name = name;
        this.fileName = fileName;
        this.timestamp = timestamp;
        this.xmlConfig = xmlConfig;
    }

    public DataGenContext(String id, String name, String fileName, long timestamp, XMLConfiguration xmlConfig, boolean isPreload) {
        super();
        this.id = id;
        this.name = name;
        this.fileName = fileName;
        this.timestamp = timestamp;
        this.xmlConfig = xmlConfig;
        this.preLoadContext = isPreload;
    }
    
    //Default Constructor
    public DataGenContext() {
    }

    public String getBaseId() {
        return id;
    }
    
    public String getId(String dataSet) {
        return id + (dataSet==null? "":"/" + dataSet);
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


    
    public DataGenContext getPreLoadToken(String fileName) throws Exception {
        
        XMLConfiguration xmlConfig = ConfigUtils.loadConfiguration(fileName);
        String name = xmlConfig.getString("Name");
        int count = xmlConfig.getInt("Count", 1000); //TODO about default count
        
        DataGenContext context =  new DataGenContext(id, name, fileName, timestamp, xmlConfig, true);
        
        context.setDataCount(count);
        context.setMainContext(this);
        return context;
    }
    
    
    public static DataGenContext getNewToken(String fileName) throws Exception {
        
        XMLConfiguration xmlConfig = ConfigUtils.loadConfiguration(fileName);
        String name = xmlConfig.getString("Name");
        DataGenContext context =  new DataGenContext(UUID.randomUUID().toString(), name, fileName, System.currentTimeMillis(), xmlConfig);
        int count = xmlConfig.getInt("Count", 1000); //TODO about default count
        context.setDataCount(count);
        
        return context;
    }


    public DataGenContext getMainContext() {
        return mainContext;
    }

    public void setMainContext(DataGenContext mainContext) {
        this.mainContext = mainContext;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public boolean isPreLoadContext() {
        return preLoadContext;
    }

    public void setPreLoadContext(boolean preLoadContext) {
        this.preLoadContext = preLoadContext;
    }

    @Override
    public String toString() {
        return "DataGenContext [id=" + id + ", name=" + name + ", fileName=" + fileName + ", timestamp=" + timestamp + ", xmlConfig=" + xmlConfig + ", mainContext=" + mainContext + ", dataCount="
                + dataCount + ", preLoadContext=" + preLoadContext + "]";
    }
}

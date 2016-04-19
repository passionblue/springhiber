package com.datagen.meta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldMetaDataManager {

    private static Logger m_logger = LoggerFactory.getLogger(FieldMetaDataManager.class);

    private Map<String, FieldMetaData> map = new ConcurrentHashMap<>();
    
    
    private static FieldMetaDataManager m_instance = new FieldMetaDataManager();

    public static FieldMetaDataManager getInstance() {
        return m_instance;
    }

    private FieldMetaDataManager() {

    }
    
    public void addMetaData(String fieldName, FieldMetaData data){
        if ( map.containsKey(fieldName)) {
            throw new IllegalStateException("Field name[" + fieldName+ "] already defined.");
        }
        map.put(fieldName, data);
    }
    
    public FieldMetaData getMetaData(String fieldName){
        return map.get(fieldName);
    }
    
}

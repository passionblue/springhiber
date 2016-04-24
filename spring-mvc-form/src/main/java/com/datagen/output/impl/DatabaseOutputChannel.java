package com.datagen.output.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.datagen.FData;
import com.datagen.FDataRow;
import com.datagen.output.impl.db.PersonService;

public class DatabaseOutputChannel extends  AbstractOutputChannel<FDataRow> {

    private static Logger m_logger = LoggerFactory.getLogger(DatabaseOutputChannel.class);

    
    private PersonService personService;
    private String tableName;
    private Map<String, FieldMeta> fieldMetaMap;
    private String configFileName;
    
    public DatabaseOutputChannel(String id) {
        super(id);
        
    }
    
    @Override
    public void open() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(configFileName);
        personService = (PersonService) context.getBean("personService");
    }

    @Override
    void writeToChannel(FDataRow row) throws Exception {
        
        List<FData> dataList = row.getData(false);

        StringBuilder sqlString = new StringBuilder();
        sqlString.append("INSERT INTO ").append(tableName).append(" (");
        
        StringBuilder valueString = new StringBuilder();
        
        for (FData fData : dataList) {
            
            FieldMeta fieldMeta = fieldMetaMap.get(fData.getFieldName());

            if ( fData.getRawFormat() == null) { 
                continue;
            }
            
            if ( fieldMeta != null) {

                sqlString.append(fieldMeta.getName()).append(",");
                
                if ( fieldMeta.getDataClass() == String.class ) {
                    valueString.append("'").append(fData.getRawFormat().toString()).append("'").append(",");
                } else {
                    valueString.append(fData.getRawFormat().toString()).append(",");
                }
            }
        }
        
        
//        sqlString = sqlString.deleteCharAt(sqlString.length()-1);
//        valueString = valueString.deleteCharAt(valueString.length()-1);

        sqlString.append("id");
        valueString.append(RandomUtils.nextInt(0, 1000000));
        
        sqlString.append(") VALUES (").append(valueString).append(")");

        personService.getPersonDao().executeSql(sqlString.toString());
    }

    @Override
    void writeHeaderToChannel(FDataRow row) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    boolean reachedMax() {
        // TODO Auto-generated method stub
        return false;
    }
    
    public PersonService getPersonService() {
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, FieldMeta> getFieldMetaMap() {
        return fieldMetaMap;
    }

    public void setFieldMetaMap(Map<String, FieldMeta> fieldMetaMap) {
        this.fieldMetaMap = fieldMetaMap;
    }

    public String getConfigFileName() {
        return configFileName;
    }

    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }




    public static class FieldMeta {
        
        private String name;
        private String map;
        private Class  dataClass;
        
        public FieldMeta(String name, String map, Class dataClass) {
            super();
            this.name = name;
            this.map = map;
            this.dataClass = dataClass;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMap() {
            return map;
        }

        public void setMap(String map) {
            this.map = map;
        }

        public Class getDataClass() {
            return dataClass;
        }

        public void setDataClass(Class dataClass) {
            this.dataClass = dataClass;
        }
        
        
        
        
    }
    

}

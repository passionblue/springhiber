package com.datagen.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.tree.DefaultExpressionEngine;
import org.apache.commons.configuration2.tree.DefaultExpressionEngineSymbols;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.configuration2.tree.NodeNameMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FDataSource;

public class ConfigUtils {

    private static Logger m_logger = LoggerFactory.getLogger(ConfigUtils.class);
    
    public static void checkKeys(HierarchicalConfiguration conf ){
        Iterator<String> keys = conf.getKeys();
        for (Iterator iterator = keys; iterator.hasNext();) {
            System.out.println(iterator.next());
        }
    }
    
    public  static XMLConfiguration loadConfiguration(String fileName) throws Exception {
        List<FDataSource> srcs = new ArrayList();

        DefaultExpressionEngine engine = new DefaultExpressionEngine(
                DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS,
                NodeNameMatchers.EQUALS_IGNORE_CASE);

        
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<XMLConfiguration> builder = new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class).configure(
                params.xml()
                .setFileName(fileName)
                .setExpressionEngine(engine));

        XMLConfiguration config = builder.getConfiguration();
        
        return config;
        
    }    

    /*
 
            <PropertyMap keyFieldName="MapKey" valueFieldName="Name" contentClass="java.util.Map">
                <Property>
                    <MapKey>first_name2</MapKey>
                    <Name>first_name</Name>
                    <DataTypeClass>java.lang.String</DataTypeClass>
                </Property>
                <Property>
                    <MapKey>last_name2</MapKey>
                    <Name>last_name</Name>
                    <DataTypeClass>java.lang.String</DataTypeClass>
                </Property>         
                <Property>
                    <MapKey>year</MapKey>
                    <Name>year</Name>
                    <DataTypeClass>java.lang.Integer</DataTypeClass>
                </Property>         
            </PropertyMap>      

             <PropertyMap keyFieldName="MapKey" valueFieldName="Name" contentClass="java.lang.String">
                <Property>
                    <MapKey>first_name2</MapKey>
                    <Name>first_name</Name>
                    <DataTypeClass>java.lang.String</DataTypeClass>
                </Property>
                <Property>
                    <MapKey>last_name2</MapKey>
                    <Name>last_name</Name>
                    <DataTypeClass>java.lang.String</DataTypeClass>
                </Property>         
                <Property>
                    <MapKey>year</MapKey>
                    <Name>year</Name>
                    <DataTypeClass>java.lang.Integer</DataTypeClass>
                </Property>         
            </PropertyMap>      
           
            
     */
    
    public static <T> Map<String, T> convertToPropertyMapMap(HierarchicalConfiguration config) {
        return convertToPropertyMapMap(config, null);
    }
    
    public static <T> Map<String, T> convertToPropertyMapMap(HierarchicalConfiguration config, Class<T> clazz ) {
        
        List<HierarchicalConfiguration<ImmutableNode>> fieldsConfigurations = config.configurationsAt("PropertyMap.Property");

        String keyFieldName = config.getString("PropertyMap[@keyFieldName]");
        String valueFieldName = config.getString("PropertyMap[@valueFieldName]");
        
        Class contentClass = clazz;
        try {
            contentClass = Class.forName(config.getString("PropertyMap[@contentClass]"));;
        }
        catch (Exception e) {
            m_logger.error(e.getMessage(),e);
        }
        
        Map<String, T> fieldMetaMap = new HashMap();
        if ( contentClass == String.class ) {

            for (HierarchicalConfiguration sub : fieldsConfigurations) {
                
                String map = sub.getString(keyFieldName);                
                String name = sub.getString(valueFieldName);

                fieldMetaMap.put(map, (T)name);
            }
        } else if ( contentClass == BigDecimal.class) {

            for (HierarchicalConfiguration sub : fieldsConfigurations) {
                
                String map = sub.getString(keyFieldName);                
                BigDecimal bigDecimal = null;
                try {
                    bigDecimal = new BigDecimal(sub.getString(valueFieldName));
                    fieldMetaMap.put(map, (T)bigDecimal);
                }
                catch (Exception e) {
                    m_logger.error(e.getMessage(),e);
                }

            }
            
        } else if ( contentClass == Integer.class) {

            for (HierarchicalConfiguration sub : fieldsConfigurations) {
                
                String map = sub.getString(keyFieldName);                
                Integer bigDecimal = null;
                try {
                    bigDecimal = Integer.valueOf(sub.getString(valueFieldName));
                    fieldMetaMap.put(map, (T)bigDecimal);
                }
                catch (Exception e) {
                    m_logger.error(e.getMessage(),e);
                }

            }
            
        } else if ( clazz.isAssignableFrom(Map.class)) {

            
            for (HierarchicalConfiguration sub : fieldsConfigurations) {
                
                Map<String, String> property = new HashMap();
                String map = sub.getString(keyFieldName);                
                for (Iterator<String> keyIt = sub.getKeys();keyIt.hasNext();) {
                    String key = keyIt.next();
                    String value = sub.getString(key);
                    property.put(key, value);
                }
            
                fieldMetaMap.put(map, (T)property);
                
            }  
            return fieldMetaMap;

        }
        return fieldMetaMap;
        
    }
    
    
    public static Object createObject(HierarchicalConfiguration config ) throws Exception {
        
        Object obj = Class.forName(config.getString("Class")).newInstance();;
        
        HierarchicalConfiguration sub = config.configurationAt("Property");
        
        for (Iterator<String> keyIt = sub.getKeys();keyIt.hasNext();) {
            String key = keyIt.next();
            String value = sub.getString(key);
            
            BeanUtils.setProperty(obj, key, value);
        }
        
        return obj;
        
    }
    
}

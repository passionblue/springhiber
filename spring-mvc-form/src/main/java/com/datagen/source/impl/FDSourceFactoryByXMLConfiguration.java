package com.datagen.source.impl;

import java.util.Iterator;

import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;

import com.datagen.FDataSource;
import com.datagen.source.FDataSourceAdapter;
import com.datagen.util.ConfigUtils;

/*
 * Contructs Data Source with Apache Common Confiugration API
 */

public class FDSourceFactoryByXMLConfiguration {

    private static Logger m_logger = LoggerFactory.getLogger(FDSourceFactoryByXMLConfiguration.class);
    
    
    public FDataSource createInstance(HierarchicalConfiguration conf) throws Exception {

        FDataSource ds = (FDataSource) createInstanceFromConfig(conf);

        m_logger.debug("DataSource created " + ds.getClass().getName());
        
        HierarchicalConfiguration adapterConfig = (HierarchicalConfiguration) conf.subset("DataAdapter");

        if ( adapterConfig != null) { //TODO somehow this still returns non null....SHIT
            FDataSourceAdapter adapter = (FDataSourceAdapter) createInstanceFromConfig(adapterConfig);
            if ( adapter != null ) {
                m_logger.debug("DataAdapter created [" + adapter.getClass().getName() + "]");
                if (ds instanceof AbstractDataSource) {
                    ((AbstractDataSource) ds).setDataAdapter(adapter);
                }
            }
        }
        return ds;

    }

    private Object createInstanceFromConfig( HierarchicalConfiguration conf ) throws Exception {
     
        String className = conf.getString("Class");
        
        if ( className == null) return null;
        
        Class clazz = Class.forName(className);
        
        Object ds = (Object) clazz.newInstance();
        Iterator<String> keys = conf.getKeys();
        BeanWrapperImpl bean = new BeanWrapperImpl(ds);
        
        for (Iterator iterator = keys; iterator.hasNext();) {
            String key = (String) iterator.next();
            if ( isPropertyKey(key)) {
              String value = conf.getString(key);
              m_logger.debug("Setting value " + key + "->" + getPropertyNameFromKey(key) + "/" + value);
              try {
                bean.setPropertyValue(getPropertyNameFromKey(key), value);
                }
                catch (Exception e) {
                    m_logger.error(e.getMessage(),e);
                }
            }
        }
        return ds;
        
    }
    

    private boolean isPropertyKey(String key) {
        if ( key != null && key.startsWith("Property"))
            return true;
        else
            return false;
    }

    private String getPropertyNameFromKey(String key) {
        
        if ( isPropertyKey(key)) {
            return key.substring("Property.".length());
        }
        
        return null;
    }
    
}

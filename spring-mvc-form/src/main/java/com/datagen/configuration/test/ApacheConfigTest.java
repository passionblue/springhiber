package com.datagen.configuration.test;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.DefaultExpressionEngine;
import org.apache.commons.configuration2.tree.DefaultExpressionEngineSymbols;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.configuration2.tree.NodeNameMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.util.LoggingUtil;

public class ApacheConfigTest {

    private static Logger m_logger = LoggerFactory.getLogger(ApacheConfigTest.class);

    public static void main(String[] args) {

        DefaultExpressionEngine engine = new DefaultExpressionEngine(
                DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS,
                NodeNameMatchers.EQUALS_IGNORE_CASE);

        
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<XMLConfiguration> builder = new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class).configure(
                params.xml()
                .setFileName("configuration.xml")
                .setExpressionEngine(engine));

        try {
            XMLConfiguration config = builder.getConfiguration();

//            List<Object> fields = config.getList("Fields");
            Object fields = config.getProperty("Fields");

            System.out.println(fields);

            List<HierarchicalConfiguration<ImmutableNode>> fs = config.configurationsAt("Fields.Field");
            
            for (HierarchicalConfiguration sub : fs) {
                
                Iterator<String> keys = sub.getKeys();

                for (Iterator iterator = keys; iterator.hasNext();) {
                    String key = (String) iterator.next();

                    System.out.println(key);
                    
                    
//                    String fieldName = sub.getString("name");
//                    String fieldType = sub.getString("type");
//                    String display = sub.getString("display");
                    
                }
                
                
                
//                LoggingUtil.log(fieldName, fieldType, display);
            
            }

        }
        catch (ConfigurationException cex) {
            // loading of the configuration file failed
        }

    }

    public static void main2(String[] args) {
        Configurations configs = new Configurations();

        try {
            XMLConfiguration config = configs.xml("configuration.xml");

            List<Object> fields = config.getList("DataGen.Fields");

            System.out.println(fields);
        }
        catch (ConfigurationException cex) {

            m_logger.error("", cex);
        }

    }
}

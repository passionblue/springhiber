package com.datagen.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.tree.DefaultExpressionEngine;
import org.apache.commons.configuration2.tree.DefaultExpressionEngineSymbols;
import org.apache.commons.configuration2.tree.NodeNameMatchers;

import com.datagen.FDataSource;

public class ConfigUtils {

    
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
    
}

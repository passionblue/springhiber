package com.datagen.source.assembler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.DataGenContext;
import com.datagen.FDataSource;
import com.datagen.FDataSourceAssembler;
import com.datagen.meta.FieldMetaData;
import com.datagen.meta.FieldMetaDataManager;
import com.datagen.source.impl.FDSourceFactoryByXMLConfiguration;
import com.datagen.util.ConfigUtils;

public class FDSourceAggregatorByXMLConfigImpl implements FDataSourceAssembler {

    private static Logger m_logger = LoggerFactory.getLogger(FDSourceAggregatorByXMLConfigImpl.class);
    
    private List<FDataSource> sources; 
    private List<FDataSource> preLoadSources; 
    
    private void init() throws Exception {

    }
    
    
    @Override
    public List<FDataSource> getPreLoadSources(DataGenContext runContext)  throws Exception  {
        XMLConfiguration config = runContext.getXmlConfig();
        
        String importGenerate = config.getString("ImportGenerate");
        XMLConfiguration preLoadConfig = ConfigUtils.loadConfiguration(importGenerate);
        if ( StringUtils.isNotBlank(importGenerate)) {
            preLoadSources = initSourcesFromConfig(runContext, preLoadConfig);
        }
        return preLoadSources;
    }


    @Override
    public List<FDataSource> getSources(DataGenContext runContext)  throws Exception  {
        sources = initSourcesFromConfig(runContext, runContext.getXmlConfig());
        return sources;
    }

    public void setSources(List<FDataSource> sources) {
        this.sources = sources;
    }

    
    public List<FDataSource> initSourcesFromConfig(DataGenContext runContext, XMLConfiguration config) throws Exception {
        List<FDataSource> srcs = new ArrayList();
        
        List<HierarchicalConfiguration<ImmutableNode>> fieldsConfigurations = config.configurationsAt("Fields.Field");
        FDSourceFactoryByXMLConfiguration factory = new FDSourceFactoryByXMLConfiguration();
        
        for (HierarchicalConfiguration sub : fieldsConfigurations) {

            String fieldName = sub.getString("name");
            String display = sub.getString("display");                
            String fieldType = sub.getString("type");

            FieldMetaData meta = new FieldMetaData(fieldName, display, fieldType);
            FieldMetaDataManager.getInstance().addMetaData(fieldName, meta);
            
            HierarchicalConfiguration dataSourceConfigs = (HierarchicalConfiguration) sub.configurationAt("DataSource");
            
            if ( dataSourceConfigs != null ) {
                FDataSource dataSource = factory.createInstance(dataSourceConfigs);
                dataSource.setFieldName(fieldName);
                dataSource.setRunId(runContext.getId());
                srcs.add(dataSource);
            } else {
                m_logger.error("", new Exception("DataSource configs not found for " + fieldName));
            }
        }

        return srcs;
    }
    
//    private  XMLConfiguration loadConfiguration(String fileName) throws Exception {
//        List<FDataSource> srcs = new ArrayList();
//
//        DefaultExpressionEngine engine = new DefaultExpressionEngine(
//                DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS,
//                NodeNameMatchers.EQUALS_IGNORE_CASE);
//
//        
//        Parameters params = new Parameters();
//        FileBasedConfigurationBuilder<XMLConfiguration> builder = new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class).configure(
//                params.xml()
//                .setFileName(fileName)
//                .setExpressionEngine(engine));
//
//        XMLConfiguration config = builder.getConfiguration();
//        
//        return config;
//        
//    }
    
}

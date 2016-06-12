package com.datagen.source.assembler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.DataGenContext;
import com.datagen.FDataSource;
import com.datagen.FDataSourceAssembler;
import com.datagen.meta.FieldMetaData;
import com.datagen.meta.FieldMetaDataManager;
import com.datagen.output.OutputChannel;
import com.datagen.source.impl.FDSourceFactoryByXMLConfiguration;
import com.datagen.source.util.FDataSourceUtil;

public class FDSourceAggregatorByXMLConfigImpl implements FDataSourceAssembler {

    private static Logger m_logger = LoggerFactory.getLogger(FDSourceAggregatorByXMLConfigImpl.class);
    
    private List<FDataSource> sources; 
    private List<FDataSource> preLoadSources; 
    private boolean           checkDuplicity;
    
    
    public FDSourceAggregatorByXMLConfigImpl() {
    }


    public FDSourceAggregatorByXMLConfigImpl(boolean checkDuplicity) {
        this.checkDuplicity = checkDuplicity;
    }


    private void init() throws Exception {

    }
    
    
    @Override
    public List<DataGenContext> getPreLoadConfigs(DataGenContext runContext) throws Exception {
        XMLConfiguration config = runContext.getXmlConfig();
        String [] files= config.getStringArray("ImportGenerate");
        
        List<DataGenContext> contexts = new ArrayList();
        
        for (int i = 0; i < files.length; i++) {
            DataGenContext preLoadConfig = runContext.getPreLoadToken(files[i]);
            contexts.add(preLoadConfig);
        }
        
        return contexts;
    }

    @Override
    public List<DataGenContext> getImportConfigs(DataGenContext runContext) throws Exception {
        XMLConfiguration config = runContext.getXmlConfig();
        String [] files= config.getStringArray("Import");
        
        List<DataGenContext> contexts = new ArrayList();
        
        for (int i = 0; i < files.length; i++) {
            DataGenContext preLoadConfig = runContext.getPreLoadToken(files[i]);
            contexts.add(preLoadConfig);
        }
        
        return contexts;
    }


    @Deprecated
    @Override
    public List<FDataSource> getPreLoadSources(DataGenContext runContext)  throws Exception  {
        XMLConfiguration config = runContext.getXmlConfig();
        preLoadSources = initSourcesFromConfig(runContext, config);
        return preLoadSources;
    }


    @Override
    public List<FDataSource> getSources(DataGenContext runContext)  throws Exception  {
        
        List<DataGenContext> noLoadImportConfigs = getImportConfigs(runContext);
        
        List<FDataSource> fsources = new ArrayList();
        for (DataGenContext pContext : noLoadImportConfigs) {
            List<FDataSource> fDataPreLoadSources = getSources(pContext);
            fsources.addAll(fDataPreLoadSources);
        }
        
        List<FDataSource> sourcesFromMainConfig = initSourcesFromConfig(runContext, runContext.getXmlConfig());
        fsources.addAll(sourcesFromMainConfig);
        FDataSourceUtil.resolveSourceRefs(fsources);
        if ( checkDuplicity) FDataSourceUtil.checkDuplicityOnSourceRefs(fsources);
        return fsources;
    }

    public void setSources(List<FDataSource> sources) {
        this.sources = sources;
    }

    
    public boolean isCheckDuplicity() {
        return checkDuplicity;
    }


    public void setCheckDuplicity(boolean checkDuplicity) {
        this.checkDuplicity = checkDuplicity;
    }


    public List<FDataSource> initSourcesFromConfig(DataGenContext runContext, XMLConfiguration config) throws Exception {
        List<FDataSource> srcs = new ArrayList();
        
        List<HierarchicalConfiguration<ImmutableNode>> fieldsConfigurations = config.configurationsAt("Fields.Field");
        FDSourceFactoryByXMLConfiguration factory = new FDSourceFactoryByXMLConfiguration();
        
        for (HierarchicalConfiguration sub : fieldsConfigurations) {

            // These are the sub fields right under <Field>
            String fieldName    = sub.getString("name");
            String display      = sub.getString("display");                
            String fieldType    = sub.getString("type");
            String sourceType    = sub.getString("SourceType");
            String dataSet      = sub.getString("DataSet");
            boolean exclude     = sub.getBoolean("ExcludeInOutput", false);
            boolean disable     = sub.getBoolean("Disable", false);

            if ( disable ) {
                m_logger.warn("Field {} of DataSet [{}] is set to DISABLED ", fieldName, dataSet);
                continue;
            }
            
            // Check duplicity within the universe. 
            // For object populator, dont need to check, becausem multiple object could have separate universe in the single runtime.
            if( checkDuplicity ) {
                FieldMetaData sourceFieldMetaData = new FieldMetaData(fieldName, display, fieldType);
                FieldMetaDataManager.getInstance().addMetaData(fieldName, sourceFieldMetaData);
            }
            
            HierarchicalConfiguration dataSourceConfigs = (HierarchicalConfiguration) sub.configurationAt("DataSource");
            
            if ( dataSourceConfigs != null ) {
                FDataSource dataSource = factory.createInstance(dataSourceConfigs);
                
//                dataSource.setFieldName(fieldName);
                dataSource.setRunId(runContext.getId(dataSet));
//                dataSource.setExcludeInOutput(exclude);

                BeanUtils.setProperty(dataSource, "fieldName", fieldName);
                BeanUtils.setProperty(dataSource, "excludeInOutput", exclude);
                BeanUtils.setProperty(dataSource, "fieldName", fieldName);
                BeanUtils.setProperty(dataSource, "type", sourceType);
                
                srcs.add(dataSource);
                m_logger.info (">> Source Created for [{}] loaded. display=[{}], SourceClass=[{}],excludeInOut=[{}]", fieldName, display, dataSource.getClass().getSimpleName(), exclude);

            } else {
                m_logger.error("", new Exception("DataSource configs not found for " + fieldName));
            }
        }

        return srcs;
    }

    
    
}

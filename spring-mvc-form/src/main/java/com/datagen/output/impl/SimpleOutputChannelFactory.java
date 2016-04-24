package com.datagen.output.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.DataGenContext;
import com.datagen.cache.MemoryCache;
import com.datagen.output.OutputChannel;
import com.datagen.output.OutputChannelFactory;
import com.datagen.output.formatter.FormatRowHeaderToDelimiteredString;
import com.datagen.output.formatter.FormatRowToDelimiteredString;
import com.datagen.output.formatter.FormatRowToRow;
import com.datagen.output.impl.DatabaseOutputChannel.FieldMeta;

public class SimpleOutputChannelFactory implements OutputChannelFactory{

    private static Logger m_logger = LoggerFactory.getLogger(SimpleOutputChannelFactory.class);
    
    public void init() {
        
    }
    
    @Override
    public List<OutputChannel> getChannels(DataGenContext runContext) throws Exception {

        List<OutputChannel> channels = new ArrayList();
        
        List<HierarchicalConfiguration<ImmutableNode>> fieldsConfigurations = runContext.getXmlConfig().configurationsAt("Outputs.Output");
        
        for (HierarchicalConfiguration sub : fieldsConfigurations) {
        
            OutputChannel channel = createChannel(runContext, sub, null);
            channels.add(channel);
        }
        
        return channels;
    }

    @Override
    public List<OutputChannel> getPreLoadChannels(DataGenContext runContext)  throws Exception {
        
        List<OutputChannel> channels = new ArrayList();
        
        XMLConfiguration preLoadConfig = runContext.getXmlConfig();
        try {
            
            String dataSetName = preLoadConfig.getString("DataSetName");
            m_logger.info("Loading Channels for [{}] from [{}]", dataSetName, runContext.getFileName());
            
            List<HierarchicalConfiguration<ImmutableNode>> fieldsConfigurations = preLoadConfig.configurationsAt("Outputs.Output");
            
            for (HierarchicalConfiguration sub : fieldsConfigurations) {
            
                OutputChannel channel = createChannel(runContext, sub, dataSetName);
                channels.add(channel);
            }

        }
        catch (Exception e) {
            m_logger.error("",e);
        }
        
        
        return channels;
    }
    

    private OutputChannel createChannel(DataGenContext runContext, HierarchicalConfiguration config, String dataSetName) throws Exception {
        
        
        String outputChannelType = config.getString("Type", null);
        int maxCount = config.getInt("MaxCount", 0);
        boolean failOnError         = config.getBoolean("FailOnEntryError", false);
        
        if ("CSV".equalsIgnoreCase(outputChannelType)) {
            
            String fileName         = config.getString("FileName");
            String delimiter        = config.getString("Delimiter");
            boolean includeHeader   = config.getBoolean("IncludeHeader");
            
            CSVFileOutputChannel channel = new CSVFileOutputChannel(runContext.getId(dataSetName), maxCount, fileName, includeHeader);
            FormatRowToDelimiteredString f = new FormatRowToDelimiteredString(delimiter);
            channel.setRowFormatter(f);
            FormatRowHeaderToDelimiteredString header = new FormatRowHeaderToDelimiteredString(delimiter);
            channel.setHeaderFormatter(header);
            channel.setFailOnEntryError(failOnError);
            return channel;
            
        } else if ("DATABASE".equalsIgnoreCase(outputChannelType)) {
            
            
            String tableName         = config.getString("table");
            String configFileName    = config.getString("ConfigFile");
            
            List<HierarchicalConfiguration<ImmutableNode>> fieldsConfigurations = config.configurationsAt("DBFieldMap.DBField");
            
            Map<String, FieldMeta> fieldMetaMap = new HashMap();
            
            for (HierarchicalConfiguration sub : fieldsConfigurations) {

                String name = sub.getString("Name");
                String map = sub.getString("Map");                
                String dataTypeClass = sub.getString("DataTypeClass");

                DatabaseOutputChannel.FieldMeta fieldMap = new DatabaseOutputChannel.FieldMeta(name, map, Class.forName(dataTypeClass));
            
                fieldMetaMap.put(map, fieldMap);
                
            }            
            
            DatabaseOutputChannel channel = new DatabaseOutputChannel(runContext.getId(dataSetName));
            
            channel.setTableName(tableName);
            channel.setFieldMetaMap(fieldMetaMap);
            channel.setFailOnEntryError(failOnError);
            channel.setConfigFileName(configFileName);
            channel.setRowFormatter(new FormatRowToRow(null));
            return channel;
            
        } else if ("MEMORY".equalsIgnoreCase(outputChannelType)) {
            List<OutputChannel> channels = new ArrayList();
            MemoryCache cache = MemoryCache.getCache(runContext.getId(dataSetName));
            MemoryOutputChannel channel = new MemoryOutputChannel(runContext.getId(dataSetName), cache);
            channel.setRowFormatter(new FormatRowToRow(null));
            channel.setMaxCount(maxCount);
            channel.setFailOnEntryError(failOnError);
            return channel;

        } else{
            ConsoleOutputChannel channel = new ConsoleOutputChannel(runContext.getId(dataSetName));
            FormatRowToDelimiteredString f = new FormatRowToDelimiteredString();
            channel.setRowFormatter(f);
            return channel;
        }
    }
    
}

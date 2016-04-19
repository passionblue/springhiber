package com.datagen.output.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.datagen.util.ConfigUtils;

public class SimpleOutputChannelFactory implements OutputChannelFactory{

    private static Logger m_logger = LoggerFactory.getLogger(SimpleOutputChannelFactory.class);
    
    public void init() {
        
    }
    
    @Override
    public List<OutputChannel> getChannels(DataGenContext runContext) {

        List<OutputChannel> channels = new ArrayList();
        
        List<HierarchicalConfiguration<ImmutableNode>> fieldsConfigurations = runContext.getXmlConfig().configurationsAt("Outputs.Output");
        
        for (HierarchicalConfiguration sub : fieldsConfigurations) {
        
            OutputChannel channel = createChannel(runContext, sub);
            channels.add(channel);
        }
        
        return channels;
    }

    @Override
    public List<OutputChannel> getPreLoadChannels(DataGenContext runContext) {
        
        List<OutputChannel> channels = new ArrayList();
        
        XMLConfiguration config = runContext.getXmlConfig();
        
        XMLConfiguration preLoadConfig;
        try {
            String importGenerate = config.getString("ImportGenerate");        
            preLoadConfig = ConfigUtils.loadConfiguration(importGenerate);
        
            List<HierarchicalConfiguration<ImmutableNode>> fieldsConfigurations = preLoadConfig.configurationsAt("Outputs.Output");
            
            for (HierarchicalConfiguration sub : fieldsConfigurations) {
            
                OutputChannel channel = createChannel(runContext, sub);
                channels.add(channel);
            }

        }
        catch (Exception e) {
            m_logger.error("",e);
        }
        
        
        return channels;
    }
    

    private OutputChannel createChannel(DataGenContext runContext, HierarchicalConfiguration config){
        String type = config.getString("Type", null);
        int maxCount = config.getInt("MaxCount", 0);
        
        if ("CSV".equalsIgnoreCase(type)) {
            
            String fileName = config.getString("FileName");
            String delimiter = config.getString("Delimiter");
            boolean includeHeader = config.getBoolean("IncludeHeader");
            
            CSVFileOutputChannel channel = new CSVFileOutputChannel(runContext.getId(), maxCount, fileName, includeHeader);
            FormatRowToDelimiteredString f = new FormatRowToDelimiteredString(delimiter);
            channel.setRowFormatter(f);
            FormatRowHeaderToDelimiteredString header = new FormatRowHeaderToDelimiteredString(delimiter);
            channel.setHeaderFormatter(header);
            return channel;
            
        } else if ("DATABASE".equalsIgnoreCase(type)) {
            
            return null;
            
        } else if ("MEMORY".equalsIgnoreCase(type)) {
            List<OutputChannel> channels = new ArrayList();
            MemoryCache cache = MemoryCache.getCache(runContext.getId());
            MemoryOutputChannel channel = new MemoryOutputChannel(runContext.getId(), cache);
            channel.setRowFormatter(new FormatRowToRow(null));
            channel.setMaxCount(maxCount);
            return channel;
        } else{
            ConsoleOutputChannel channel = new ConsoleOutputChannel(runContext.getId());
            FormatRowToDelimiteredString f = new FormatRowToDelimiteredString();
            channel.setRowFormatter(f);
            return channel;
        }
    }
    
}

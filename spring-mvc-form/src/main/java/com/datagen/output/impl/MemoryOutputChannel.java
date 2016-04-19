package com.datagen.output.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FDataRow;
import com.datagen.cache.CacheInterface;

public class MemoryOutputChannel extends  AbstractOutputChannel<FDataRow> {

    private static Logger m_logger = LoggerFactory.getLogger(MemoryOutputChannel.class);
    
    private CacheInterface cache;
    
    public MemoryOutputChannel(String id, CacheInterface cache) {
        super(id);
        this.cache = cache;
        m_logger.info("MemoryOutputChannel created [{}]", id );
    }

    @Override
    public void writeToChannel(FDataRow dataRow) throws Exception {
        cache.store(dataRow);
    }

    @Override
    public void open() throws Exception {
        
    }

    @Override
    public void close() {
    }

    @Override
    void writeHeaderToChannel(FDataRow t) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    boolean reachedMax() {
       
        if ( cache.dataSize() >= this.maxCount  &&  this.maxCount > 0) 
            return true;
        return false;
    }

   
}

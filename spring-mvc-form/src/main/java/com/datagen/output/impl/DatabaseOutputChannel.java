package com.datagen.output.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseOutputChannel extends  AbstractOutputChannel<String> {

    public DatabaseOutputChannel(String id) {
        super(id);
    }

    private static Logger m_logger = LoggerFactory.getLogger(DatabaseOutputChannel.class);



    @Override
    void writeToChannel(String t) throws Exception {
        
    }

    @Override
    void writeHeaderToChannel(String t) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    boolean reachedMax() {
        // TODO Auto-generated method stub
        return false;
    }
    

}

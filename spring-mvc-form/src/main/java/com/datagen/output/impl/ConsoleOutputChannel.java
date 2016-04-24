package com.datagen.output.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FDataRow;

public class ConsoleOutputChannel extends  AbstractOutputChannel<String> {

    private static Logger m_logger = LoggerFactory.getLogger(ConsoleOutputChannel.class);
    
    public ConsoleOutputChannel(String id) {
        super(id);
        m_logger.info("ConsoleOutputChannel created [{}]", id );
    }

    @Override
    public void writeToChannel(String dataRow) throws Exception {
//        System.out.println("##[" + id + "] " + dataRow);
    }

    @Override
    public void open() throws Exception {
        
    }


    @Override
    void writeHeaderToChannel(String t) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void close() {
    }

    @Override
    boolean reachedMax() {
        return false;
    }
}

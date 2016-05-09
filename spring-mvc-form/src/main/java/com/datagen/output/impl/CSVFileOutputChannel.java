package com.datagen.output.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSVFileOutputChannel extends  AbstractOutputChannel<String> {

    private static Logger m_logger = LoggerFactory.getLogger(CSVFileOutputChannel.class);
    
    private String fileName;
    private PrintWriter writer;
    private int    dataCount;
    
    public CSVFileOutputChannel(String id, int maxCount, String fileName, boolean includeHeader) {
        super(id, maxCount);
        this.fileName = fileName;
        this.includeHeader = includeHeader;
        m_logger.info("CSVFileOutputChannel instance created [{}] file={}", id, fileName );
    }

    @Override

    public void writeToChannel(String dataRow) throws Exception {
        
        if (writer != null ) {
            writer.println(dataRow);
            writer.flush();
            dataCount++;
            m_logger.debug("Data entered [{}]", dataRow);
        } else {
            m_logger.warn("Writer not initiated or failed during init [{}] file={}", id, fileName);
        }
        
    }

    @Override
    void writeHeaderToChannel(String t) throws Exception {
        
        if ( !headerAdded ){
            writeToChannel(t);
            headerAdded = true;
        }
    }

    @Override
    public void open() throws Exception {
        File file = new File(fileName);

        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
            String tartget = fileName + "." + System.currentTimeMillis();
            Files.move(Paths.get(fileName), Paths.get(tartget) , StandardCopyOption.REPLACE_EXISTING);
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
        writer = new PrintWriter(bw);
        dataCount = 0;
        m_logger.info("CSVFileOutputChannel opened [{}] file={}", this.getId(), fileName );
        
    }
    @Override
    boolean reachedMax() {
       
        if ( dataCount >= this.maxCount &&  this.maxCount > 0) 
            return true;
        return false;
    }
    @Override
    public void close() {
        
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
        catch (Exception e) {
            m_logger.error(e.getMessage(),e);
        }
    }
}

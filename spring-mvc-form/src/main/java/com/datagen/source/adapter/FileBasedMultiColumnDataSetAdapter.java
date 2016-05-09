package com.datagen.source.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.DataGenContext;
/*
 * Read CSV type of file and return a array of string each time. 
 * 
 */
public class FileBasedMultiColumnDataSetAdapter extends AbstractDataSetAdapter<String[]>{

    private static Logger m_logger = LoggerFactory.getLogger(FileBasedMultiColumnDataSetAdapter.class);
    
    private String fileName;
    private String dataDelimiter = ",";
    private String loadColumnIndex;
    private boolean skipHeader = false;
    private List<String[]> dataSet;
    private int[] columns; // parsed from loadColumnIndex
    
    public FileBasedMultiColumnDataSetAdapter() throws Exception {
    }
    
    public FileBasedMultiColumnDataSetAdapter(String filename) throws Exception {
        this.fileName = filename;
        init();
    }
    
    @Override
    public void reload(DataGenContext context) throws Exception {
        init();
    }
    
    public void init() throws Exception {
        
        Scanner sc = new Scanner(this.getClass().getClassLoader().getResourceAsStream(fileName));

        dataSet = new ArrayList();
        boolean firstLineSkipped = !skipHeader;
        while(sc.hasNextLine()){
            
            String line = sc.nextLine();
            
            if (line == null || StringUtils.isBlank(line.trim()))
                continue;
            
            String[] splitted = line.split(dataDelimiter, -1);
            
            if ( firstLineSkipped ) {
                dataSet.add(splitted);
            } else {
                firstLineSkipped = true;
            }
        }
        
        m_logger.info("Data of ArrayString loaded from file " + fileName + ", count: " + dataSet.size());
    }
    
    @Override
    public int getDataSize() {
        return dataSet.size();
    }

    @Override
    public String[] getByPosition(int pos) {
        return dataSet.get(pos);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDataDelimiter() {
        return dataDelimiter;
    }

    public void setDataDelimiter(String dataDelimiter) {
        this.dataDelimiter = dataDelimiter;
    }

    public List<String[]> getDataSet() {
        return dataSet;
    }

    public void setDataSet(List<String[]> dataSet) {
        this.dataSet = dataSet;
    }

    public boolean isSkipHeader() {
        return skipHeader;
    }

    public void setSkipHeader(boolean skipHeader) {
        this.skipHeader = skipHeader;
    }

    
    
    
}

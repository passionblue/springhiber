package com.datagen.source.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.DataGenContext;

/*
 * File should contain only one column
 */

public class FileBasedDataSetAdapter extends AbstractDataSetAdapter<String>{

    private static Logger m_logger = LoggerFactory.getLogger(FileBasedDataSetAdapter.class);
    
    private String fileName;
    private List<String> dataSet;
    
    public FileBasedDataSetAdapter() throws Exception {
    }
    
    public FileBasedDataSetAdapter(String filename) throws Exception {
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
        
        while(sc.hasNextLine()){
            dataSet.add(sc.nextLine());
        }
        
        sc.close();
        
        m_logger.info("Data loaded from file " + fileName + ", count: " + dataSet.size());
    }
    
    @Override
    public int getDataSize() {
        return dataSet.size();
    }

    @Override
    public String getByPosition(int pos) {
        return dataSet.get(pos);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
}

package com.datagen.source.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.DataGenContext;

/*
 * Data loaded from input string with comma 
 */

public class FixedDataSetAdapter extends AbstractDataSetAdapter<String>{

    private static Logger m_logger = LoggerFactory.getLogger(FixedDataSetAdapter.class);
    
    private String dataString;
    private List<String> dataSet;
    
    public FixedDataSetAdapter() throws Exception {
    }
    
    public FixedDataSetAdapter(String dataString) throws Exception {
        this.dataString = dataString;
        init();
    }
    
    @Override
    public void reload(DataGenContext context) throws Exception {
        init();
    }

    public void init() throws Exception {
        
        dataSet = new ArrayList();
        dataSet.addAll(Arrays.asList(dataString.split(",", -1)));
        
        m_logger.info("Data loaded from string " + dataString + ", count: " + dataSet.size());
    }
    
    @Override
    public int getDataSize() {
        return dataSet.size();
    }

    @Override
    public String getByPosition(int pos) {
        return dataSet.get(pos);
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public List<String> getDataSet() {
        return dataSet;
    }

    public void setDataSet(List<String> dataSet) {
        this.dataSet = dataSet;
    }


    
}

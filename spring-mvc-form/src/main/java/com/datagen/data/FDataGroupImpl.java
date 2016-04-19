package com.datagen.data;

import java.util.ArrayList;
import java.util.List;

import com.datagen.FData;
import com.datagen.FDataGroup;

/*
 * Generally used by Mixer
 */

public class FDataGroupImpl extends AbstractFData<List<FData>> implements FDataGroup {
    
    private List<FData> dataList;
    
    public FDataGroupImpl(String fieldName, String [] data) {
        this.fieldName = fieldName;
        dataList = new ArrayList();
        for (int i = 0; i < data.length; i++) {
            dataList.add(new FDataString(fieldName, data[i]));
        }
    }
    
    public FDataGroupImpl(String fieldName, List<FData> data) {
        this.fieldName = fieldName;
        dataList = new ArrayList(data);
    }    
    
    @Override
    public List<FData> getRawFormat() {
        return dataList;
    }
    
    @Override
    public List<FData> getUnderlyingData() {
        return dataList;
    }

    @Override
    public String toString() {
        return "FDataGroupImpl [dataList=" + dataList + "]";
    }
}

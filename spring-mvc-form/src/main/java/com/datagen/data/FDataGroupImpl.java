package com.datagen.data;

import java.util.ArrayList;
import java.util.List;

import com.datagen.FData;
import com.datagen.FDataGroup;

/*
 * Group implementation
 * 
 * @see FDataStickyGroupImpl, FDataGroup
 */

public class FDataGroupImpl extends AbstractFData<List<FData>> implements FDataGroup {
    
    private List<FData> dataList;
    
    public FDataGroupImpl(String fieldName, boolean excludeInOutput, String [] data) {
        super(fieldName, excludeInOutput);
        dataList = new ArrayList();
        for (int i = 0; i < data.length; i++) {
            dataList.add(new FDataString(fieldName, excludeInOutput, data[i]));
        }
    }
    
    public FDataGroupImpl(String fieldName, boolean excludeInOutput, List<FData> data) {
        super(fieldName, excludeInOutput);
        
        for (FData fData : data) {
            if ( fData instanceof AbstractFData){
                ((AbstractFData)fData).setExcludeInOutput(excludeInOutput);
            }
        }
        
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

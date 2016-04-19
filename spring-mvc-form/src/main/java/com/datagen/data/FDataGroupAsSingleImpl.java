package com.datagen.data;

import java.util.ArrayList;
import java.util.List;

import com.datagen.FData;
import com.datagen.FDataGroup;

/*
 * This data source is based on multiple underlying data source aggregaated at the source level. 
 * But should not be broken during retrieving data. Only formatter processing needs to handle underlying data
 * 
 */


public class FDataGroupAsSingleImpl  extends AbstractFData<List<FData>>  implements FDataGroup {
    
    private List<FData> dataList;

    public FDataGroupAsSingleImpl(String fieldName, String []data) {
        this.fieldName = fieldName;
        dataList = new ArrayList();
        
        for (int i = 0; i < data.length; i++) {
            dataList.add(new FDataString(fieldName, data[i]));
        }
    }
    
    @Override
    public List<FData> getRawFormat() {
        List<FData> ret = new ArrayList();
        ret.add(this);
        return ret;
    }
    
    public List<FData> getUnderlyingData() {
        return dataList;
    }

    @Override
    public String toString() {
        return "FDataGroupAsSingleImpl [dataList=" + dataList + "]";
    }
    
    
}

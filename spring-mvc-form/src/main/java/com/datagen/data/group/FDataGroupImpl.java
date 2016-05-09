package com.datagen.data.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.datagen.FData;
import com.datagen.FDataGroup;
import com.datagen.data.AbstractFData;
import com.datagen.data.FDataString;

/*
 * Group implementation
 * 
 * @see FDataStickyGroupImpl, FDataGroup
 */

public class FDataGroupImpl extends AbstractFData<List<FData>> implements FDataGroup {
    
    private List<FData> dataList;
    private Map<String, FData> dataListMap = new HashMap();
    
    private boolean     sticky;
    
    public FDataGroupImpl(String fieldName, boolean excludeInOutput, boolean sticky, String [] data) {
        super(fieldName, excludeInOutput);
        dataList = new ArrayList();
        for (int i = 0; i < data.length; i++) {
            dataList.add(new FDataString(fieldName, excludeInOutput, data[i]));
        }
        this.sticky = sticky;
        mapfify(dataList);
    }
    
    public FDataGroupImpl(String fieldName, boolean excludeInOutput, boolean sticky,  List<FData> data) {
        super(fieldName, excludeInOutput);
        
        for (FData fData : data) {
            if ( fData instanceof AbstractFData){
                ((AbstractFData)fData).setExcludeInOutput(excludeInOutput);
            }
        }
        dataList = new ArrayList(data);
        this.sticky = sticky;
        mapfify(dataList);
    }    
    
    private void mapfify(List<FData> datas){
        
        for (Iterator iterator = datas.iterator(); iterator.hasNext();) {
            FData fd = (FData) iterator.next();
            if ( fd.getFieldName() == null) {
                System.out.println(fd);
            }
            dataListMap.put(fd.getFieldName(), fd);
        }
        
    }
    
    public boolean isSticky() {
        return sticky;
    }

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    @Override
    public List<FData> getRawFormat() {
        if ( sticky) {
            List<FData> ret = new ArrayList();
            ret.add(this);
            return ret;
        } else {
            return dataList;
        }
    }
    
    @Override
    public List<FData> getUnderlyingDatas() {
        return dataList;
    }

    
    
    @Override
    public FData getUnderlyingData(String fieldName) {
        if ( fieldName == null) return null;
        return dataListMap.get(fieldName);
    }
    
    @Override
    public String getStringFormat() {
        return null;
    }

    @Override
    public String toString() {
        return "FDataGroupImpl [dataList=" + dataList + ", sticky=" + sticky + "]";
    }
}

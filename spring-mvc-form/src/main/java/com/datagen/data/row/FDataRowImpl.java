package com.datagen.data.row;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.datagen.FData;
import com.datagen.FDataRow;
import com.datagen.data.AbstractFData;

public class FDataRowImpl extends CopyOnWriteArrayList<FData> implements FDataRow {

    private Map<String, FData> nameMap = new ConcurrentHashMap<>();
    
    @Override
    public int count() {
        return this.size();
    }

    @Override
    public List<FData> getData(boolean excludeExcluded) {
        
        if (excludeExcluded) {
            List<FData> retArray = new ArrayList();
            for (FData fData : this) {
                if (!fData.excludeInOutput())
                    retArray.add(fData);
            }
            return retArray;
        }
        return new ArrayList(this);
    }

    @Override
    public boolean isOrdered() {
        return false;
    }

    @Override
    public FData getByName(String name) {
        return nameMap.get(name);
    }

    @Override
    public FData dataAt(int idx) {
        return this.get(idx);
    }

    @Override
    public void addData(FData fData) {
        nameMap.put(fData.getFieldName(), fData);

        if (fData instanceof AbstractFData ) {
            ((AbstractFData) fData).setDataRow(this);
        }
        this.add(fData);
    }

    @Override
    public void addData(List<FData> fDatas) {
        
        for (FData fData : fDatas) {
            if (fData instanceof AbstractFData ) {
                ((AbstractFData) fData).setDataRow(this);
            }
        }
        
        this.addAll(fDatas);
    }

    
}

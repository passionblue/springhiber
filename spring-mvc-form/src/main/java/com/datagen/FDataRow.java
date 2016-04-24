package com.datagen;

import java.util.List;

public interface FDataRow {
    int             count();
    
    List<FData>     getData(boolean excludeExcluded);
    boolean         isOrdered();
    
    FData           getByName(String name);
    FData           dataAt(int idx); // 0 based
    
    void            addData(FData fData);
    void            addData(List<FData> fDatas);
    
    
}

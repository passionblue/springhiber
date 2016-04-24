package com.datagen.data;

import java.util.ArrayList;
import java.util.List;

import com.datagen.FData;
import com.datagen.FDataGroup;

/*
 * 
 * This data source is based on multiple underlying data source aggregaated at the source level. 
 * But should not be broken during retrieving data. Only formatter processing needs to handle underlying data. 
 * 
 * So the size of getRaw() and getUnderlying() would be different, while size of those for general group imple would be same
 * 
 */


public class FDataStickyGroupImpl  extends FDataGroupImpl  implements FDataGroup {
    
    private List<FData> dataList;

    public FDataStickyGroupImpl(String fieldName, boolean excludeInOutput, String []data) {
        super(fieldName, excludeInOutput, data);
    }
    
    @Override
    public List<FData> getRawFormat() {
        List<FData> ret = new ArrayList();
        ret.add(this);
        return ret;
    }
    
    @Override
    public String toString() {
        return "FDataStickyGroupImpl [dataList=" + dataList + "]";
    }
    
    
}

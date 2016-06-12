package com.datagen.data;

import com.datagen.source.util.FDStringUtil;

public class FDataStringArray extends AbstractFData<String[]> {

    private String data[];
    
    public FDataStringArray(String fieldName, boolean excludeInOutput, String str[]) {
        super(fieldName, excludeInOutput);
        data = str;
    }    
    

    @Override
    public String[] getRawFormat() {
        return data;
    }

    @Override
    public String toString() {
        return "FDataString [data=" + data + "]";
    }

    @Override
    public String getStringFormat() {
        return FDStringUtil.toString(data);
    }
}

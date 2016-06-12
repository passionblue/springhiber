package com.datagen.data;

public class FDataBoolean extends AbstractFData<Boolean> {

    private String data;
    private Boolean raw;
    
    public FDataBoolean(String fieldName, boolean excludeInOutput, Boolean obj) {
        super(fieldName, excludeInOutput);
        if (obj != null)
            data = obj.toString();
        
        raw = obj;
    }    
    
    @Override
    public Boolean getRawFormat() {
        if ( raw == null ) 
            return Boolean.FALSE;
        return raw;
    }
    
    @Override
    public String getStringFormat() {
        return getRawFormat().toString();
    }

    @Override
    public String toString() {
        return "FDataBoolean [data=" + data + ", raw=" + raw + "]";
    }
}

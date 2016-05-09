package com.datagen.data;

public class FDataBoolean extends AbstractFData<Boolean> {

    private String data;
    private Boolean raw;
    
//    public FDataString(String fieldName, Object obj) {
//        super(fieldName, false);
//        if (obj != null)
//            data = obj.toString();
//    }

    public FDataBoolean(String fieldName, boolean excludeInOutput, Boolean obj) {
        super(fieldName, excludeInOutput);
        if (obj != null)
            data = obj.toString();
        
        raw = obj;
    }    
    
//    public FDataString(Object obj) {
//        this(null, obj);
//    }

    @Override
    public Boolean getRawFormat() {
        return raw;
    }

    
    
    @Override
    public String getStringFormat() {
        return data;
    }

    @Override
    public String toString() {
        return "FDataBoolean [data=" + data + ", raw=" + raw + "]";
    }
}

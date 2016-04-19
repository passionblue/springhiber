package com.datagen.data;

public class FDataString extends AbstractFData<String> {

    private String data;
    
    public FDataString(String fieldName, Object obj) {
        setFieldName(fieldName);
        if (obj != null)
            data = obj.toString();
    }

//    public FDataString(Object obj) {
//        this(null, obj);
//    }

    @Override
    public String getRawFormat() {
        return data;
    }

    @Override
    public String toString() {
        return "FDataString [data=" + data + "]";
    }

    
    
}

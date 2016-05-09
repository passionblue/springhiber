package com.datagen.data;

public class FDataObject extends AbstractFData<Object> {

    private Object data;
    
    public FDataObject(String fieldName, boolean excludeInOutput, Object obj) {
        super(fieldName, excludeInOutput);
        data = obj;
    }    
    
    @Override
    public Object getRawFormat() {
        return data;
    }

    @Override
    public String toString() {
        return "FDataObject [data=" + data + ", fieldName=" + fieldName + ", dataRow=" + dataRow + "]";
    }

    @Override
    public String getStringFormat() {
        return data == null? null: data.toString();
    }

    

}

package com.datagen.data;

import com.datagen.FData;

public class FDataNull implements FData<Object> {

    private String fieldName;
    
    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public Object getRawFormat() {
        return "";
    }

    @Override
    public String toString() {
        return "FDataNull []";
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    
    
}

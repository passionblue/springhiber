package com.datagen.data;

import com.datagen.FData;

abstract public class AbstractFData<T> implements FData<T> {

    protected String fieldName;
    
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    
}

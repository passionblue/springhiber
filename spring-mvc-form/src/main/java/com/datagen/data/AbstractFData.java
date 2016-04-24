package com.datagen.data;

import com.datagen.FData;
import com.datagen.FDataRow;

abstract public class AbstractFData<T> implements FData<T> {

    protected String    fieldName;
    protected FDataRow  dataRow;
    protected boolean   excludeInOutput; // indicator to exclude to output
    
    public AbstractFData(String fieldName, boolean excludeInOutput) {
        super();
        this.fieldName = fieldName;
        this.excludeInOutput = excludeInOutput;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public FDataRow getDataRow() {
        return dataRow;
    }

    public void setDataRow(FDataRow dataRow) {
        this.dataRow = dataRow;
    }

    public boolean excludeInOutput() {
        return excludeInOutput;
    }

    public void setExcludeInOutput(boolean excludeInOutput) {
        this.excludeInOutput = excludeInOutput;
    }
    
}

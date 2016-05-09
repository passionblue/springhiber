package com.datagen.source.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FData;
import com.datagen.data.FDataBoolean;

public class FDRandomBooleanGenerator extends AbstractDataSource {

    private static Logger m_logger = LoggerFactory.getLogger(FDRandomBooleanGenerator.class);
    
    private String rangeStart;
    private String rangeEnd;
    private String numberClass;//
    
    @Override
    public FData nextFData() {
        Boolean bool = new Boolean(Math.random() < 0.5);
        return new FDataBoolean(this.fieldName, this.excludeInOutput, bool);
    }
    
    @Override
    public FData nextFData(Object arg) {
        return nextFData();
    }

    public String getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(String rangeStart) {
        this.rangeStart = rangeStart;
    }

    public String getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(String rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public String getNumberClass() {
        return numberClass;
    }

    public void setNumberClass(String numberClass) {
        this.numberClass = numberClass;
    }


    
}

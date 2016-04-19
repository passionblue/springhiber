package com.datagen.source.impl;

import com.datagen.FData;
import com.datagen.data.FDataCustomComposite;

public class FDCustomCompositeSource extends AbstractDataSource {
    
    private String pattern;
    
    @Override
    public FData generateNext() {
        return new FDataCustomComposite(fieldName, pattern);
    }
    
    @Override
    public FData generateNext(Object arg) {
        return generateNext();
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

}

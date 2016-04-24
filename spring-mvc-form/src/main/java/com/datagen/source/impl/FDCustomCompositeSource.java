package com.datagen.source.impl;

import com.datagen.FData;
import com.datagen.data.FDataRegexField;

public class FDCustomCompositeSource extends AbstractDataSource {
    
    private String type;
    private String arg;
    
    @Override
    public FData nextFData() {
        
        //TODO 

        if ( "regex".equalsIgnoreCase(type) ){
            return new FDataRegexField(fieldName, excludeInOutput, arg);
        } else{
            return new FDataRegexField(fieldName, excludeInOutput, arg);
            
        }
    }
    
    @Override
    public FData nextFData(Object arg) {
        return nextFData();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }


}

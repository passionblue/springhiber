package com.datagen.source.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FData;
import com.datagen.FDataSource;
public class FDSourceReference extends AbstractDataSource {

    private static Logger m_logger = LoggerFactory.getLogger(FDSourceReference.class);

    private String      sourceRefName;
    private FDataSource sourceRef;
    
    //Default Constructor
    public FDSourceReference() {
    }

    @Override
    public FData nextFData() {
        return ((AbstractDataSource)sourceRef).nextFData();
    }

    @Override
    public FData nextFData(Object arg) {
        return ((AbstractDataSource)sourceRef).nextFData(arg);
    }

    public String getSourceRefName() {
        return sourceRefName;
    }

    public void setSourceRefName(String sourceRefName) {
        this.sourceRefName = sourceRefName;
    }

    public FDataSource getSourceRef() {
        return sourceRef;
    }

    public void setSourceRef(FDataSource sourceRef) {
        this.sourceRef = sourceRef;
    }
    

    
    

}

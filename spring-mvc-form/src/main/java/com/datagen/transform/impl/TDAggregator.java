package com.datagen.transform.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FData;
import com.datagen.FDataTransformer;

public class TDAggregator implements FDataTransformer {

    private static Logger m_logger = LoggerFactory.getLogger(TDAggregator.class);
    
    private List<FDataTransformer> transformers;
    
    @Override
    public FData transform(FData sourceWrapper) {

        if ( transformers == null) return sourceWrapper;
        
        for (FDataTransformer transformer : transformers) {
            sourceWrapper = transformer.transform(sourceWrapper);
        }
        
        return sourceWrapper;
    }

    
}

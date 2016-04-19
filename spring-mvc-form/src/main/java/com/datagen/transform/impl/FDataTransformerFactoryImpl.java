package com.datagen.transform.impl;

import java.util.Map;

import com.datagen.FData;
import com.datagen.FDataTransformFactory;
import com.datagen.FDataTransformer;

public class FDataTransformerFactoryImpl implements FDataTransformFactory {

    
    private Map<String, FDataTransformer> transformers;
    
    
    
    @Override
    public FDataTransformer getTransformer(FData data) {
        
        if ( data != null && transformers.get(data) != null)
            return transformers.get(data);
        
        return transformers.get("noop");
    }

    public Map<String, FDataTransformer> getTransformers() {
        return transformers;
    }

    public void setTransformers(Map<String, FDataTransformer> transformers) {
        this.transformers = transformers;
    }
}

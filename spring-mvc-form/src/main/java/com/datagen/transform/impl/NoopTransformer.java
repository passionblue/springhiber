package com.datagen.transform.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FData;
import com.datagen.transform.AbstractFDataTransformer;

public class NoopTransformer extends AbstractFDataTransformer {

    private static Logger m_logger = LoggerFactory.getLogger(NoopTransformer.class);
    
    @Override
    public FData transform(FData source) {
//        m_logger.debug("NoopTransformer.transform()");
        return source;
    }

}

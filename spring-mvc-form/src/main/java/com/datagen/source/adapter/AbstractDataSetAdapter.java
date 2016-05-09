package com.datagen.source.adapter;

import org.apache.commons.lang3.NotImplementedException;

import com.datagen.DataGenContext;
import com.datagen.source.FDataSourceAdapter;

abstract public class AbstractDataSetAdapter<T> implements FDataSourceAdapter<T> {

    @Override
    public void reload(DataGenContext context) throws Exception {
        throw new NotImplementedException("AbstractDataSetAdapter.reload()");
    }

    @Override
    public void close() {
        
    }

    
}

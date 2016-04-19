package com.datagen.source.impl;

import java.util.List;

import com.datagen.FDataSource;
import com.datagen.FDataSourceMixer;

/*
 * Get data from multiple data sources and return new set of data after processing. 
 * The best use, with multiple generic dataSource, it creates co-related data sets by grouping differently. 
 * 
 */


abstract public class AbstractFDataSourceMixer implements FDataSourceMixer{

    private List<FDataSource> sources;
    
    @Override
    public List<FDataSource> getSources() {
        return sources;
    }

}

package com.datagen.source;

import java.util.Map;

import com.datagen.FDataSource;

public class FDataSourceFactory {

    private Map<String, FDataSource> dataSourceMap;

    public Map<String, FDataSource> getDataSourceMap() {
        return dataSourceMap;
    }

    public void setDataSourceMap(Map<String, FDataSource> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
    }
}

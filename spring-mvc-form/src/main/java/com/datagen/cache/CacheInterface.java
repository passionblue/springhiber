package com.datagen.cache;

import java.util.List;

import com.datagen.FDataRow;

public interface CacheInterface {

    void store(FDataRow dataRow);
    int dataSize();
    
    List<FDataRow> getAll();
}

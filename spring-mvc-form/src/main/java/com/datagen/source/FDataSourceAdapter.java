package com.datagen.source;

import com.datagen.DataGenContext;

/*
 * Pre load fixed/limited set up data and return the single String data 
 */
public interface FDataSourceAdapter<T> {
    
    void reload(DataGenContext context) throws Exception;
    void close();
    
    int getDataSize();
    T getByPosition(int pos);

}

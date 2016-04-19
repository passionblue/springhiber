package com.datagen.source.adapter;

import com.datagen.source.FDataSourceAdapter;

/*
 * First load the keys only and retrieve data when it is needed. 
 * Used to load randomly from large volume of data. 
 */
public class KeyBasedDatabaseAdapter  extends AbstractDataSetAdapter<String>{

    
    
    
    @Override
    public int getDataSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getByPosition(int pos) {
        // TODO Auto-generated method stub
        return null;
    }

}

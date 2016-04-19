package com.datagen;

import java.util.List;

public interface FDataGroup extends FData<List<FData>> {

    /*
     * get all the fields
     */
    List<FData> getUnderlyingData();
    
}

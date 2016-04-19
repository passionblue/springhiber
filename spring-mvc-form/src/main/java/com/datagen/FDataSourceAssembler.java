package com.datagen;

import java.util.List;

public interface FDataSourceAssembler {

    List<FDataSource>       getSources(DataGenContext runContext) throws Exception ;
    List<FDataSource>       getPreLoadSources(DataGenContext runContext) throws Exception ;

    //    FDataSortedList         getFData();
}

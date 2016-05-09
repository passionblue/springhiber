package com.datagen;

import java.util.List;

public interface FDataSourceAssembler {

    List<FDataSource>       getSources(DataGenContext runContext) throws Exception ;
    List<FDataSource>       getPreLoadSources(DataGenContext runContext) throws Exception ;
    
    /*
     * Data will be loaded
     */
    List<DataGenContext>    getPreLoadConfigs(DataGenContext runContext) throws Exception ;
    /*
     * No loading from the imported configs
     */
    List<DataGenContext>    getImportConfigs(DataGenContext runContext) throws Exception ;
    

    //    FDataSortedList         getFData();
}

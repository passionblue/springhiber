package com.datagen;

public interface FDataSource {

    FData generateNext();
    FData generateNext(Object arg);
    
    void setFieldName(String name);
    void setRunId(String id);
    void reload(DataGenContext context) throws Exception ;
}

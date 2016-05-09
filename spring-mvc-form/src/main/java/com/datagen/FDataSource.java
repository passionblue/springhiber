package com.datagen;

import com.datagen.fault.FaultGenerater;

public interface FDataSource {

    FData generateNext();
    FData generateNext(Object arg);
    boolean excludeInOutput();
    
    String getFieldName();
    void setFieldName(String name);
    void setRunId(String id);
    void setExcludeInOutput(boolean exclude);
    void setFaultGenerater(FaultGenerater faultGen);

    void reload(DataGenContext context) throws Exception ;
    void close();
    
}

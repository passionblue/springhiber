package com.datagen.source.adapter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.DataGenContext;
import com.datagen.FData;
import com.datagen.FDataRow;
import com.datagen.cache.CacheInterface;
import com.datagen.cache.MemoryCache;
import com.datagen.util.XmlConfigParameterUtil;

/*
 * Pre-load intended dataset into memory and return from that. 
 * This adapter should not be used with FDRandomSingleFDLoadFromAdapter. Because the field of the source is
 * only sticky group or field. So the underlying group will not be properly represented. 
 * 
 * use FDRandomMultipleFDLoadFromAdapter. so that the underlying Fields will be seamlessly used 
 * 
 */

public class PreloadDatabaseAdapter  extends AbstractDataSetAdapter<List<FData>>{
    
    private static Logger m_logger = LoggerFactory.getLogger(PreloadDatabaseAdapter.class);
    
    private CacheInterface cacheInterface = MemoryCache.instance;

    private String dataSet;
    private String fieldNameList;
    private List<FDataRow> dataList = new ArrayList();

    public PreloadDatabaseAdapter() {
        
    }

    public PreloadDatabaseAdapter(String id) {
        
    }
    
    
    @Override
    public void reload(DataGenContext context) throws Exception {
        cacheInterface = MemoryCache.getCache(context.getId(dataSet));
        dataList = cacheInterface.getAll();
        m_logger.info("PreloadDatabaseAdapter.reload() from MEMORY " + context.getId(null) + ",count=" + dataList.size());
    }

    @Override
    public int getDataSize() {
        return cacheInterface.dataSize();
    }

    @Override
    public List<FData> getByPosition(int pos) {
        
        FDataRow row = dataList.get(pos);
        
        List<FData> ret = new ArrayList();
        
        String[] fieldNames = XmlConfigParameterUtil.convertToStringArray(fieldNameList, true);
        if (fieldNames == null|| fieldNames.length == 0) return ret;
        
        for (int i = 0; i < fieldNames.length; i++) {
            FData data = row.getByName(fieldNames[i]);
            
            //TODO if fData is set to "excludeInOutput" from previous workflow
            // What should I do here. For now, don't exclude them
//            if (data.excludeInOutput()){
//                cotninue;
//            }
            
            if (data != null) {
                ret.add(data);
            } else {
                throw new IllegalArgumentException("Field not found for [" + fieldNames[i] +"] while pre-loading data");
            }
        }
        return ret;
    }

    public String getFieldNameList() {
        return fieldNameList;
    }

    public void setFieldNameList(String fieldNameList) {
        this.fieldNameList = fieldNameList;
    }

    
    public String[] parseFieldNames() {
        if ( fieldNameList == null ) 
            return null;
        
        return fieldNameList.split(",", -1);
    }

    public String getDataSet() {
        return dataSet;
    }

    public void setDataSet(String dataSet) {
        this.dataSet = dataSet;
    }


}

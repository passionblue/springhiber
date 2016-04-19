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
 * 
 */

public class PreloadDatabaseAdapter  extends AbstractDataSetAdapter<List<FData>>{
    
    private static Logger m_logger = LoggerFactory.getLogger(PreloadDatabaseAdapter.class);
    
    private CacheInterface cacheInterface = MemoryCache.instance;

    private String fieldNameList;
    private List<FDataRow> dataList = new ArrayList();

    public PreloadDatabaseAdapter() {
        
    }

    public PreloadDatabaseAdapter(String id) {
        
    }
    
    
    @Override
    public void reload(DataGenContext context) throws Exception {
        cacheInterface = MemoryCache.getCache(context.getId());
        dataList = cacheInterface.getAll();
        
        m_logger.info("PreloadDatabaseAdapter.reload() from MEMORY " + context.getId() + ",count=" + dataList.size());
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

}

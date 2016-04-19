package com.datagen.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.datagen.FDataRow;

public class MemoryCache implements CacheInterface {

    private List<FDataRow> dataList = new ArrayList();

    public static Map<String, MemoryCache> cacheMap = new ConcurrentHashMap<>();
    
    public static MemoryCache instance = new MemoryCache();
    
    @Override
    public synchronized void store(FDataRow dataRow) {
        dataList.add(dataRow);
    }

    
    
    @Override
    public synchronized List<FDataRow> getAll() {
        return new ArrayList(dataList);
    }



    @Override
    public synchronized int dataSize() {
        // TODO Auto-generated method stub
        return dataList.size();
    }



    public synchronized List<FDataRow> retrieve() {
        return new ArrayList(dataList);
    }

    public static MemoryCache getCache(String id) {
        
        if (!cacheMap.containsKey(id)){
            cacheMap.put(id, new MemoryCache());
        }
        return cacheMap.get(id);
    }
    
}

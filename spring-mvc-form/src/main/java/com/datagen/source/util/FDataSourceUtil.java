package com.datagen.source.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FDataSource;
import com.datagen.source.impl.FDSourceReference;
public class FDataSourceUtil {

    private static Logger m_logger = LoggerFactory.getLogger(FDataSourceUtil.class);

    public static void resolveSourceRefs(List<FDataSource> sources ) {

        Map<String, FDataSource> map = new HashMap();
        
        for (Iterator iterator = sources.iterator(); iterator.hasNext();) {
            FDataSource fDataSource = (FDataSource) iterator.next();
            
            map.put(fDataSource.getFieldName(), fDataSource);
        }
        
        for (Iterator iterator = sources.iterator(); iterator.hasNext();) {
            FDataSource fDataSource = (FDataSource) iterator.next();
            
            if (fDataSource instanceof FDSourceReference) {

                FDataSource referencedSourcee = map.get(((FDSourceReference) fDataSource).getSourceRefName());
                if ( referencedSourcee != null ) {
                    ((FDSourceReference) fDataSource).setSourceRef(referencedSourcee);
                    
                } else {
                    throw new IllegalArgumentException("Referenced Data Source by [" + ((FDSourceReference) fDataSource).getSourceRefName() + "] not found in the scope. Currently ref DS only refers to the same file scope. For field " + fDataSource.getFieldName());
                }
                
            }
        
        }
    }
    
    public static void checkDuplicityOnSourceRefs(List<FDataSource> sources ) {

        Map<String, FDataSource> map = new HashMap();
        
        for (Iterator iterator = sources.iterator(); iterator.hasNext();) {
            FDataSource fDataSource = (FDataSource) iterator.next();
            
            if ( map.containsKey(fDataSource.getFieldName())) {
                throw new IllegalArgumentException("Duplicate field names detected " + fDataSource.getFieldName());
            }
            map.put(fDataSource.getFieldName(), fDataSource);
        }
    }    
    
    
    private static List<FDataSource> mergeAndResolve(boolean allowDuplicateNames, List<FDataSource>... sources ) {
        
        Map<String, FDataSource>    map = new HashMap();
        List<FDataSource>           mergedList = new ArrayList();
        
        if ( allowDuplicateNames ) {
            for (int i = 0; i < sources.length; i++) {
                mergedList.addAll(sources[i]);
            }
        } else {
            for (int i = 0; i < sources.length; i++) {
                for (Iterator iterator = sources[i].iterator(); iterator.hasNext();) {
                    FDataSource fDataSource = (FDataSource) iterator.next();
                    if ( map.containsKey(fDataSource.getFieldName())) {
                        m_logger.warn("Dropped while merging dataSource due to duplicate name. " + fDataSource.getFieldName());
                    } else {
                        mergedList.add(fDataSource);
                    }
                }
            }
        }
        
        resolveSourceRefs(mergedList);
        return mergedList;
    }    
}

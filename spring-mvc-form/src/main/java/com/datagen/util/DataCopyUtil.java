package com.datagen.util;

import org.apache.commons.lang3.StringUtils;

public class DataCopyUtil {


    /*
     * copy the source array to output array with string pointed by selected array.
     */
    
    public static String[] copyStringArrayByIndex(String[] source, int[] selectedColumns) {
        
        String [] selectedData = new String[selectedColumns.length];
        
        for (int i = 0; i < selectedColumns.length; i++) {
            
            if ( selectedColumns[i] >= source.length || selectedColumns[i] < 0 ) 
                selectedData[i] = null;
            else 
                selectedData[i] = StringUtils.trim(source[selectedColumns[i]]);
        }
        
        return selectedData;
    }
    
}

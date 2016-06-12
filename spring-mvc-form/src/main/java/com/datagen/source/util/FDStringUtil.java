package com.datagen.source.util;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FDStringUtil {

    private static Logger m_logger = LoggerFactory.getLogger(FDStringUtil.class);


    
    
    public static String toString(String [] str) {
        
        if ( str == null || str.length == 0 ) return "";
        
//        StringBuilder builder = new StringBuilder();
//        
//        for (int i = 0; i < str.length; i++) {
//            builder.append(str[i] == null?"":str[i]).append(",");
//        }

        return Arrays.deepToString(str);
    }
}

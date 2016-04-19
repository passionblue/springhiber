package com.datagen.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUtil {

    private static Logger m_logger = LoggerFactory.getLogger(LoggingUtil.class);
    
    
    public static void log(Object...objects ) {
        
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < objects.length; i++) {
            if ( objects[i] == null) 
                build.append(",");
            else
                build.append(objects[i]).append(",");
        }
        
        m_logger.debug("LOG>" + build.toString());
        
    }
    
}

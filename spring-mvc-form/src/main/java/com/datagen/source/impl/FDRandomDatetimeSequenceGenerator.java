package com.datagen.source.impl;

import java.util.Date;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.DataGenContext;
import com.datagen.FData;
import com.datagen.data.FDataString;


/*
 * Generate timestamp in specified format from the start time. 
 * It only increment with random interval
 * 
 * 
 */

public class FDRandomDatetimeSequenceGenerator extends AbstractDataSource {

    private static Logger m_logger = LoggerFactory.getLogger(FDRandomDatetimeSequenceGenerator.class);
    
    public static final String DEFAULT_DATETIME_PATTER = "yyyyMMdd-HH:mm:ss.SSS";
    
    private String dateTimeStart; 
    private long   maxIntervalInMs; 

    private String dateTimePattern = DEFAULT_DATETIME_PATTER;
    private String outputClass = "java.lang.String";

    
    private long currentTimStamp;
    
    
    @Override
    public void reload(DataGenContext context) throws Exception {
        currentTimStamp = DateTimeFormat.forPattern(dateTimePattern).parseDateTime(dateTimeStart).getMillis();
        
        m_logger.info("initialized [{}] <- [{}]", currentTimStamp, dateTimeStart);
        
    }

    @Override
    public FData nextFData() {
        
        long randomIncrement = RandomUtils.nextLong(0, maxIntervalInMs);

        currentTimStamp += randomIncrement;
        
        Class clazz = String.class;
        try {
            clazz = Class.forName(outputClass);
        }
        catch (ClassNotFoundException e) {
            m_logger.error("",e);
        }
        String data = null;
        
        if ( clazz == String.class ) {
            data = new DateTime(new Date(currentTimStamp)).toString(dateTimePattern);            
        } else if ( clazz == Long.class ) {
            data = String.valueOf(currentTimStamp);
        } else if ( clazz == Date.class ) {
            data = new Date(currentTimStamp).toString();
        } else {
            data = new DateTime(new Date(currentTimStamp)).toString(dateTimePattern);            
        }

        
        return new FDataString(getFieldName(), excludeInOutput, data);
    }
    
    @Override
    public FData nextFData(Object arg) {
        return nextFData();
    }



    public String getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(String dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }


    public long getMaxIntervalInMs() {
        return maxIntervalInMs;
    }

    public void setMaxIntervalInMs(long maxIntervalInMs) {
        this.maxIntervalInMs = maxIntervalInMs;
    }

    public long getCurrentTimStamp() {
        return currentTimStamp;
    }

    public void setCurrentTimStamp(long currentTimStamp) {
        this.currentTimStamp = currentTimStamp;
    }

    public String getDateTimePattern() {
        return dateTimePattern;
    }

    public void setDateTimePattern(String dateTimePattern) {
        this.dateTimePattern = dateTimePattern;
    }

    
    
    
    public String getOutputClass() {
        return outputClass;
    }

    public void setOutputClass(String outputClass) {
        this.outputClass = outputClass;
    }

    public static void main(String[] args) throws Exception {

        
        
        
    }
    
    
}

package com.datagen.source.impl;

import java.util.Date;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FData;
import com.datagen.data.FDataDate;
import com.datagen.data.FDataString;

public class FDRandomDatetimeGenerator extends AbstractDataSource {

    private static Logger m_logger = LoggerFactory.getLogger(FDRandomDatetimeGenerator.class);
    
    public static final String DEFAULT_DATETIME_PATTER = "yyyyMMdd-HH:mm:ss.SSS";
    
    private String dateStart; 
    private String dateEnd;
    private String dateTimePattern = DEFAULT_DATETIME_PATTER;
    private String outputClass = "java.lang.String";
    
    @Override
    public FData nextFData() {


        
        Date start = new Date(0);
        Date end = new Date();
               
        if ( StringUtils.isNotBlank(dateStart))
            start = DateTimeFormat.forPattern(dateTimePattern).parseDateTime(dateStart).toDate();
        
        if ( StringUtils.isNotBlank(dateEnd))
            end = DateTimeFormat.forPattern(dateTimePattern).parseDateTime(dateEnd).toDate();
        
        long randomTime = RandomUtils.nextLong(start.getTime(), end.getTime());


        Class clazz = String.class;
        try {
            clazz = Class.forName(outputClass);
        }
        catch (ClassNotFoundException e) {
            m_logger.error("",e);
        }
        String data = null;
        
        if ( clazz == String.class ) {
            data = new DateTime(new Date(randomTime)).toString(dateTimePattern);            
            return new FDataString(getFieldName(), excludeInOutput, data);
        
        } else if ( clazz == Long.class ) {
            data = String.valueOf(randomTime);
            return new FDataString(getFieldName(), excludeInOutput, data);
        } else if ( clazz == Date.class ) {
            return new FDataDate(getFieldName(), excludeInOutput, new Date(randomTime) , dateTimePattern);
        } else {
            data = new DateTime(new Date(randomTime)).toString(dateTimePattern);            
        }
        
        return new FDataString(getFieldName(), excludeInOutput, data);
    }
    
    @Override
    public FData nextFData(Object arg) {
        return nextFData();
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
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

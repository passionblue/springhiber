package com.datagen.source.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FData;
import com.datagen.data.FDataNull;
import com.datagen.data.FDataString;

public class FDRandomNumberGenerator extends AbstractDataSource {

    private static Logger m_logger = LoggerFactory.getLogger(FDRandomNumberGenerator.class);
    
    private String rangeStart;
    private String rangeEnd;
    private String numberClass;//
    
    @Override
    public FData nextFData() {

        Object data = null;
        try {
            Class clazz = Class.forName(numberClass);
            data = null; 
                    
                    
            if (clazz == Integer.class) {
                data = RandomUtils.nextInt(Integer.parseInt(rangeStart), Integer.parseInt(rangeEnd));

            } else if ( clazz == Double.class ) {
                data = RandomUtils.nextDouble(Double.parseDouble(rangeStart), Double.parseDouble(rangeEnd));

            } else if ( clazz == Float.class ) {
                data = RandomUtils.nextDouble(Double.parseDouble(rangeStart), Double.parseDouble(rangeEnd));
            
            } else if ( clazz == BigDecimal.class ) {
                double d = RandomUtils.nextDouble(Double.parseDouble(rangeStart), Double.parseDouble(rangeEnd));
                data = new BigDecimal(d);
            }
        
        }
        catch (Exception e) {
            m_logger.error(e.getMessage(),e);
        }

        if ( data == null)
            return new FDataNull(fieldName, excludeInOutput);
        
        return new FDataString(fieldName, excludeInOutput, data);

    }
    
    @Override
    public FData nextFData(Object arg) {
        return nextFData();
    }

    public String getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(String rangeStart) {
        this.rangeStart = rangeStart;
    }

    public String getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(String rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public String getNumberClass() {
        return numberClass;
    }

    public void setNumberClass(String numberClass) {
        this.numberClass = numberClass;
    }


    
}

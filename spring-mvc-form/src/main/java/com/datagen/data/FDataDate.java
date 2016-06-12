package com.datagen.data;

import java.util.Date;

import org.joda.time.DateTime;

public class FDataDate extends AbstractFData<Date> {

    private String data;
    private Date rawData;
    private String pattern;

    public FDataDate(String fieldName, boolean excludeInOutput, Date obj, String pattern) {
        super(fieldName, excludeInOutput);
        this.rawData = obj;
        if (obj != null) {
            data = obj.toString();
        }
    }    

    @Override
    public Date getRawFormat() {
        return rawData;
    }
    
    @Override
    public String getStringFormat() {

        if( rawData == null) return null;
        
        String str = new DateTime(rawData).toString(pattern);
        return str;
    }

    @Override
    public String toString() {
        return getStringFormat();
    }
    
}

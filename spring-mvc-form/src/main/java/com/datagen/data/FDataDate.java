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
    
//    public FDataString(Object obj) {
//        this(null, obj);
//    }

    @Override
    public Date getRawFormat() {
        return rawData;
    }

    
    
    @Override
    public String getStringFormat() {
        String str = new DateTime(rawData).toString(pattern);
        return str;
    }

    @Override
    public String toString() {
        String str = new DateTime(rawData).toString(pattern);
        return str;
    }
    
}

package com.datagen.output.formatter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FData;
import com.datagen.FDataRow;
import com.datagen.output.OutputRowChannelFormatter;

public class FormatRowToSQL implements OutputRowChannelFormatter<String>{

    private static Logger m_logger = LoggerFactory.getLogger(FormatRowToSQL.class);

    private Set<String> passingFields = new HashSet();
    
    //Default Constructor
    public FormatRowToSQL(String fieldNames) {
        
        if ( !StringUtils.isBlank(fieldNames)) {
            String[] fields = fieldNames.split(",", -1);
            passingFields.addAll(Arrays.asList(fields));
        }
    }

    @Override
    public String format(FDataRow row) {


        
        return null;
    }    
}

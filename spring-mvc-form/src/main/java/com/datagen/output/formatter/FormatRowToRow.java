package com.datagen.output.formatter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FDataRow;
import com.datagen.output.OutputRowChannelFormatter;

public class FormatRowToRow implements OutputRowChannelFormatter<FDataRow>{

    private static Logger m_logger = LoggerFactory.getLogger(FormatRowToRow.class);

    private Set<String> passingFields = new HashSet();
    
    //Default Constructor
    public FormatRowToRow(String fieldNames) {
        
        if ( !StringUtils.isBlank(fieldNames)) {
            String[] fields = fieldNames.split(",");
            passingFields.addAll(Arrays.asList(fields));
        }
    }

    @Override
    public FDataRow format(FDataRow row) {

        if ( passingFields.size() > 0 ){
            
        }
        return row;
    }    
}

package com.datagen.output.formatter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FData;
import com.datagen.FDataGroup;
import com.datagen.FDataRow;
import com.datagen.output.OutputRowChannelFormatter;

public class FormatRowToDelimiteredString implements OutputRowChannelFormatter<String>{

    private static Logger m_logger = LoggerFactory.getLogger(FormatRowToDelimiteredString.class);

    private String delimiter = ",";
    
    //Default Constructor
    public FormatRowToDelimiteredString() {
    }
    
    //Default Constructor
    public FormatRowToDelimiteredString(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public String format(FDataRow row) {
        
        List<FData> fields = row.getData(true);

        StringBuilder builder = new StringBuilder();

        for (FData fData : fields) {

            if ( fData.excludeInOutput() ) 
                continue;
            
            if ( fData instanceof FDataGroup ) {
                FDataGroup group = (FDataGroup) fData;
                List<FData> dataFields = group.getUnderlyingData();
                for (FData fd : dataFields) {
                    builder.append(fd.getRawFormat().toString()).append(delimiter);
                }
            } else {
                builder.append(fData.getRawFormat() == null?"":fData.getRawFormat().toString()).append(delimiter);
            }
        }
        
        builder.delete(builder.length() - delimiter.length(), builder.length());
        return builder.toString();
    }    
}

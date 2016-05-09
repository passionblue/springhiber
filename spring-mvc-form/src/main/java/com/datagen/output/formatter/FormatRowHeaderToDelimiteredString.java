package com.datagen.output.formatter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FData;
import com.datagen.FDataGroup;
import com.datagen.FDataRow;
import com.datagen.meta.FieldMetaData;
import com.datagen.meta.FieldMetaDataManager;
import com.datagen.output.OutputRowChannelFormatter;

public class FormatRowHeaderToDelimiteredString implements OutputRowChannelFormatter<String>{

    private static Logger m_logger = LoggerFactory.getLogger(FormatRowHeaderToDelimiteredString.class);

    private String delimiter = ",";
    
    //Default Constructor
    public FormatRowHeaderToDelimiteredString() {
    }
    //Default Constructor
    public FormatRowHeaderToDelimiteredString(String delimiter) {
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
                List<FData> dataFields = group.getUnderlyingDatas();
                int subIndex = 0;
                for (FData fd : dataFields) {
                    
                    FieldMetaData meta = FieldMetaDataManager.getInstance().getMetaData(fd.getFieldName());
                    
                    // Because data gets splitted from one singe FData record, add sub fix name at the end of field header name 
                    builder.append(meta==null?fd.getFieldName(): meta.getDisplay()).append("(" + subIndex + ")").append(delimiter); 
                    subIndex++;
                }
            } else {
                FieldMetaData meta = FieldMetaDataManager.getInstance().getMetaData(fData.getFieldName());
                builder.append(meta==null?fData.getFieldName(): meta.getDisplay()).append(delimiter);
            }
        }
        
        builder.delete(builder.length() - delimiter.length(), builder.length());
        return builder.toString();
    }    
}

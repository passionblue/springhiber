package com.datagen.output.formatter;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FData;
import com.datagen.FDataGroup;
import com.datagen.FDataRow;
import com.datagen.output.OutputRowChannelFormatter;

public class FormatRowToFixedWidthString implements OutputRowChannelFormatter<String>{

    private static Logger m_logger = LoggerFactory.getLogger(FormatRowToFixedWidthString.class);

    private String delimiter = " ";
    private int    columnWidth;
    private boolean cutoutExtra;
    
    //Default Constructor
    public FormatRowToFixedWidthString() {
    }
    
    //Default Constructor
    public FormatRowToFixedWidthString(String delimiter) {
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
                for (FData fd : dataFields) {
                    builder.append(convert(fd.getRawFormat().toString())).append(delimiter);
                }
            } else {
                builder.append(convert(fData.getRawFormat() == null?"":fData.getRawFormat().toString())).append(delimiter);
            }
        }
        
        builder.delete(builder.length() - delimiter.length(), builder.length());

        return builder.toString();
    }    
    
    private String convert(Object data) {
        if (data == null ) {
            return StringUtils.rightPad(" ", columnWidth);
        }
        
        if ( cutoutExtra && data.toString().trim().length() > columnWidth) {
            
            return data.toString().trim().substring(0,  columnWidth);
        } 
        
        return StringUtils.rightPad(data.toString(), columnWidth);
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    public boolean isCutoutExtra() {
        return cutoutExtra;
    }

    public void setCutoutExtra(boolean cutoutExtra) {
        this.cutoutExtra = cutoutExtra;
    }
    
    
}

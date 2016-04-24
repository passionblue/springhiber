package com.datagen.source.impl;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import com.datagen.FData;
import com.datagen.configuration.FDConfigurator;
import com.datagen.data.FDataStickyGroupImpl;
import com.datagen.data.FDataGroupImpl;
import com.datagen.data.FDataNull;
import com.datagen.data.FDataString;
import com.datagen.util.DataCopyUtil;
import com.datagen.util.XmlConfigParameterUtil;

public class FDRandomSingleFDLoadFromAdapter extends AbstractDataSource {
    
    public static String FDCONFIG_RANDOM_STRING_MAX_LENGTH              = "fdata.source.random_string.max_length";  // Integer
    public static String FDCONFIG_RANDOM_STRING_FIXED_LENGTH            = "fdata.source.random_string.fixed_length"; // TRUE, FALSE
    public static String FDCONFIG_RANDOM_STRING_FORMAT_MASK             = "fdata.source.random_string.format_mask"; //T
    public static String FDCONFIG_RANDOM_STRING_CONTENT_VARIATION       = "fdata.source.random_string.content_variation";  // NUMERIC, ALPHANUMERIC, CHARACTER
    
    private FDConfigurator configurator;
    private String         columnToLoadForMultiColumnData;
    private int[]          selectedColumns;
    
    @Override
    public FData nextFData() {
        
        int max = dataAdapter.getDataSize();
        Object data = dataAdapter.getByPosition(RandomUtils.nextInt(0, max));
        
        if ( data instanceof String) 
            return new FDataString(fieldName, excludeInOutput, data);

        else if ( data instanceof String[] ) {
            /*
             * Source data is array of String. If multiple columns were selected, it will be loaded with SingleGroup, which 
             * is the difference from MultipleFData loader
             */
            String [] array = (String[]) data;
            if ( selectedColumns != null && selectedColumns.length > 0 ) {
                
                if ( selectedColumns.length == 1) 
                    return new FDataString(fieldName, excludeInOutput, array[selectedColumns[0]]);
                else {
                    String [] selectedData = DataCopyUtil.copyStringArrayByIndex(array, selectedColumns);;
                    return new FDataStickyGroupImpl(fieldName,excludeInOutput, selectedData);
                }
            } else {
                return new FDataStickyGroupImpl(fieldName,excludeInOutput, array);
            }            
        } else if ( data instanceof FData ) {

            ((FData) data).setFieldName(fieldName);
            return (FData) data;
            
        } else if ( data instanceof List ) {
            
            return new FDataGroupImpl( fieldName, excludeInOutput, (List<FData>) data);
        }
        
        return new FDataNull(fieldName, excludeInOutput);
    }
    
    @Override
    public FData nextFData(Object arg) {
        return nextFData();
    }


    public FDConfigurator getConfigurator() {
        return configurator;
    }

    public void setConfigurator(FDConfigurator configurator) {
        this.configurator = configurator;
    }

    public String getColumnToLoadForMultiColumnData() {
        return columnToLoadForMultiColumnData;
    }

    public void setColumnToLoadForMultiColumnData(String columnToLoadForMultiColumnData) {
        this.columnToLoadForMultiColumnData = columnToLoadForMultiColumnData;
        
        this.selectedColumns = XmlConfigParameterUtil.convertFrom(this.columnToLoadForMultiColumnData);
    }

    
    
    
    

    
}

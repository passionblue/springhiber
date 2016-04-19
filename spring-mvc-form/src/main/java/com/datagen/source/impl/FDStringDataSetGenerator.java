package com.datagen.source.impl;

import org.apache.commons.lang3.RandomUtils;

import com.datagen.FData;
import com.datagen.FDataSource;
import com.datagen.configuration.FDConfigurator;
import com.datagen.data.FDataString;
import com.datagen.source.FDataSourceAdapter; 

public class FDStringDataSetGenerator extends AbstractDataSource {
    
    public static String FDCONFIG_random_distribution_MAX_LENGTH              = "fdata.source.random_distribution.max_length";  // Integer
    public static String FDCONFIG_random_distribution_FIXED_LENGTH            = "fdata.source.random_distribution.fixed_length"; // TRUE, FALSE
    public static String FDCONFIG_random_distribution_FORMAT_MASK             = "fdata.source.random_distribution.format_mask"; //T
    public static String FDCONFIG_random_distribution_CONTENT_VARIATION       = "fdata.source.random_distribution.content_variation";  // NUMERIC, ALPHANUMERIC, CHARACTER
    
    private FDConfigurator configurator;
    private FDataSourceAdapter<String> dataLoadHolder;
    
    @Override
    public FData generateNext() {

        int nextPos = RandomUtils.nextInt(0,  dataLoadHolder.getDataSize());
    
        
        
        
        String raw = dataLoadHolder.getByPosition(nextPos);
        return new FDataString(fieldName, raw);
    }

    //TODO to support non-random generation
    
    @Override
    public FData generateNext(Object arg) {
        
            return generateNext();
    }
    
}

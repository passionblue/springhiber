package com.datagen.source.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import static java.util.Optional.ofNullable;

import com.datagen.FData;
import com.datagen.configuration.FDConfigurator;
import com.datagen.data.FDataString;

public class FDRandomStringGenerator extends AbstractDataSource {
    
    public static String FDCONFIG_RANDOM_STRING_MAX_LENGTH              = "fdata.source.random_string.max_length";  // Integer
    public static String FDCONFIG_RANDOM_STRING_FIXED_LENGTH            = "fdata.source.random_string.fixed_length"; // TRUE, FALSE
    public static String FDCONFIG_RANDOM_STRING_FORMAT_MASK             = "fdata.source.random_string.format_mask"; //T
    public static String FDCONFIG_RANDOM_STRING_CONTENT_VARIATION       = "fdata.source.random_string.content_variation";  // NUMERIC, ALPHANUMERIC, CHARACTER
    
    private FDConfigurator configurator;
    /*
     * Configurable Properties
     */
//    private String prefixString;
//    private String appendingString;
//    private String mask;
    private int    defaultLength = 10; 
    private boolean fixedLength = true;
    private boolean numbersOnly = false;
    private boolean lettersOnly = false;
    
    @Override
    public FData generateNext() {

//        String raw = RandomStringUtils.random(Integer.parseInt((String)configurator.getConfig(FDCONFIG_RANDOM_STRING_MAX_LENGTH, "10")));
        
        boolean letter = lettersOnly || ( !lettersOnly && !numbersOnly  );
        boolean number = numbersOnly || ( !lettersOnly && !numbersOnly  );
        
        int dataLength = defaultLength;

        if ( !fixedLength) {
            dataLength = RandomUtils.nextInt(1, defaultLength+1);
        }
        
        if ( StringUtils.isNotBlank(mask)) { 
            dataLength = mask.length();
        }
        
        String data = RandomStringUtils.random(dataLength, letter, number);
        
//        if ( StringUtils.isNotBlank(mask)) { 
//            data = applyMask(data);
//        }
//
//        
//        return new FDataString(ofNullable(prefixString).orElse("") + data + ofNullable(appendingString).orElse(""));

        data = applyCommonFormatter(data);
        return new FDataString(fieldName,data);
    }
    
    @Override
    public FData generateNext(Object arg) {
        return generateNext();
    }


    public FDConfigurator getConfigurator() {
        return configurator;
    }

    public void setConfigurator(FDConfigurator configurator) {
        this.configurator = configurator;
    }

//    public String getPrefixString() {
//        return prefixString;
//    }
//
//    public void setPrefixString(String prefixString) {
//        this.prefixString = prefixString;
//    }
//
//    public String getAppendingString() {
//        return appendingString;
//    }
//
//    public void setAppendingString(String appendingString) {
//        this.appendingString = appendingString;
//    }
//
//    public String getMask() {
//        return mask;
//    }
//
//    public void setMask(String mask) {
//        this.mask = mask;
//    }

    public boolean isNumbersOnly() {
        return numbersOnly;
    }

    public void setNumbersOnly(boolean numbersOnly) {
        this.numbersOnly = numbersOnly;
    }

    public boolean isLettersOnly() {
        return lettersOnly;
    }

    public void setLettersOnly(boolean lettersOnly) {
        this.lettersOnly = lettersOnly;
    }

    public int getDefaultLength() {
        return defaultLength;
    }

    public void setDefaultLength(int defaultLength) {
        this.defaultLength = defaultLength;
    }

    public boolean isFixedLength() {
        return fixedLength;
    }

    public void setFixedLength(boolean fixedLength) {
        this.fixedLength = fixedLength;
    }
    
    
    
}

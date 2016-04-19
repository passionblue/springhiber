package com.datagen.source.impl;

import static java.util.Optional.ofNullable;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.datagen.DataGenContext;
import com.datagen.FDataSource;
import com.datagen.source.FDataSourceAdapter;
import com.datagen.source.adapter.SourceFieldDataFormatter;

abstract public class AbstractDataSource implements FDataSource {

    protected String runId;
    protected String fieldName;
    protected String prefixString;
    protected String appendingString;
    protected String mask;
    
    protected List<SourceFieldDataFormatter> formatters;
    protected FDataSourceAdapter<?> dataAdapter;
    
    protected String applyCommonFormatter(String data) {
        
        if ( StringUtils.isNotBlank(mask)) { 
            data = applyMask(data);
        }
        
        return ofNullable(prefixString).orElse("") + data + ofNullable(appendingString).orElse("");
    }

    private String applyMask(String val) {

        if ( mask == null ) return val;
        
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < val.length(); i++) {

            char maskChar = mask.charAt(i);
            
            if ( Character.compare(maskChar, '*') == 0 ) 
                b.append(val.charAt(i));
            else
                b.append(maskChar);
            
        }
        
        return b.toString();
    }
    
    @Override
    public void reload(DataGenContext context) throws Exception{
        if ( dataAdapter != null)
            dataAdapter.reload(context);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPrefixString() {
        return prefixString;
    }

    public void setPrefixString(String prefixString) {
        this.prefixString = prefixString;
    }

    public String getAppendingString() {
        return appendingString;
    }

    public void setAppendingString(String appendingString) {
        this.appendingString = appendingString;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public List<SourceFieldDataFormatter> getFormatters() {
        return formatters;
    }

    public void setFormatters(List<SourceFieldDataFormatter> formatters) {
        this.formatters = formatters;
    }

    public FDataSourceAdapter<?> getDataAdapter() {
        return dataAdapter;
    }

    public void setDataAdapter(FDataSourceAdapter<?> dataAdapter) {
        this.dataAdapter = dataAdapter;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }
    
    
}

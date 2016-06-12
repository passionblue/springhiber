package com.datagen.source.impl;

import static java.util.Optional.ofNullable;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.DataGenContext;
import com.datagen.FData;
import com.datagen.FDataSource;
import com.datagen.data.FDataNull;
import com.datagen.data.FDataString;
import com.datagen.fault.FaultGenerater;
import com.datagen.source.FDataSourceAdapter;
import com.datagen.source.adapter.SourceFieldDataFormatter;

abstract public class AbstractDataSource implements FDataSource {

    private static Logger m_logger = LoggerFactory.getLogger(AbstractDataSource.class);
    
    protected String runId;
    protected String fieldName;
    protected String prefixString;
    protected String appendingString;
    protected String mask; //?
    protected String type;
    protected FaultGenerater faultGenerater;
    protected boolean excludeInOutput;
    
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
    public FData generateNext() {
        
        if ( faultGenerater != null && faultGenerater.faultRaise()) { 
            Object fault = faultGenerater.getFaultData();
            
            if ( fault == null || StringUtils.isBlank(fault.toString())){
                return new FDataNull(this.fieldName, excludeInOutput);
            } else {
                return new FDataString(fieldName, excludeInOutput, fault.toString());
            }
        }
        
        return nextFData();
    }

    @Override
    public FData generateNext(Object arg) {
        
        return  nextFData(arg);
    }

    public  abstract FData nextFData();
    public  abstract FData nextFData(Object arg);    
    
    @Override
    public void reload(DataGenContext context) throws Exception{
        if ( dataAdapter != null)
            dataAdapter.reload(context);
        m_logger.info("DataSource data loaded [{}]", fieldName);
    }

    @Override
    public void close() {
        if ( dataAdapter != null)
            dataAdapter.close();
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

    public FaultGenerater getFaultGenerater() {
        return faultGenerater;
    }

    public void setFaultGenerater(FaultGenerater faultGenerater) {
        this.faultGenerater = faultGenerater;
    }

    public boolean excludeInOutput() {
        return excludeInOutput;
    }

    public void setExcludeInOutput(boolean excludeInOutput) {
        this.excludeInOutput = excludeInOutput;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isExcludeInOutput() {
        return excludeInOutput;
    }


    
}

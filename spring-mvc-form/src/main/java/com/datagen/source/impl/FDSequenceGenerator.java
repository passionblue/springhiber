package com.datagen.source.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.DataGenContext;
import com.datagen.FData;
import com.datagen.data.FDataString;

public class FDSequenceGenerator extends AbstractDataSource {

    private static Logger m_logger = LoggerFactory.getLogger(FDSequenceGenerator.class);

    private String  storeFileName;
    private long    underlyingSeq;

    
    private boolean resetInNewRun;
    private boolean fixedSize;
    private String  sequenceType; // Normal, Hex, AlphaNumeric 
    private int     maxSize;
    private String  prefixString;
    private boolean useDataSetPrefix;
    private String  padding = "0";
    
    //Default Constructor
    public FDSequenceGenerator() {
        
        
        
        
        
    }

    @Override
    public void reload(DataGenContext context) throws Exception {
        
        String dataSetName = context.getName();
        if ( dataSetName!= null && useDataSetPrefix ) {
            prefixString = dataSetName;
        }
        
        m_logger.info("Reloading with {}", this);

        if ( ! resetInNewRun ) {
            storeFileName = "SequenceGen-" +context.getName() +".seq";
            File currFile = new File("seq/" + storeFileName);
            try {
                FileUtils.readLines(currFile);
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
    }

    @Override
    public FData nextFData() {
        
        long seq = incrementSeq();
        String seqStr = String.valueOf(seq);
        
        if ( sequenceType.equalsIgnoreCase("HEX")) {
            seqStr = Long.toHexString(seq);
            seqStr = StringUtils.leftPad(seqStr, maxSize, padding);
            
        } else if ( sequenceType.equalsIgnoreCase("ALPAHNUM")) {
            
        } else {
            seqStr = StringUtils.leftPad(seqStr, maxSize, padding);
            
        }
        
        
        seqStr = prefixString !=null? prefixString.trim() + seqStr : seqStr;
        
        return new FDataString(this.fieldName, excludeInOutput, seqStr.toUpperCase());
        
    }

    @Override
    public FData nextFData(Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getStoreFileName() {
        return storeFileName;
    }

    public void setStoreFileName(String storeFileName) {
        this.storeFileName = storeFileName;
    }

    public long getUnderlyingSeq() {
        return underlyingSeq;
    }

    public void setUnderlyingSeq(long underlyingSeq) {
        this.underlyingSeq = underlyingSeq;
    }

    public boolean isResetInNewRun() {
        return resetInNewRun;
    }

    public void setResetInNewRun(boolean resetInNewRun) {
        this.resetInNewRun = resetInNewRun;
    }

    public boolean isFixedSize() {
        return fixedSize;
    }

    public void setFixedSize(boolean fixedSize) {
        this.fixedSize = fixedSize;
    }

    public String getSequenceType() {
        return sequenceType;
    }

    public void setSequenceType(String sequenceType) {
        this.sequenceType = sequenceType;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getPrefixString() {
        return prefixString;
    }

    public void setPrefixString(String prefixString) {
        this.prefixString = prefixString;
    }
    

    public boolean isUseDataSetPrefix() {
        return useDataSetPrefix;
    }

    public void setUseDataSetPrefix(boolean useDataSetPrefix) {
        this.useDataSetPrefix = useDataSetPrefix;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

        

    @Override
    public String toString() {
        return "FDSequenceGenerator [storeFileName=" + storeFileName + ", underlyingSeq=" + underlyingSeq + ", resetInNewRun=" + resetInNewRun + ", fixedSize=" + fixedSize + ", sequenceType="
                + sequenceType + ", maxSize=" + maxSize + ", prefixString=" + prefixString + ", useDataSetPrefix=" + useDataSetPrefix + "]";
    }
    
    
    
    private long incrementSeq() {
        if ( resetInNewRun ) {
            return underlyingSeq++;
        } else {
            try {
                return loadAndIncrementFile(storeFileName);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(), e);
                return underlyingSeq++;
            }
        }
        
    }
    
    private long loadAndIncrementFile(String fileName) throws Exception {
        File currFile = new File("seq/" + fileName);
        
        long nextSeq=-1;
        
        if ( currFile.exists() ) {
            
            List<String> lines = FileUtils.readLines(currFile);
            if ( lines != null && lines.size() >  0) {
                long prevSeq = Long.parseLong(lines.get(0));
                nextSeq = prevSeq + 1;
            }
            
            
        } else {
            nextSeq = 1;
        }

        FileUtils.writeStringToFile(currFile, String.valueOf(nextSeq));
        
        return nextSeq;
    }

    
    
}

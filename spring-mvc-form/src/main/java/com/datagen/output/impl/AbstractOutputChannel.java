package com.datagen.output.impl;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.FDataRow;
import com.datagen.output.OutputChannel;
import com.datagen.output.OutputRowChannelFormatter;

abstract public class AbstractOutputChannel<T> implements OutputChannel {

    private static Logger m_logger = LoggerFactory.getLogger(AbstractOutputChannel.class);

    protected OutputRowChannelFormatter<T> headerFormatter;
    protected OutputRowChannelFormatter<T> rowFormatter;
    protected String id;
    protected int maxCount = 0;
    protected boolean includeHeader;
    protected boolean headerAdded;
    
    //Default Constructor
    public AbstractOutputChannel(String id, int maxCount) {
        this.id = id;
        if (maxCount == -1) {
            maxCount = RandomUtils.nextInt(1, 1000);
        }
        m_logger.info("MaxCount set to " + maxCount);
    }

    public AbstractOutputChannel(String id) {
        this(id, 0);
    }    
    
    @Override
    public final void write(FDataRow dataRow) throws Exception {
        m_logger.debug("Writing to output channel [" + id + "]");

        if ( includeHeader && !headerAdded ) {
            writeHeaderToChannel(headerFormatter.format(dataRow));
            m_logger.debug("Completed Header to output channel [" + id + "]");
        }

        if (reachedMax()){
            return;
        }
        
        writeToChannel(rowFormatter.format(dataRow));
        m_logger.debug("Completed writing to output channel [" + id + "] " + getClass().getSimpleName());
    }

    abstract void       writeToChannel(T t) throws Exception;
    abstract void       writeHeaderToChannel(T t) throws Exception;
    abstract boolean    reachedMax();

    @Override
    public void open() throws Exception {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }

    public OutputRowChannelFormatter<T> getRowFormatter() {
        return rowFormatter;
    }

    public void setRowFormatter(OutputRowChannelFormatter<T> rowFormatter) {
        this.rowFormatter = rowFormatter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OutputRowChannelFormatter<T> getHeaderFormatter() {
        return headerFormatter;
    }

    public void setHeaderFormatter(OutputRowChannelFormatter<T> headerFormatter) {
        this.headerFormatter = headerFormatter;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public boolean isIncludeHeader() {
        return includeHeader;
    }

    public void setIncludeHeader(boolean includeHeader) {
        this.includeHeader = includeHeader;
    }

    public boolean isHeaderAdded() {
        return headerAdded;
    }

    public void setHeaderAdded(boolean headerAdded) {
        this.headerAdded = headerAdded;
    }

    
    
    
}

package com.datagen.output;

import com.datagen.FDataRow;

public interface OutputChannel {

    void write(FDataRow dataRow) throws Exception ;
    void open() throws Exception;
    void close();
}

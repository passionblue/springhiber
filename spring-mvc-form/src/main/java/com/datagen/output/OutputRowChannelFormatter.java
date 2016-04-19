package com.datagen.output;

import com.datagen.FDataRow;

public interface OutputRowChannelFormatter<T> {

    T format(FDataRow row);
    
}

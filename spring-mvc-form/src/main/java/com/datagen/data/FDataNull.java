package com.datagen.data;

public class FDataNull extends AbstractFData<Object>  {

    public FDataNull(String fieldName) {
        super(fieldName, false);
        this.fieldName = fieldName;
    }

    public FDataNull(String fieldName, boolean excludeInOutput) {
        super(fieldName, excludeInOutput);
        this.fieldName = fieldName;
    }

    @Override
    public Object getRawFormat() {
        return null;
    }

}

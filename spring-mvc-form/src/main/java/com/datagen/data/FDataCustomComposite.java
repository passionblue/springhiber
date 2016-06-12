package com.datagen.data;


/*
 * Not used at the time. Dont know what to do 
 */
public class FDataCustomComposite extends AbstractFData<String> {

    private String data;

    @Override
    public String getRawFormat() {
        return data;
    }

    public FDataCustomComposite(String fieldName, boolean excludeInOutput) {
        super(fieldName, excludeInOutput);
    }
    public FDataCustomComposite(String fieldName,boolean excludeInOutput, String data) {
        super(fieldName, excludeInOutput);
        this.data = data;
    }

    @Override
    public String getStringFormat() {
        return data;
    }
}

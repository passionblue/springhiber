package com.datagen.data;

public class FDataCustomComposite extends AbstractFData<String> {

    private String data;
//    private String pattern;
    

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
        // TODO Auto-generated method stub
        return null;
    }
    
    
}

package com.datagen.data;

public class FDataCustomComposite extends AbstractFData<String> {

    private String data;
    private String pattern;
    
    public FDataCustomComposite(String fieldName, String pattern) {
        setFieldName(fieldName);
        this.pattern = pattern;
    }

    @Override
    public String getRawFormat() {
        return data;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    
    
}

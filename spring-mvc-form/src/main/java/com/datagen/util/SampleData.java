package com.datagen.util;

public class SampleData{
    
    
    private String data;
    private Integer value;
    
    
    
    public SampleData(String data, Integer value) {
        super();
        this.data = data;
        this.value = value;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
}
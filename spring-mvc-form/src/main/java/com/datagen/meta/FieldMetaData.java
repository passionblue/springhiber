package com.datagen.meta;

public class FieldMetaData {

    private String fieldName;
    private String display;
    private String Type;

    public FieldMetaData(String fieldName, String display, String type) {
        super();
        this.fieldName = fieldName;
        this.display = display;
        Type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public String toString() {
        return "FieldMetaData [fieldName=" + fieldName + ", display=" + display + ", Type=" + Type + "]";
    }
    

}

package com.datagen;

public interface FData<T> {

    String      getFieldName();
    void        setFieldName(String fieldName);
    T           getRawFormat();
}

package com.datagen.source.adapter;

public interface SourceFieldDataFormatter<T, V> {
    
    V format(V v);

}

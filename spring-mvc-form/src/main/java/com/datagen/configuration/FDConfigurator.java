package com.datagen.configuration;

public interface FDConfigurator {

    Object getConfig(String key);
    Object getConfig(String key, Object defaultVal);
    
    
    
}

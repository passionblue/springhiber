package com.datagen.configuration.v2;

import java.util.HashMap;
import java.util.Map;

public class ConfigNode {

    private String name;
    private Map<String, ConfigNode> children = new HashMap();
    

    //Default Constructor
    public ConfigNode(String name) {
        this.name = name;
    }

}

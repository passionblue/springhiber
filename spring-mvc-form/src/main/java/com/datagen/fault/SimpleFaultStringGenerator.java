package com.datagen.fault;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleFaultStringGenerator implements FaultGenerater {

    private static Logger m_logger = LoggerFactory.getLogger(SimpleFaultStringGenerator.class);

    private double faultRatioByPercent;
    private String faultString;
    
    @Override
    public boolean faultRaise() {

        double rand = RandomUtils.nextDouble(0.0, 100.0);

        if ( rand < faultRatioByPercent)
            return true;
        else
            return false;
    }

    @Override
    public Object getFaultData() {
        return faultString;
    }

    //Default Constructor
    public SimpleFaultStringGenerator() {
    }

    public double getFaultRatioByPercent() {
        return faultRatioByPercent;
    }

    public void setFaultRatioByPercent(double faultRatioByPercent) {
        this.faultRatioByPercent = faultRatioByPercent;
    }

    public String getFaultString() {
        return faultString;
    }

    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }

    
    
}

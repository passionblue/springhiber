package com.datagen.util;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.RandomDataGenerator;

/*
 * https://en.wikipedia.org/wiki/Normal_distribution
 * https://commons.apache.org/proper/commons-math/userguide/distribution.html
 * 
 */
public class FDataRandomUtil {
    
    private static double[] getDistributionRandom(int size) {
        NormalDistribution nd = new NormalDistribution();
        return nd.sample(size);
    }
    
    private static String getNextHex() {
        
        RandomDataGenerator g = new RandomDataGenerator();
        
        return g.nextHexString(10).toUpperCase();
        
    }
    
    
    private static double[] getDistributionRandom(int size, double mean, double sd) {
        NormalDistribution nd = new NormalDistribution(mean, sd);
        return nd.sample(size);
    }
    
    
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            
            System.out.println(getNextHex());
            
            
        }
    }
    
}

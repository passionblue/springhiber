package com.datagen.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mifmif.common.regex.Generex;
import com.mifmif.common.regex.util.Iterator;

public class FDataRegexUtil {

    /*
     * This method is based on Generex
     * https://github.com/mifmif/Generex
     * 
        <dependency>
          <groupId>com.github.mifmif</groupId>
          <artifactId>generex</artifactId>
          <version>0.0.4</version>
        </dependency>     
     
     */
    public static String randomSSN() {
        Generex generex = new Generex("999-\\d{2}-\\d{4}");
        String randomStr = generex.random();
        return randomStr;
    }

    public static String randomUSPhone() {
        Generex generex = new Generex("\\(\\d{3}\\)\\d{3}-\\d{4}");
        String randomStr = generex.random();
        return randomStr;
    }    
    
    
    
    public static void compileCustomFDataPattern(String pattern) {

        
//        Pattern p = Pattern.compile("(\\$\\{)(.*?)(\\})");
        Pattern p = Pattern.compile("(\\$\\{)(.*?)(\\})");

        Matcher m  = p.matcher(pattern);

        if (m.find()) {
//            System.out.println(m.group(0));
//            System.out.println(m.group(1));
            System.out.println(m.group(2));
//            System.out.println("Found at: "+ m.start()            +        " - " + m.end());
        }        
        
        
        
    }
    
    public static void main(String[] args) throws Exception {
        compileCustomFDataPattern("ss${field}, ${lat}");
        
        String string = "var1/[value1]/asdfasdf, asdfvar2[dffvalue2], var3[value3]";
        Pattern pattern = Pattern.compile("(\\[)(.*?)(\\])");
        Matcher matcher = pattern.matcher(string);

        List<String> listMatches = new ArrayList<String>();

        while(matcher.find())
        {
            listMatches.add(matcher.group(2));
        }

        for(String s : listMatches)
        {
            string = string.replaceAll("\\[" + s + "\\]", "-");
            
            System.out.println(s);
        }       
        
        System.out.println(string);
    }
    
    public static void main3(String[] args) {
        Generex generex = new Generex("\\d{3}-\\d{2}-\\d{4}");
        Set<String> s = new HashSet();
        for (int i = 0; i < 100000; i++) {
            String ssn = FDataRegexUtil.randomSSN();
            String phone = FDataRegexUtil.randomUSPhone();
            System.out.println(phone);
            System.out.println(ssn);
            
            if ( s.contains(ssn))
                System.out.println("XXXX " + ssn);
            else
                s.add(ssn);
        }
    }
    
    public static void main2(String[] args) {
        Generex generex = new Generex("[0-x]([a-c]|[e-g]{1,2})");

        // generate the second String in lexicographical order that match the
        // given Regex.
        String secondString = generex.getMatchedString(2);
        System.out.println(secondString);// it print '0b'

        // Generate all String that matches the given Regex.
        //List<String> matchedStrs = generex.getAllMatchedStrings();

        // Using Generex iterator
        Iterator iterator = generex.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        // it print 0a 0b 0c 0e 0ee 0e 0e 0f 0fe 0f 0f 0g 0ge 0g 0g 1a 1b 1c 1e
        // 1ee
        // 1e 1e 1f 1fe 1f 1f 1g 1ge 1g 1g 2a 2b 2c 2e 2ee 2e 2e 2f 2fe 2f 2f 2g
        // 2ge 2g 2g 3a 3b 3c 3e 3ee 3e 3e 3f 3fe 3f 3f 3g 3ge 3g 3g 1ee

        // Generate random String
        String randomStr = generex.random();
        System.out.println(randomStr);// a random value from the previous String
                                        // list

    }    
}

package com.datagen.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;

public class XmlConfigParameterUtil {
    
    public static <T> T santizeAndConvert(String param, Class<T> clazz) {
        if ( StringUtils.isBlank(param)) return null;
        
        if (clazz == BigDecimal.class) {
            return (T) new BigDecimal(param.trim());
        } else if ( clazz == Integer.class) {
            return (T) Integer.valueOf(param.trim());
        } else if ( clazz == Date.class) {
            return (T) DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(param.trim()).toDate();
        } else if ( clazz == Double.class) {
            return (T) Double.valueOf(param.trim());
        }

        return (T)param.trim();
    }

//    
//    public static String convertToStringFrom(int [] array){
//
//       
//        
//    }
    
    public static int[] convertFrom(String str){
        return convertFrom(str, 0);
    }
    public static int[] convertFrom(String str, int defaultValue){
        
        if ( str == null ) return new int[]{};
        
        String numberStrs[] = str.split(",", -1);
        int[] numbers = new int[numberStrs.length];

        for(int i = 0;i < numberStrs.length;i++)
        {
            if ( StringUtils.isBlank(numberStrs[i].trim()))
                numbers[i] = 0;
            else
                numbers[i] = Integer.parseInt(numberStrs[i].trim());
        }
        return numbers;
    }
    
    public static String[] convertToStringArray(String str, boolean skipBlank) {
        if ( StringUtils.isBlank(str)) 
            return new String[0];
        
        String [] splitted =  str.split(",", -1);
        
        List<String> list = new ArrayList<String> ();
        for (int i = 0; i < splitted.length; i++) {
            if ( skipBlank && StringUtils.isBlank(splitted[i]) ){
                //
            } else {
                list.add(splitted[i].trim());
            }
        }
        return list.toArray(new String[list.size()] );
    }


    
    public static void main(String[] args) throws Exception {
        
        System.out.println(Arrays.toString(convertFrom("1,2,3,4,,5")));
        System.out.println(Arrays.toString(convertFrom(",1,2,3,4,,")));
        System.out.println(Arrays.toString(convertToStringArray(",1,2,3,4,,", true)));
        System.out.println(Arrays.toString(convertToStringArray(",1,2,3,4   ,,", false)));
    }
    
}

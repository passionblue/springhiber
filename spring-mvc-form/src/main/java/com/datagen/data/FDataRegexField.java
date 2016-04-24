package com.datagen.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.datagen.FData;

public class FDataRegexField extends FDataCustomComposite {

    private String dataPattern;
    
    public FDataRegexField(String fieldName, boolean excludeInOutput,  String pattern) {
        super(fieldName, excludeInOutput);
        this.dataPattern = pattern;
    }

    @Override
    public String getRawFormat() {

        Pattern pattern = Pattern.compile("(\\[)(.*?)(\\])");
        Matcher matcher = pattern.matcher(dataPattern);

        List<String> listMatches = new ArrayList<String>();

        String retData = dataPattern;
        while(matcher.find())
        {
            listMatches.add(matcher.group(2));
            String field = matcher.group(2);
            if ( field != null) {
                FData fData = this.dataRow.getByName(field);
                if ( fData != null && fData.getRawFormat() != null) {
                    retData = retData.replaceAll("\\[" + field + "\\]", fData.getRawFormat().toString());
                }
            }           
        }
        
        return retData;
    }

}

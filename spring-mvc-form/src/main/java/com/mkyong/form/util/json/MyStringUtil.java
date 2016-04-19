package com.mkyong.form.util.json;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.SplittableRandom;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class MyStringUtil {


    
    static String readFile(String path, Charset encoding) throws Exception {
        
        byte[] encoded = null;
        try {
            encoded = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(path).toURI()));
        }
        catch (Exception e) {
            encoded = Files.readAllBytes(Paths.get(path));
        
        }
        
        
        
        return new String(encoded, encoding);
    }

}

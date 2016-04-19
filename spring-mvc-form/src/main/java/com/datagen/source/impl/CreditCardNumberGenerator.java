package com.datagen.source.impl;

import org.apache.commons.lang3.RandomStringUtils;

import com.datagen.FData;
import com.datagen.data.FDataString;

public class CreditCardNumberGenerator extends AbstractDataSource {

    private int cardNumberLength = 16;

    @Override
    public FData generateNext() {
        
        
        String cardNumber = RandomStringUtils.random(cardNumberLength - 1, false, true);
        
        long cardNumberLong = Long.parseLong(cardNumber);
        int cd = generateCheckDigit(cardNumberLong);
        
        return new FDataString(getFieldName(), String.valueOf(cardNumberLong) + String.valueOf(cd));

    }

    @Override
    public FData generateNext(Object arg) {
        return generateNext();
    }

    private static int generateCheckDigit(long l) {
        String str = Long.toString(l);
        int[] ints = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            ints[i] = Integer.parseInt(str.substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        if (sum % 10 == 0) {
            return 0;
        }
        else
            return 10 - (sum % 10);
    }

}

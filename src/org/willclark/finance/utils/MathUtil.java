package org.willclark.finance.utils;

public class MathUtil {

    public static int generateRandomNumber(int min, int max) {
    	return min + (int)(Math.random() * ((max - min) + 1));
    }
    
}

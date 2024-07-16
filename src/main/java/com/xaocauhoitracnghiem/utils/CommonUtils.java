package com.xaocauhoitracnghiem.utils;

import java.util.List;

public class CommonUtils {
	public static String numToLetterBySubstr(int i) {
	    String LETTERS = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    if (i > 0 && i <= 25) {
	        return LETTERS.substring(i, i + 1);
	    } else {
	        return "?";
	    }
	}
	public static void shuffle(List lst) {
		
	}
}

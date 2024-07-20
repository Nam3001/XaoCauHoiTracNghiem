package com.xaocauhoitracnghiem.utils;

import java.util.List;
import java.util.Random;

public class CommonUtils {
	public static String numToLetterBySubstr(int i) {
	    String LETTERS = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    if (i > 0 && i <= 25) {
	        return LETTERS.substring(i, i + 1);
	    } else {
	        return "?";
	    }
	}
	public static <T> void shuffle(List<T> lst) {
		Random rand = new Random();
		for (int i = lst.size() - 1; i >= 0; i--) {
			int j = rand.nextInt(i+1);

			T tmp = lst.get(i);
			lst.set(i, lst.get(j));
			lst.set(j, tmp);
		}
	}
}

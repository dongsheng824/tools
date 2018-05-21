package com.guods.tools;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
/**
 * 62进制随机码生成
 * @author guods
 *
 */
public class ShortCodeGenerator {

	private static final long BASE_ID = 1514623000000L;
	private static final char[] BASE_STR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
			'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z' };

	private static final String _str = new String(BASE_STR);

	private static final int len = BASE_STR.length;

	public static StringBuffer toStrBuffer(long num) {
		if (num < len) {
			return new StringBuffer().append(BASE_STR[(int) num]);
		}
		long a = num % len;
		long b = num / len;
		char e = BASE_STR[(int) a];
		if (b >= len) {
			return toStrBuffer(b).append(e);
		} else {
			return new StringBuffer().append(BASE_STR[(int) b]).append(e);
		}
	}

	public static long toNum(String s) {
		int a = s.length() - 1;
		long val = 0;
		for (int i = a; i >= 0; i--) {
			char c = s.charAt(i);
			val += (_str.indexOf(c) * Math.pow(len, a - i));
		}
		return val;
	}

	public static String generate(){
		Random random = ThreadLocalRandom.current();
		double randomNum = random.nextDouble();
		//生成毫秒值
		StringBuffer strBuffer = toStrBuffer(System.currentTimeMillis() - BASE_ID);
		//添加1位随机数
		strBuffer.append(BASE_STR[(int)(randomNum * 62)]);
		return strBuffer.toString();
	}
}

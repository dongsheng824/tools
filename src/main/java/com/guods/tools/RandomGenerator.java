package com.guods.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 生成随机电话号码、日期
 * 
 * @author guods
 *
 */
public class RandomGenerator {

	///////////////////////////////////// 随机生成电话号码
	/**
	 * 随机生成固定号码头的电话号码
	 * 
	 * @param preNum
	 *            号码头
	 */
	public void genRandomPhoneNums(String preNum) {
		for (int i = 0; i < 100; i++) {
			System.out.println(genRandomPhoneNum(preNum));
		}
	}

	public String genRandomPhoneNum(String preNum) {
		Random random = ThreadLocalRandom.current();
		Long num = random.nextLong();
		return preNum + num.toString().substring(1, 9);
	}

	///////////////////////////////////// 随机生成日期
	/**
	 * 随机生成区间范围内的日期
	 * 
	 * @param begin
	 * @param end
	 * @throws ParseException
	 */
	public void genRandomDates(String begin, String end) throws ParseException {
		for (int i = 0; i < 100; i++) {
			System.out.println(genRandomDate(begin, end));
		}
	}

	/**
	 * 随机生成区间范围内的日期对，日期对后面的日期大于前面日期
	 * 
	 * @param begin
	 *            开始日期最小时间
	 * @param end
	 *            开始日期最大时间
	 * @param offset
	 *            相对开始日期的最大偏移
	 * @throws ParseException
	 */
	public void genRandomDatePairs(String begin, String end, Long offset) throws ParseException {
		for (int i = 0; i < 100; i++) {
			String date1 = genRandomDate(begin, end);
			String date2 = genRandomDate(date1, offset);
			System.out.println(date1 + "," + date2);
		}
	}

	public String genRandomDate(String begin, String end) throws ParseException {
		float randomFloat;
		long num;
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = formater.parse(begin);
		Date endDate = formater.parse(end);
		Random random = ThreadLocalRandom.current();
		randomFloat = random.nextFloat();
		num = (long) (randomFloat * (endDate.getTime() - beginDate.getTime()));
		Date randomDate = new Date(beginDate.getTime() + num);
		return formater.format(randomDate);
	}

	public String genRandomDate(String begin, Long offset) throws ParseException {
		float randomFloat;
		long num;
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = formater.parse(begin);
		Date endDate = new Date(beginDate.getTime() + offset * 24 * 60 * 60 * 1000);
		Random random = ThreadLocalRandom.current();
		randomFloat = random.nextFloat();
		num = (long) (randomFloat * (endDate.getTime() - beginDate.getTime()));
		Date randomDate = new Date(beginDate.getTime() + num);
		return formater.format(randomDate);
	}
	/**
	 * 生成随机码
	 * @return
	 */
	public String genRandomCode(){
		char[] seed = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
				'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		Random random = ThreadLocalRandom.current();
		StringBuffer sb = new StringBuffer();
		int k;
		for (int i = 0; i < 4; i++) {
			k = random.nextInt();
			k = k >= 0 ? k : -k;
			sb.append(seed[k%36]);
		}
		return sb.toString();
	}
}

package com.kk.autocode.util;

import java.util.Random;

/**
 * 数字工具类
 *
 */
public class NumberUtils {

	private static final Random rand = new Random();

	/**
	 * 随机产生一个数字,无范围要求 方法描述
	 * 
	 * @return
	 * @创建日期 2016年9月6日
	 */
	public static int randInt() {
		return rand.nextInt();
	}

	/**
	 * 随机一个数字，有范围要求 方法描述
	 * 
	 * @param value
	 * @return
	 * @创建日期 2016年9月6日
	 */
	public static int randInt(int value) {

		if (value == 0) {
			return 1;
		}

		int resp = rand.nextInt(value);

		// 值生成不能为0
		if (resp == 0) {
			return randInt(value);
		}
		return resp;
	}

	/**
	 * 返回字符串的数字 方法描述
	 * 
	 * @return
	 * @创建日期 2016年9月6日
	 */
	public static String randIntStr() {
		return String.valueOf(randInt(Integer.MAX_VALUE));
	}

	/**
	 * 获得当前时间的纳秒数 方法描述
	 * 
	 * @return
	 * @创建日期 2016年9月6日
	 */
	public static String sysTimeLongStr() {
		return String.valueOf(System.nanoTime());
	}
}

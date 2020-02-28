package com.kk.utils;

/**
 * 系统符号获取
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月11日 下午1:04:34
 */
public class SystemSymbol {

	/**
	 * 获取系统换行符
	 */
	public static final String SYTEM_LINE = GetLine();

	/**
	 * 获取系统换行符
	 * 
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static final String GetLine() {

		// 默认换行符
		String line = "\n";

		String lineSeparator = (String) java.security.AccessController
				.doPrivileged(new sun.security.action.GetPropertyAction("line.separator"));

		if (null != lineSeparator) {
			line = lineSeparator;
		} else {
			String value = (String) System.getProperties().get("line.separator");
			if (null != value) {
				line = value;
			}
		}
		return line;
	}

	public static void main(String[] args) {
		System.out.println(GetLine());
	}

}

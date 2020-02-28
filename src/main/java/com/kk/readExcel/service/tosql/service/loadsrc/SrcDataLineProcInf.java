package com.kk.readExcel.service.tosql.service.loadsrc;

import java.util.Map.Entry;

/**
 * src文件处理的接口信息
 * @since 2017年3月19日 下午6:11:58
 * @version 0.0.1
 * @author liujun
 */
public interface SrcDataLineProcInf<K,V> {
	
	/**
	 * 进行数据转换
	 * @param line 行信息
	 * @return 结果信息
	 */
	public Entry<K, V> parseDataValue(String line);

}

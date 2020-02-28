package com.kk.readExcel.service.tosql.service.loadsrc;

import java.util.Map;

/**
 * 其他数据源信息加载
 * 
 * @since 2017年3月19日 下午6:03:43
 * @version 0.0.1
 * @author liujun
 */
public interface SrcDataLoaderInf<K, V> {

	/**
	 * 加载数据源信息
	 * 
	 * @param name
	 *            名称信息
	 * @return 数据信息
	 */
	public Map<K, V> loaderDataSrcProcess(String name);

}

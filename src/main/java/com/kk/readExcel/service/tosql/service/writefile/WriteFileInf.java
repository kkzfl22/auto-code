package com.kk.readExcel.service.tosql.service.writefile;

import java.util.List;

/**
 * 将数据写入文件的接口
 * 
 * @since 2017年3月19日 下午4:34:38
 * @version 0.0.1
 * @author liujun
 */
public interface WriteFileInf {

	/**
	 * 将数据写入到文件
	 * 
	 * @param data
	 *            数据信息
	 * @param spitFlag
	 *            是否分文件写入的标识
	 *            @param fileName 文件名
	 * @return
	 */
	public boolean wirteSql(List<List<String>> data, boolean spitFlag, String fileName);

}

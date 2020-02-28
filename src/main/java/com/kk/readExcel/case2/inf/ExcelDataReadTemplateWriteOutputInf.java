package com.kk.readExcel.case2.inf;

/**
 * 进行读取excel的模板，然后将数据写入
 * 
 * 将java对象转化为excel中的行记录
 * 
 * @author liujun
 * @date 2016年4月1日
 * @verion 0.0.1
 */
public interface ExcelDataReadTemplateWriteOutputInf<T> extends ExcelDataReadInputInf {

	/**
	 * 获取模板文件路径
	 * 
	 * @return 模板文件所在路径
	 */
	public String getTemplate();

}

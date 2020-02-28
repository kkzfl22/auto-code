package com.kk.readExcel.case2.inf;

import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * 进行excel的数据转换接口
 * 
 * 将java对象转化为excel中的行记录
 * 
 * @author liujun
 * @date 2016年4月1日
 * @verion 0.0.1
 */
public interface ExcelDataWriteOutputInf<T> {

	/**
	 * 将javaBean中的信息写入至行记录中
	 * 
	 * @param beaninfo
	 *            实体信息
	 * @return sheet 页对象信息，用来创建行
	 */
	public void excelRowWrite(T beaninfo, HSSFSheet sheet);

	/**
	 * excel设置表头信息
	 * 
	 * @param sheet
	 *            sheet页对象信息，可能跨多行设置
	 * @param row
	 *            excel行信息
	 */
	public void excelColumnWrite(HSSFSheet sheet);

}

package com.kk.readExcel.case2.inf;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;

import com.kk.readExcel.case2.bean.comm.ExcelReadBean;

/**
 * 进行excel的数据转换接口
 * 
 * 将excel中的行记录转换为java中的bean对象
 * 
 * @author liujun
 * @date 2016年4月1日
 * @verion 0.0.1
 */
public interface ExcelDataReadInputInf {

	/**
	 * 进行不必要的数据跳过
	 * 
	 * @param iterRow
	 *            ，进行迭代器对应行数的跳过
	 * @return
	 */
	public void jumpRownum(Iterator<Row> iterRow);

	/**
	 * 获取进行javabean相关联的信息
	 * 
	 * @param iterRow
	 *            迭代器对象
	 * @return 生成javaBean所城要的对象
	 */
	public ExcelReadBean getReadBean(Iterator<Row> iterRow);

}

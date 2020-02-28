package com.kk.readExcel.common.inf;

import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

import com.kk.readExcel.service.tosql.bean.ColumnMsgInfo;
import com.kk.readExcel.service.tosql.bean.DataParseToSQLBean;

/**
 * 进行excel的数据转换接口
 * 
 * 将excel中的行记录转换为java中的bean对象
 * 
 * @author liujun
 * @date 2016年4月1日
 * @verion 0.0.1
 */
public interface ExcelDataParseInputInf {

	/**
	 * 将excel数据中的每行转换为javabean对象的一个实体对象
	 * 
	 * @param excelRow
	 *            excel行记录
	 * @return bean实体对象
	 */
	public DataParseToSQLBean parseBean(Row excelRow);

	/**
	 * 获取列信息
	 * 
	 * @param excelRow
	 * @return
	 */
	public Map<Integer,ColumnMsgInfo> getColumnMsg(Row excelRow);

	/**
	 * 读取表名信息
	 * 
	 * @return
	 */
	public String getTableName(Row excelRow);
}

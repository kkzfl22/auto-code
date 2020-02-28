package com.kk.excelToJavaSet.service.inf;

import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

import com.kk.excelToJavaSet.bean.JavaSetColumnMsgInfo;
import com.kk.excelToJavaSet.bean.JavaSetDataParseToSQLBean;
import com.kk.readExcel.service.tosql.bean.ColumnMsgInfo;

/**
 * 进行excel的数据转换接口
 * 
 * 将excel中的行记录转换为java中的bean对象
 * 
 * @author liujun
 * @date 2016年4月1日
 * @verion 0.0.1
 */
public interface JavaSetExcelDataParseInputInf {

	/**
	 * 将excel数据中的每行转换为javabean对象的一个实体对象
	 * 
	 * @param excelRow
	 *            excel行记录
	 * @return bean实体对象
	 */
	public JavaSetDataParseToSQLBean parseBean(Row excelRow);

	/**
	 * 获取列信息
	 * 
	 * @param excelRow
	 * @return
	 */
	public Map<Integer, JavaSetColumnMsgInfo> getColumnMsg(Row excelRow);

	/**
	 * 设置列数据名称信息
	 * 
	 * @param excelRow
	 *            数据行信息
	 * @param columnMap
	 *            列map
	 * @return 结果信息
	 */
	public Map<Integer, JavaSetColumnMsgInfo> setColumnMsgName(Row excelRow,
			Map<Integer, JavaSetColumnMsgInfo> columnMap);

	/**
	 * 设置列数据类型信息
	 * 
	 * @param excelRow
	 *            数据行信息
	 * @param columnMap
	 *            列map
	 * @return 结果信息
	 */
	public Map<Integer, JavaSetColumnMsgInfo> setColumnMsgType(Row excelRow,
			Map<Integer, JavaSetColumnMsgInfo> columnMap);

	/**
	 * 读取表名信息
	 * 
	 * @return
	 */
	public String getTableName(Row excelRow);
}

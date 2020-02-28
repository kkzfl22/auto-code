package com.kk.readExcel.case2.bean.comm;

import java.util.Map;

/**
 * excel 进行读取的bean的信息
 * 
 * @since 2018年5月14日 下午4:53:54
 * @version 0.0.1
 * @author liujun
 */
public class ExcelReadBean {

	/**
	 * 定义的列的信息
	 */
	private Map<Integer, ColumnToJavaBeanMsg> columnMap;

	/**
	 * 列所对应的java的实体的bean
	 */
	private Class<?> javaClassType;

	public Map<Integer, ColumnToJavaBeanMsg> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<Integer, ColumnToJavaBeanMsg> columnMap) {
		this.columnMap = columnMap;
	}

	public Class<?> getJavaClassType() {
		return javaClassType;
	}

	public void setJavaClassType(Class<?> javaClassType) {
		this.javaClassType = javaClassType;
	}

}

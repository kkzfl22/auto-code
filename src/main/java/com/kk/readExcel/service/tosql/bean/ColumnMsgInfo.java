package com.kk.readExcel.service.tosql.bean;

import org.apache.poi.ss.usermodel.CellType;

/**
 * 列名信息的bean
 * 
 * @since 2017年3月19日 下午2:40:06
 * @version 0.0.1
 * @author liujun
 */
public class ColumnMsgInfo {

	/**
	 * 列名
	 */
	private String columnName;

	/**
	 * 列索引信息
	 */
	private int index;

	/**
	 * 数据类型信息
	 */
	private int type;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColumnMsgInfo [columnName=");
		builder.append(columnName);
		builder.append(", index=");
		builder.append(index);
		builder.append("]");
		return builder.toString();
	}

}

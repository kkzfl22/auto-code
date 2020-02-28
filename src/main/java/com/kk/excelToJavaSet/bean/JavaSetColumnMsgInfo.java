package com.kk.excelToJavaSet.bean;

/**
 * 列名信息的bean
 * 
 * @since 2017年3月19日 下午2:40:06
 * @version 0.0.1
 * @author liujun
 */
public class JavaSetColumnMsgInfo {

	/**
	 * 列名
	 */
	private String columnName;

	/**
	 * 列描述信息
	 */
	private String columnMsg;

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

	public String getColumnMsg() {
		return columnMsg;
	}

	public void setColumnMsg(String columnMsg) {
		this.columnMsg = columnMsg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColumnMsgInfo [columnName=");
		builder.append(columnName);
		builder.append(", columnMsg=");
		builder.append(columnMsg);
		builder.append(", index=");
		builder.append(index);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}

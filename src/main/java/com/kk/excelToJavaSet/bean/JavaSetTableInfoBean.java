package com.kk.excelToJavaSet.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库文件相关的bean信息
 * 
 * @since 2017年3月19日 下午2:58:54
 * @version 0.0.1
 * @author liujun
 */
public class JavaSetTableInfoBean {

	/**
	 * 数据库表的名称
	 */
	private String tableName;

	/**
	 * 数据列相关的信息
	 */
	private Map<Integer, JavaSetColumnMsgInfo> column;

	/**
	 * 数据信息
	 */
	private List<JavaSetDataParseToSQLBean> value = new ArrayList<>();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<Integer, JavaSetColumnMsgInfo> getColumn() {
		return column;
	}

	public void setColumn(Map<Integer, JavaSetColumnMsgInfo> column) {
		this.column = column;
	}

	public List<JavaSetDataParseToSQLBean> getValue() {
		return value;
	}

	public void addValue(JavaSetDataParseToSQLBean value) {
		this.value.add(value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TableInfoDTO [tableName=");
		builder.append(tableName);
		builder.append(", column=");
		builder.append(column);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

}

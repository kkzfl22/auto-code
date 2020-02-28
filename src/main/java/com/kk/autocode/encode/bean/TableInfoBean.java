package com.kk.autocode.encode.bean;

/**
 * 表名信息
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月8日 下午5:03:54
 */
public class TableInfoBean {
	
	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 表描述信息
	 */
	private String tableComment;
	

	public TableInfoBean(String tableName, String tableComment) {
		super();
		this.tableName = tableName;
		this.tableComment = tableComment;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TableInfoBean [tableName=");
		builder.append(tableName);
		builder.append(", tableComment=");
		builder.append(tableComment);
		builder.append("]");
		return builder.toString();
	}
	

}

package com.kk.docprocess.docCommon.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 表的相关信息
 *
 * @version 0.0.1
 * @author liujun
 */
public class TableBean {

  /** 表名 */
  private String tableName;

  /** 表的描述 */
  private String tableMsg;

  /** 主键 */
  private String primaryKey;

  /** 列信息集合 */
  private List<TableColumnBean> columnList = new ArrayList<>();

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getTableMsg() {
    return tableMsg;
  }

  public void setTableMsg(String tableMsg) {
    this.tableMsg = tableMsg;
  }

  public String getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(String primaryKey) {
    this.primaryKey = primaryKey;
  }

  public List<TableColumnBean> getColumnList() {
    return columnList;
  }

  public void setColumnList(List<TableColumnBean> columnList) {
    this.columnList = columnList;
  }

  public void addColumnList(TableColumnBean column) {
    this.columnList.add(column);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("TableBean [tableName=");
    builder.append(tableName);
    builder.append(", tableMsg=");
    builder.append(tableMsg);
    builder.append(", primaryKey=");
    builder.append(primaryKey);
    builder.append(", columnList=");
    builder.append(columnList);
    builder.append("]");
    return builder.toString();
  }
}

package com.kk.docprocess.docCommon.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 表的相关信息
* 源文件名：TableBean.java
* 文件版本：1.0.0
* 创建作者：liujun
* 创建日期：2016年10月26日
* 修改作者：liujun
* 修改日期：2016年10月26日
* 文件描述：TODO
* 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
*/
public class TableBean {

    /**
     * 表名
    * @字段说明 tableName
    */
    private String tableName;

    /**
     * 表的描述
    * @字段说明 tableMsg
    */
    private String tableMsg;

    /**
     * 主键
    * @字段说明 primaryKey
    */
    private String primaryKey;

    /**
     * 列信息集合
    * @字段说明 columnList
    */
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

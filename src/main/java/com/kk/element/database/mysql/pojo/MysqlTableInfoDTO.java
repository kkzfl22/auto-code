package com.kk.element.database.mysql.pojo;

import com.kk.element.pojo.DataResultParseInf;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mysql的表信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/13
 */
public class MysqlTableInfoDTO extends TableInfoDTO implements DataResultParseInf {

  @Override
  public TableInfoDTO parse(ResultSet rs) throws SQLException {

    String tableName = rs.getString("table_name");
    String columnName = rs.getString("table_comment");

    tableName = tableName.toLowerCase();

    int spitIndex = columnName.indexOf(";");

    if (spitIndex != -1) {
      columnName = columnName.substring(0, spitIndex);
    }

    TableInfoDTO bean = new TableInfoDTO(tableName, columnName);

    return bean;
  }
}

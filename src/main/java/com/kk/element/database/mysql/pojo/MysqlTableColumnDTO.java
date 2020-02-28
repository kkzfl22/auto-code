package com.kk.element.database.mysql.pojo;

import com.kk.element.pojo.DataResultParseInf;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mysql表列信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/13
 */
public class MysqlTableColumnDTO extends TableColumnDTO implements DataResultParseInf {

  @Override
  public TableColumnDTO parse(ResultSet rs) throws SQLException {

    String tableName = rs.getString("table_name");
    String columnName = rs.getString("column_name");
    String columnMsg = rs.getString("COLUMN_COMMENT");
    String dataType = rs.getString("DATA_TYPE");
    String primayKey = rs.getString("COLUMN_KEY");
    String extra = rs.getString("EXTRA");
    String precision = rs.getString("NUMERIC_PRECISION");
    String scale = rs.getString("NUMERIC_SCALE");
    String charMax = rs.getString("CHARACTER_MAXIMUM_LENGTH");
    String octLength = rs.getString("CHARACTER_OCTET_LENGTH");

    columnName = columnName.toLowerCase();

    boolean priKey = "PRI".equals(primayKey) ? true : false;

    TableColumnDTO bean = new TableColumnDTO(columnName, columnMsg, dataType, priKey);
    if (null != precision) {
      bean.setDataLength(Integer.parseInt(precision));
    }

    if (null != octLength) {
      bean.setDataLength(Integer.parseInt(octLength));
    }

    if (null != charMax) {
      bean.setDataLength(Integer.parseInt(charMax));
    }

    if (null != scale) {
      bean.setDataScale(Integer.parseInt(scale));
    }
    bean.setAutoIncrement("auto_increment".equals(extra) ? true : false);
    bean.setTableName(tableName);

    return bean;
  }
}

package com.kk.autocode.encode.bean;

import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.pojo.DataResultParseInf;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * oracle表列信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/13
 */
public class OracleTableColumnDTO extends TableColumnDTO implements DataResultParseInf {

  @Override
  public TableColumnDTO parse(ResultSet rs) throws SQLException {

    String tableName = rs.getString("TABLE_NAME");
    String columnName = rs.getString("COLUMN_NAME");
    String dataType = rs.getString("DATA_TYPE");
    String columnMsg = rs.getString("COMMENTS");
    int dataLength = rs.getInt("DATA_LENGTH");
    int dataScale = rs.getInt("DATA_SCALE");
    String primayKey = rs.getString("primaryName");
    columnName = columnName.toLowerCase();

    boolean priKey = null != primayKey && "".equals(primayKey) ? true : false;

    TableColumnDTO tableBean = new TableColumnDTO(columnName, columnMsg, dataType, priKey);

    tableBean.setDataLength(dataLength);
    tableBean.setDataScale(dataScale);
    tableBean.setTableName(tableName);

    return tableBean;
  }
}

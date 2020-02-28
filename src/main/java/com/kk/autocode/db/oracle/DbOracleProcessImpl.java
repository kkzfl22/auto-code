package com.kk.autocode.db.oracle;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.kk.autocode.db.DbProcessInf;
import com.kk.autocode.encode.bean.OracleTableColumnDTO;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;
import com.kk.autocode.util.DateUtils;
import com.kk.autocode.util.DbUtils;
import com.kk.autocode.util.NumberUtils;
import com.kk.autocode.util.StringUtils;
import com.kk.element.common.properties.PropertiesUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.Map.Entry;

/**
 * oracle 的实现
 *
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月8日 下午5:23:28
 */
public class DbOracleProcessImpl implements DbProcessInf {

  @Override
  public Map<String, List<TableColumnDTO>> getTableColumnByBean(String tableSpace)
      throws Exception {

    Map<String, List<TableColumnDTO>> map = new HashMap<>();

    StringBuilder sql = new StringBuilder();

    sql.append("select col.TABLE_NAME,");
    sql.append("       col.COLUMN_NAME,");
    sql.append("       col.DATA_TYPE,");
    sql.append("       col.DATA_LENGTH,");
    sql.append("       col.DATA_SCALE,");
    sql.append("       comments.COMMENTS,");
    sql.append("       prim.COLUMN_NAME as primaryName");
    sql.append("  from user_tab_columns col");
    sql.append("  left join user_col_comments comments");
    sql.append("    on comments.TABLE_NAME = col.TABLE_NAME");
    sql.append("   and comments.COLUMN_NAME = col.COLUMN_NAME");
    sql.append("  left join (select cu.TABLE_NAME, cu.COLUMN_NAME");
    sql.append("               from user_cons_columns cu, user_constraints au");
    sql.append("              where cu.constraint_name = au.constraint_name");
    sql.append("                and au.constraint_type = 'P') prim");
    sql.append("    on prim.TABLE_NAME = col.TABLE_NAME");
    sql.append("   and prim.COLUMN_NAME = col.COLUMN_NAME");
    sql.append(" order by col.TABLE_NAME");

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      // 初始化数据源信息
      DataSource dataSource =
          DruidDataSourceFactory.createDataSource(PropertiesUtils.getInstance().getProperties());

      conn = dataSource.getConnection();
      pstmt = conn.prepareStatement(sql.toString());

      rs = pstmt.executeQuery();

      OracleTableColumnDTO parseBean = new OracleTableColumnDTO();

      List<TableColumnDTO> tempList = null;
      TableColumnDTO tableColumnInfo = null;
      while (rs.next()) {

        tableColumnInfo = parseBean.parse(rs);

        if (!map.containsKey(tableColumnInfo.getTableName())) {
          tempList = new ArrayList<>();
          map.put(tableColumnInfo.getTableName(), tempList);
        }

        tempList.add(tableColumnInfo);
      }
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstmt);
      DbUtils.close(conn);
    }

    return map;
  }

  @Override
  public String createValue(TableColumnDTO bean) {
    String type = bean.getDataType();

    String randValue = null;
    switch (type) {
      case "NUMBER":
        randValue = String.valueOf(NumberUtils.randInt(10));
        break;
      case "varchar":
        // 随机生成一个字符串,5位以内
        randValue = StringUtils.RandomString(NumberUtils.randInt(5));
        break;
      case "timestamp":
        randValue = DateUtils.getStrCurrtTimeSSS();
        break;
      default:
        // 默认生成值
        randValue = "880";
        break;
    }

    return randValue;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public Map<String, List<TableColumnDTO>> getTableColumnInfoByMap(String tableSpace)
      throws Exception {

    // 取得表的相关信息
    Map<String, List<TableColumnDTO>> result = this.getTableColumnByBean(tableSpace);

    if (null != result && !result.isEmpty()) {
      for (Iterator iter = result.entrySet().iterator(); iter.hasNext(); ) {
        Entry<String, List<TableColumnDTO>> rs =
            (Entry<String, List<TableColumnDTO>>) iter.next();

        List<TableColumnDTO> list = rs.getValue();

        for (TableColumnDTO tableBean : list) {

          String dataType = tableBean.getDataType();
          // 数字类型
          if ("NUMBER".equals(dataType)) {
            dataType = "NUMERIC";
          }
          // 时间格式类型
          else if ("TIMESTAMP(6)".equals(dataType)) {
            dataType = "TIMESTAMP";
          }
          // 其余均由字符类型
          else {
            dataType = "VARCHAR";
          }

          tableBean.setDataType(dataType);
        }
      }
    }

    return result;
  }

  @Override
  public String parseJavaType(TableColumnDTO bean) {

    String javaType = null;

    switch (bean.getDataType()) {
      case "NUMBER":
        // 当前不带小数
        if (bean.getDataScale() == 0) {
          if (bean.getDataLength() <= 9) {
            javaType = "int";
          } else if (bean.getDataLength() > 9 && bean.getDataLength() < 18) {
            javaType = "long";
          }
        }
        // 如果当前是浮点数
        else if (bean.getDataScale() > 0) {
          javaType = "double";
        }
        break;
      case "VARCHAR":
        javaType = "String";
        break;
      case "VARCHAR2":
        javaType = "String";
        break;
      case "timestamp":
        javaType = "String";
        break;
      default:
        javaType = "String";
        break;
    }

    return javaType;
  }

  @Override
  public String getJavaDefValue(TableColumnDTO bean) {
    String type = bean.getDataType();

    String defValue = null;
    switch (type) {
      case "NUMBER":
        // 当前不带小数
        if (bean.getDataScale() == 0) {
          if (bean.getDataLength() <= 9) {
            defValue = "= -1";
          } else if (bean.getDataLength() > 9 && bean.getDataLength() < 18) {
            defValue = " = -1l";
          }
        }
        break;
      case "NUMERIC":
        defValue = "= -1";
        break;
      default:
        break;
    }

    return defValue;
  }

  @Override
  public Map<String, TableInfoDTO> getTableInfo(String tableSpace) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
}

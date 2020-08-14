package com.kk.autocode.db.mysql;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.kk.autocode.db.DbProcessInf;
import com.kk.autocode.encode.constant.JavaDataTypeGenerateValueEnum;
import com.kk.autocode.encode.constant.MysqlDataTypeEnum;
import com.kk.autocode.encode.constant.StandardTypeEnum;
import com.kk.docprocess.doctoadapterdoc.process.impl.TypeProcess;
import com.kk.docprocess.doctoadapterdoc.process.impl.ValueProcess;
import com.kk.element.database.mysql.pojo.MysqlTableColumnDTO;
import com.kk.element.database.mysql.pojo.MysqlTableInfoDTO;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;
import com.kk.autocode.util.DateUtils;
import com.kk.autocode.util.DbUtils;
import com.kk.autocode.util.NumberUtils;
import com.kk.autocode.util.StringUtils;
import com.kk.docprocess.doctoadapterdoc.process.impl.NameProcess;
import com.kk.element.common.properties.PropertiesUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

/**
 * mysql操作的接口的实现
 *
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月8日 下午5:12:54
 */
public class DbMysqlProcessImpl implements DbProcessInf {

  @Override
  public Map<String, List<TableColumnDTO>> getTableColumnByBean(String tableSpace)
      throws Exception {
    Map<String, List<TableColumnDTO>> map = new HashMap<>();

    // 查询所有的表
    String sql =
        "select table_name,column_name,data_type,COLUMN_COMMENT,COLUMN_KEY,EXTRA,"
            + "NUMERIC_PRECISION,NUMERIC_SCALE,CHARACTER_MAXIMUM_LENGTH,CHARACTER_OCTET_LENGTH,"
            + "IS_NULLABLE,COLUMN_DEFAULT "
            + "from information_schema.COLUMNS where table_schema = '"
            + tableSpace
            + "' order by table_name";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      // 初始化数据源信息
      DataSource dataSource =
          DruidDataSourceFactory.createDataSource(PropertiesUtils.getInstance().getProperties());

      conn = dataSource.getConnection();
      pstmt = conn.prepareStatement(sql);

      rs = pstmt.executeQuery();

      MysqlTableColumnDTO parseBean = new MysqlTableColumnDTO();
      TableColumnDTO tableColumnInfo = null;

      List<TableColumnDTO> tempList = null;
      while (rs.next()) {
        tableColumnInfo = parseBean.parse(rs);

        tempList = map.get(tableColumnInfo.getTableName());

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

    // 1,得到标准的类型
    StandardTypeEnum standType = MysqlDataTypeEnum.databaseToStandKey(bean.getDataType());

    String outGener = JavaDataTypeGenerateValueEnum.getGenerateFun(standType, bean.getDataLength());

    return outGener;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public Map<String, List<TableColumnDTO>> getTableColumnInfoByMap(String tableSpace)
      throws Exception {

    // 取得表的相关信息
    Map<String, List<TableColumnDTO>> result = this.getTableColumnByBean(tableSpace);

    if (null != result && !result.isEmpty()) {
      for (Iterator iter = result.entrySet().iterator(); iter.hasNext(); ) {
        Entry<String, List<TableColumnDTO>> rs = (Entry<String, List<TableColumnDTO>>) iter.next();

        List<TableColumnDTO> list = rs.getValue();

        for (TableColumnDTO tableBean : list) {

          String dataType = tableBean.getDataType();

          String outType =
              TypeProcess.INSTANCE.dbTypeParseMyBatis(dataType, tableBean.getDataLength());

          tableBean.setDataType(outType);
        }
      }
    }

    return result;
  }

  @Override
  public String parseJavaType(TableColumnDTO bean) {
    return TypeProcess.INSTANCE.getJavaType(bean.getDataType());
  }

  @Override
  public String getJavaDefValue(TableColumnDTO bean) {
    return ValueProcess.INSTANCE.getJavaDefValue(bean.getDataType());
  }

  @Override
  public Map<String, TableInfoDTO> getTableInfo(String tableSpace) throws Exception {
    Map<String, TableInfoDTO> map = new HashMap<>();

    // 查询所有的表
    String sql =
        "select table_name,table_comment from information_schema.tables  where table_schema = '"
            + tableSpace
            + "'";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      // 初始化数据源信息
      DataSource dataSource =
          DruidDataSourceFactory.createDataSource(PropertiesUtils.getInstance().getProperties());

      conn = dataSource.getConnection();
      pstmt = conn.prepareStatement(sql);

      rs = pstmt.executeQuery();

      MysqlTableInfoDTO tempList = new MysqlTableInfoDTO();
      TableInfoDTO tableinfo;
      while (rs.next()) {
        tableinfo = tempList.parse(rs);

        map.put(tableinfo.getTableName(), tableinfo);
      }
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstmt);
      DbUtils.close(conn);
    }

    return map;
  }
}

package com.kk.element.database.mysql.DAO;

import com.kk.element.database.mysql.pojo.MysqlTableColumnDTO;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/13
 */
public class MySqlTableColumnDaoImpl {

  /** 实例对象 */
  public static final MySqlTableColumnDaoImpl INSTANCE = new MySqlTableColumnDaoImpl();

  /** 日志 */
  private Logger logger = LoggerFactory.getLogger(MySqlTableColumnDaoImpl.class);

  /**
   * 查询所有的表的列信息
   *
   * @return 表列信息
   */
  public List<TableColumnDTO> selectTableColumn(String databaseName) {

    StringBuilder sql = new StringBuilder();
    sql.append("select table_name,column_name,data_type,COLUMN_COMMENT,COLUMN_KEY,EXTRA,");
    sql.append("NUMERIC_PRECISION,NUMERIC_SCALE,CHARACTER_MAXIMUM_LENGTH,CHARACTER_OCTET_LENGTH  ");
    sql.append("from information_schema.COLUMNS where table_schema = '");
    sql.append(databaseName);
    sql.append("' order by table_name");

    try {
      return MysqlJdbcProcDao.INSTANCE.select(sql.toString(), new MysqlTableColumnDTO());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("MySqlTableColumnDaoImpl selectTableInfo Exception", e);
    }

    return null;
  }
}

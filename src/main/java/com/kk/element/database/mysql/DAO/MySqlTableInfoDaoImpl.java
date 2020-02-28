package com.kk.element.database.mysql.DAO;

import com.kk.element.database.mysql.pojo.MysqlTableInfoDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/13
 */
public class MySqlTableInfoDaoImpl {

  /** 实例对象 */
  public static final MySqlTableInfoDaoImpl INSTANCE = new MySqlTableInfoDaoImpl();

  /** 日志 */
  private Logger logger = LoggerFactory.getLogger(MySqlTableInfoDaoImpl.class);

  /**
   * 查询所有的表的列信息
   *
   * @return 表列信息
   */
  public List<TableInfoDTO> selectTableInfo(String databaseName) {

    StringBuilder sql = new StringBuilder();

    sql.append(
        "select table_name,table_comment from information_schema.tables  where table_schema = '");
    sql.append(databaseName);
    sql.append("'");

    try {
      return MysqlJdbcProcDao.INSTANCE.select(sql.toString(), new MysqlTableInfoDTO());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("MySqlTableColumnDaoImpl selectTableInfo Exception", e);
    }

    return null;
  }
}

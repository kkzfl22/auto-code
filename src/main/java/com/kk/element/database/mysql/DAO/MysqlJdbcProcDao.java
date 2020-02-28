package com.kk.element.database.mysql.DAO;

import com.kk.element.common.constant.PropertyEnum;
import com.kk.element.common.properties.PropertiesUtils;
import com.kk.element.pojo.DataResultParseInf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 进行mysql的数据库表的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/06
 */
public class MysqlJdbcProcDao {

  public static final MysqlJdbcProcDao INSTANCE = new MysqlJdbcProcDao();

  private Logger logger = LoggerFactory.getLogger(MysqlJdbcProcDao.class);

  /**
   * data
   *
   * @param sql
   * @param fun
   * @param <T>
   * @return
   */
  public <T> List<T> select(String sql, DataResultParseInf fun) throws Exception {

    Statement stmt = null;
    ResultSet resultSet = null;
    Connection connection = null;
    try {
      connection = this.getConnection();
      stmt = connection.createStatement();
      resultSet = stmt.executeQuery(sql);

      if (null != resultSet) {
        List<T> result = new ArrayList<>();

        while (resultSet.next()) {
          result.add(fun.parse(resultSet));
        }

        return result;
      }

    } catch (Exception e) {
      logger.error("MysqlJdbcProcDao Exception", e);
      throw e;
    } finally {
      this.close(resultSet);
      this.close(stmt);
      this.close(connection);
    }

    return null;
  }

  public void close(AutoCloseable close) {
    if (null != close) {
      try {
        close.close();
      } catch (Exception e) {
        logger.error("MysqlJdbcProcDao close Exception", e);
      }
    }
  }

  /**
   * 获取连接对象信息
   *
   * @return 结果数据
   * @throws Exception 异常信息
   */
  public Connection getConnection() throws Exception {

    String url = PropertiesUtils.getInstance().getValue(PropertyEnum.DB_MYSQL_CONN_URL);
    String user = PropertiesUtils.getInstance().getValue(PropertyEnum.DB_MYSQL_CONN_USENAME);
    String password = PropertiesUtils.getInstance().getValue(PropertyEnum.DB_TYPE_CONN_PASSWORD);
    String classString = PropertiesUtils.getInstance().getValue(PropertyEnum.DB_MYSQL_CONN_DRIVER);


    Class.forName(classString);
    Connection connection = DriverManager.getConnection(url, user, password);

    return connection;
  }
}

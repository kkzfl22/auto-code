package com.kk.element.common.constant;

/**
 * application property file key
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/18
 */
public enum PropertyEnum {

  /** mysql的驱动信息 */
  DB_MYSQL_CONN_DRIVER("driverClassName"),

  /** mysql的连接字符串 */
  DB_MYSQL_CONN_URL("url"),

  /** 数据库操作的用户名 */
  DB_MYSQL_CONN_USENAME("username"),

  /** 数据库操作的密码 */
  DB_TYPE_CONN_PASSWORD("password"),

  /** 当前操作的数据库类型信息, db type 1,oracle,2 mysql */
  DB_TYPE("dbtype"),
  ;

  private String key;

  PropertyEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}

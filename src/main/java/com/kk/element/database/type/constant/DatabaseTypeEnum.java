package com.kk.element.database.type.constant;

/**
 * database type
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/09/13
 */
public enum DatabaseTypeEnum {

  /** database mysql */
  DATABASE_MYSQL("MYSQL"),

  /** database oracle */
  DATABASE_ORACLE("ORACLE"),
  ;

  private String type;

  DatabaseTypeEnum(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DatabaseTypeEnum{");
    sb.append("type='").append(type).append('\'');
    sb.append('}');
    return sb.toString();
  }

  /**
   * get proerties the type
   *
   * @param propertisName proties name
   * @return type info
   */
  public static DatabaseTypeEnum getPropertiesDbType(String propertisName) {
    if (propertisName != null) {
      String name =
          propertisName.substring(propertisName.indexOf("_") + 1, propertisName.lastIndexOf("."));

      for (DatabaseTypeEnum types : values()) {
        if (types.type.equalsIgnoreCase(name)) {
          return types;
        }
      }
    }
    return null;
  }

  /**
   * get proerties the type
   *
   * @param name database name
   * @return type info
   */
  public static DatabaseTypeEnum getDbType(String name) {
    if (name != null) {

      for (DatabaseTypeEnum types : values()) {
        if (types.type.equalsIgnoreCase(name)) {
          return types;
        }
      }
    }
    return null;
  }
}

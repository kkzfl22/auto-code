package com.kk.autocode.encode.constant;

/**
 * java的数据类型枚举信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/29
 */
public enum MysqlDataTypeEnum {

  /** TINYINT 1 字节 (-128，127) */
  TINYINT(StandardTypeEnum.TINYINT, "TINYINT"),

  /** SMALLINT 2 字节 (-32 768，32 767) */
  SMALLINT(StandardTypeEnum.SMALLINT, "SMALLINT"),

  /** MEDIUMINT 3 字节 (-8 388 608，8 388 607) */
  MEDIUMINT(StandardTypeEnum.MEDIUMINT, "MEDIUMINT"),

  /** INT或INTEGER 4 字节 (-2 147 483 648，2 147 483 647) */
  INT(StandardTypeEnum.INTEGER, "INT"),

  /** INT或INTEGER 4 字节 (-2 147 483 648，2 147 483 647) */
  INTEGER(StandardTypeEnum.INTEGER, "INTEGER"),

  /** BIGINT 8 字节 (-9,223,372,036,854,775,808，9 223 372 036 854 775 807) */
  BIGINT(StandardTypeEnum.BIGINT, "BIGINT"),

  /**
   * FLOAT 4 字节 (-3.402 823 466 E+38，-1.175 494 351 E-38)，0，(1.175 494 351 E-38，3.402 823 466 351
   * E+38)
   */
  FLOAT(StandardTypeEnum.FLOAT, "FLOAT"),

  /**
   * DOUBLE 8 字节 (-1.797 693 134 862 315 7 E+308，-2.225 073 858 507 201 4 E-308)，0，(2.225 073 858
   * 507 201 4 E-308，1.797 693 134 862 315 7 E+308)
   */
  DOUBLE(StandardTypeEnum.DOUBLE, "DOUBLE"),

  /** DECIMAL 对DECIMAL(M,D) ，如果M>D，为M+2否则为D+2 依赖于M和D的值 */
  DECIMAL(StandardTypeEnum.DECIMAL, "DECIMAL"),

  /** DATE 3 1000-01-01/9999-12-31 YYYY-MM-DD 日期值 */
  DATE(StandardTypeEnum.DATE, "DATE"),

  /** TIME 3 '-838:59:59'/'838:59:59' HH:MM:SS 时间值或持续时间 */
  TIME(StandardTypeEnum.TIME, "TIME"),

  /** YEAR 1 1901/2155 YYYY 年份值 */
  YEAR(StandardTypeEnum.YEAR, "YEAR"),

  /** DATETIME 8 1000-01-01 00:00:00/9999-12-31 23:59:59 YYYY-MM-DD HH:MM:SS 混合日期和时间值 */
  DATETIME(StandardTypeEnum.DATETIME, "DATETIME"),

  /**
   * TIMESTAMP 4 1970-01-01 00:00:00/2038 结束时间是第 2147483647 秒，北京时间 2038-1-19 11:14:07，格林尼治时间
   * 2038年1月19日 凌晨 03:14:07 YYYYMMDD HHMMSS 混合日期和时间值，时间戳
   */
  TIMESTAMP(StandardTypeEnum.TIMESTAMP, "TIMESTAMP"),

  /** CHAR 0-255字节 定长字符串 */
  CHAR(StandardTypeEnum.CHAR, "CHAR", 0, 255),

  /** VARCHAR 0-65535 字节 变长字符串 */
  VARCHAR(StandardTypeEnum.VARCHAR, "VARCHAR", 0, 65535),

  /** TINYBLOB 0-255字节 不超过 255 个字符的二进制字符串 */
  TINYBLOB(StandardTypeEnum.TINYBLOB, "TINYBLOB", 0, 255),

  /** TINYTEXT 0-255字节 短文本字符串 */
  TINYTEXT(StandardTypeEnum.TINYTEXT, "TINYTEXT", 0, 255),

  /** BLOB 0-65 535字节 二进制形式的长文本数据 */
  BLOB(StandardTypeEnum.BLOB, "BLOB", 0, 65536),

  /** TEXT 0-65 535字节 长文本数据 */
  TEXT(StandardTypeEnum.TEXT, "TEXT", 0, 65536),

  /** MEDIUMBLOB 0-16 777 215字节 二进制形式的中等长度文本数据 */
  MEDIUMBLOB(StandardTypeEnum.MEDIUMBLOB, "MEDIUMBLOB", 0, 16777215),

  /** MEDIUMTEXT 0-16 777 215字节 中等长度文本数据 */
  MEDIUMTEXT(StandardTypeEnum.MEDIUMTEXT, "MEDIUMTEXT", 0, 16777215),

  /** LONGBLOB 0-4 294 967 295字节 二进制形式的极大文本数据 */
  LONGBLOB(StandardTypeEnum.LONGBLOB, "LONGBLOB", 0, 4294967295L),

  /** LONGTEXT 0-4 294 967 295字节 极大文本数据 */
  LONGTEXT(StandardTypeEnum.LONGTEXT, "LONGTEXT", 0, 4294967295L),
  ;

  /** 标准的key */
  private StandardTypeEnum standKey;

  /** mysql中的类型名称 */
  private String mysqlType;

  /** 长度定义范围的开始 */
  private Long lengthStart;

  /** 长度定义的范围的结束 */
  private Long lengthEnd;

  /**
   * 指明标准的key，与mysql的类型
   *
   * @param standKey
   * @param mysqlType
   */
  MysqlDataTypeEnum(StandardTypeEnum standKey, String mysqlType) {
    this.standKey = standKey;
    this.mysqlType = mysqlType;
  }

  /**
   * 标准的key与mysql类型，并指定长度范围
   *
   * @param standKey
   * @param mysqlType
   * @param lengthStart
   * @param lengthEnd
   */
  MysqlDataTypeEnum(StandardTypeEnum standKey, String mysqlType, long lengthStart, long lengthEnd) {
    this.standKey = standKey;
    this.mysqlType = mysqlType;
    this.lengthStart = lengthStart;
    this.lengthEnd = lengthEnd;
  }

  /**
   * 根据数据库的类型，转换为标准的key
   *
   * <p>当转换的类不存在时，则会抛出IllegalArgumentException异常
   *
   * @param mysqlType mysql的类型
   * @return 标准的key
   */
  public static StandardTypeEnum databaseToStandKey(String mysqlType) {
    return databaseToStandKey(mysqlType, null);
  }

  /**
   * 根据数据库的类型，转换为标准的key
   *
   * <p>当转换的类不存在时，则会抛出IllegalArgumentException异常
   *
   * @param mysqlType mysql的类型
   * @param length 类型长度
   * @return 标准的key
   */
  public static StandardTypeEnum databaseToStandKey(String mysqlType, Integer length) {
    for (MysqlDataTypeEnum dataType : values()) {
      // 在数据库的类型判断中不区分大小写
      if (dataType.mysqlType.equalsIgnoreCase(mysqlType)) {

        // 1,如果在标准的设置中未定义长度，则与java中直接对应，返回标准的类型
        if (dataType.lengthStart == null || dataType.lengthEnd == null || null == length) {
          return dataType.standKey;
        }

        // 检查类型是否在java的范围中
        if (dataType.lengthStart <= length && length <= dataType.lengthEnd) {
          return dataType.standKey;
        } else {
          throw new IllegalArgumentException(
              "mysql type :"
                  + mysqlType
                  + " is not in scope {"
                  + dataType.lengthStart
                  + " - "
                  + dataType.lengthEnd
                  + "}");
        }
      }
    }

    throw new IllegalArgumentException("mysql type :" + mysqlType + " not corrspend stand key");
  }
}

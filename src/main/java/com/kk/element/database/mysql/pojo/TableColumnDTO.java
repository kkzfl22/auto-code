package com.kk.element.database.mysql.pojo;

/**
 * java的对应数据库的字段信息
 *
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月8日 下午5:04:10
 */
public class TableColumnDTO {

  /** 表名信息 */
  private String tableName;

  /** 列名 */
  private String columnName;

  /** 注释信息 */
  private String columnMsg;

  /** 列的类型信息 */
  private String dataType;

  /** 是否为主键 */
  private boolean primaryKey;

  /** 长度信息 */
  private int dataLength;

  /** 精度 */
  private int dataScale;

  /** 是否自增长 */
  private boolean isAutoIncrement;

  /** 是否允许为空 */
  private Boolean nullFlag;

  /** 数据库设置的默认值 */
  private String defaultValue;

  public TableColumnDTO() {}

  public TableColumnDTO(
      String columnName,
      String columnMsg,
      String dataType,
      boolean primaryKey,
      boolean nullFlag,
      String defaultvalue) {
    super();
    this.columnName = columnName;
    this.columnMsg = columnMsg;
    this.dataType = dataType;
    this.primaryKey = primaryKey;
    this.nullFlag = nullFlag;
    this.defaultValue = defaultvalue;
  }

  public boolean isPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(boolean primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getColumnMsg() {
    return columnMsg;
  }

  public void setColumnMsg(String columnMsg) {
    this.columnMsg = columnMsg;
  }

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public int getDataLength() {
    return dataLength;
  }

  public void setDataLength(int dataLength) {
    this.dataLength = dataLength;
  }

  public int getDataScale() {
    return dataScale;
  }

  public void setDataScale(int dataScale) {
    this.dataScale = dataScale;
  }

  public boolean isAutoIncrement() {
    return isAutoIncrement;
  }

  public void setAutoIncrement(boolean isAutoIncrement) {
    this.isAutoIncrement = isAutoIncrement;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public boolean isNullFlag() {
    return nullFlag;
  }

  public void setNullFlag(boolean nullFlag) {
    this.nullFlag = nullFlag;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TableColumnDTO{");
    sb.append("tableName='").append(tableName).append('\'');
    sb.append(", columnName='").append(columnName).append('\'');
    sb.append(", columnMsg='").append(columnMsg).append('\'');
    sb.append(", dataType='").append(dataType).append('\'');
    sb.append(", primaryKey=").append(primaryKey);
    sb.append(", dataLength=").append(dataLength);
    sb.append(", dataScale=").append(dataScale);
    sb.append(", isAutoIncrement=").append(isAutoIncrement);
    sb.append(", emptyFlag=").append(nullFlag);
    sb.append(", defaultValue='").append(defaultValue).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

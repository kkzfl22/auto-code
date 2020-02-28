package com.kk.docprocess.docCommon.bean;

/** 列类型信息 */
public class TableColumnBean implements Comparable<TableColumnBean> {

  /** 序号 @字段说明 seqNum */
  private int seqNum;

  /** 列名 @字段说明 columnName */
  private String columnName;

  /** 数据库的类型信息 */
  private String dbType;

  /** 用于表示java的类型信息 */
  private String type;

  /** 列长度 */
  private int length;

  /** 是否为空(Y/N) @字段说明 isNullFlag */
  private String isNullFlag;

  /** 默认值 @字段说明 defaultValue */
  private String defaultValue;

  /** 是否为自增加主键(Y/N) @字段说明 primaryKeyFlag */
  private String autoInctFlag;

  /** 描述信息 @字段说明 desc */
  private String desc;

  public int getSeqNum() {
    return seqNum;
  }

  public void setSeqNum(int seqNum) {
    this.seqNum = seqNum;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getIsNullFlag() {
    return isNullFlag;
  }

  public void setIsNullFlag(String isNullFlag) {
    this.isNullFlag = isNullFlag;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getAutoInctFlag() {
    return autoInctFlag;
  }

  public void setAutoInctFlag(String autoInctFlag) {
    this.autoInctFlag = autoInctFlag;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public String getDbType() {
    return dbType;
  }

  public void setDbType(String dbType) {
    this.dbType = dbType;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TableColumnBean{");
    sb.append("seqNum=").append(seqNum);
    sb.append(", columnName='").append(columnName).append('\'');
    sb.append(", dbType='").append(dbType).append('\'');
    sb.append(", type='").append(type).append('\'');
    sb.append(", length=").append(length);
    sb.append(", isNullFlag='").append(isNullFlag).append('\'');
    sb.append(", defaultValue='").append(defaultValue).append('\'');
    sb.append(", autoInctFlag='").append(autoInctFlag).append('\'');
    sb.append(", desc='").append(desc).append('\'');
    sb.append('}');
    return sb.toString();
  }

  @Override
  public int compareTo(TableColumnBean o) {
    if (this.getSeqNum() > o.getSeqNum()) {
      return 1;
    } else if (this.getSeqNum() < o.getSeqNum()) {
      return -1;
    }
    return 0;
  }
}

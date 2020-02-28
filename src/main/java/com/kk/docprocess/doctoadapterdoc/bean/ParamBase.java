package com.kk.docprocess.doctoadapterdoc.bean;

/**
 * 基本参数的bean信息
 *
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月11日 下午1:44:46
 */
public class ParamBase {

  /** 请求参数序列 */
  private String paramSeq;

  /** 参数名 */
  private String paramName;

  /** 数据库的类型信息 */
  private String dbType;

  /** 参数类型,用于表示java的类型 */
  private String paramType;

  /** 是否为空(Y/N) */
  private String isNullFlag;

  /** 默认值 */
  private String defValue;

  /** 描述信息 */
  private String msg;

  public ParamBase() {}

  public ParamBase(
      String paramSeq,
      String paramName,
      String paramType,
      String isNullFlag,
      String defValue,
      String msg) {
    super();
    this.paramSeq = paramSeq;
    this.paramName = paramName;
    this.paramType = paramType;
    this.isNullFlag = isNullFlag;
    this.defValue = defValue;
    this.msg = msg;
  }

  public String getParamSeq() {
    return paramSeq;
  }

  public void setParamSeq(String paramSeq) {
    this.paramSeq = paramSeq;
  }

  public String getParamName() {
    return paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public String getParamType() {
    return paramType;
  }

  public void setParamType(String paramType) {
    this.paramType = paramType;
  }

  public String isNullFlag() {
    return isNullFlag;
  }

  public void setNullFlag(String isNullFlag) {
    this.isNullFlag = isNullFlag;
  }

  public String getDefValue() {
    return defValue;
  }

  public void setDefValue(String defValue) {
    this.defValue = defValue;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getDbType() {
    return dbType;
  }

  public void setDbType(String dbType) {
    this.dbType = dbType;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ParamBase{");
    sb.append("paramSeq='").append(paramSeq).append('\'');
    sb.append(", paramName='").append(paramName).append('\'');
    sb.append(", dbType='").append(dbType).append('\'');
    sb.append(", paramType='").append(paramType).append('\'');
    sb.append(", isNullFlag='").append(isNullFlag).append('\'');
    sb.append(", defValue='").append(defValue).append('\'');
    sb.append(", msg='").append(msg).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

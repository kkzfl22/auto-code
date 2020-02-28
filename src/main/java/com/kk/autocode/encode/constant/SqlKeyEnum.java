package com.kk.autocode.encode.constant;

/**
 * SQL中的关键字
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/11/13
 */
public enum SqlKeyEnum {

  /** where条件中的and符号 */
  AND("and"),
  ;

  private String key;

  SqlKeyEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SqlKeyEnum{");
    sb.append("key='").append(key).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

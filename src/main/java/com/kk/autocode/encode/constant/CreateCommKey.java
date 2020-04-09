package com.kk.autocode.encode.constant;

/**
 * @author liujun
 * @version 0.0.1
 */
public enum CreateCommKey {
  JUNIT_IMPORT_KEY("junit_import_key"),
  ;

  private String key;

  CreateCommKey(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CreateCommKey{");
    sb.append("key='").append(key).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

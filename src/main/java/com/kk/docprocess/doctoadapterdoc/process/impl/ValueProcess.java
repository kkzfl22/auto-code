package com.kk.docprocess.doctoadapterdoc.process.impl;

import com.kk.autocode.util.StringUtils;
import com.kk.element.database.mysql.pojo.TableColumnDTO;

/**
 * 数据值的处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/13
 */
public class ValueProcess {

  /** 实例信息 */
  public static final ValueProcess INSTANCE = new ValueProcess();

  /**
   * 得到java的默认值 方法描述
   *
   * @param type type info
   * @return 类型信息
   */
  public String getJavaDefValue(String type) {
    String defValue = "";

    switch (type) {
      case "INT":
        defValue = "= 0";
        break;
      case "FLOAT":
        defValue = "= 0f";
        break;
      case "NUMERIC":
        defValue = "= 0";
        break;
      case "DOUBLE":
        defValue = "= 0";
        break;
      case "BIGINT":
        defValue = "= 0l";
        break;
      default:
        break;
    }

    return defValue;
  }

  /**
   * 对javabean设置默认值的方法
   *
   * @param defValue 默认值
   * @param dbtype 类型信息
   * @return 默认值
   */
  public String getJavaDefValue(String defValue, String dbtype) {

    // 优先默认值
    if (!StringUtils.isEmptyOrNull(defValue)) {
      return defValue;
    }

    String javaTypeValue;

    switch (dbtype) {
      case "int":
        javaTypeValue = "1";
        break;
      case "bigint":
        javaTypeValue = "11";
        break;
      case "boolean":
        javaTypeValue = "true";
        break;
      case "varchar":
        javaTypeValue = "V";
        break;
      case "long":
        javaTypeValue = "2l";
        break;
      case "float":
        javaTypeValue = "3.0f";
        break;
      case "double":
        javaTypeValue = "4d";
        break;
      case "date":
        javaTypeValue = "2019-01-01";
        break;
      case "datetime":
        javaTypeValue = "2019-01-01 01:00:00";
        break;
      case "timestamp":
        javaTypeValue = "2019-01-01 02:00:00";
        break;
      default:
        javaTypeValue = "D";
        break;
    }

    return javaTypeValue;
  }

  /**
   * 转换参数信息 方法描述
   *
   * @param javaType 类型信息
   * @param value 值信息
   * @return 返回值
   */
  public String parseJavaValue(String javaType, String value) {
    String outJavaValue = value;

    //    switch (javaType) {
    //      case "int":
    //        outJavaValue = value;
    //        break;
    //      case "long":
    //        outJavaValue = value;
    //        break;
    //      case "float":
    //        outJavaValue = value;
    //        break;
    //      default:
    //        outJavaValue = value;
    //        break;
    //    }

    return outJavaValue;
  }
}

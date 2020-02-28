package com.kk.docprocess.doctoadapterdoc.process.impl;

import com.kk.autocode.encode.constant.JavaDataTypeEnum;
import com.kk.autocode.encode.constant.MybatisDataTypeEnum;
import com.kk.autocode.encode.constant.MysqlDataTypeEnum;
import com.kk.autocode.encode.constant.StandardTypeEnum;

/**
 * 类型处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/13
 */
public class TypeProcess {

  /** 类型转化实例 */
  public static final TypeProcess INSTANCE = new TypeProcess();

  /**
   * 得到java的数据类型 方法描述
   *
   * @param databaseType typeinfo
   * @return @创建日期 2016年10月9日
   */
  public String getJavaType(String databaseType) {

    String javaType = null;

    String tempType = databaseType.toLowerCase();

    // 1,从数据库的类型中获取
    StandardTypeEnum standardType = MysqlDataTypeEnum.databaseToStandKey(tempType);

    // 通过数据库的类型转换为java类型信息
    return JavaDataTypeEnum.getJavaType(standardType);

    //    switch (tempType) {
    //      case "int":
    //        javaType = "Integer";
    //        break;
    //      case "varchar":
    //        javaType = "String";
    //        break;
    //      case "datetime":
    //        javaType = "String";
    //        break;
    //      case "bigint":
    //        javaType = "Long";
    //        break;
    //      case "timestamp":
    //        javaType = "String";
    //        break;
    //      case "float":
    //        javaType = "Float";
    //        break;
    //      case "double":
    //        javaType = "Double";
    //        break;
    //      case "decimal":
    //        javaType = "BigDecimal";
    //        break;
    //      default:
    //        javaType = "String";
    //        break;
    //    }

    // return javaType;
  }

  /**
   * 转换为mybatis的类型信息
   *
   * @param dataType
   * @return
   */
  public String dbTypeParseMyBatis(String dataType, Integer length) {
    String outType;

    // 1,从数据库的类型中获取
    StandardTypeEnum standardType = MysqlDataTypeEnum.databaseToStandKey(dataType, length);

    // 转换成对应的mybatis类型返回
    return MybatisDataTypeEnum.getMybatisType(standardType);

    //    switch (dataType) {
    //      case "int":
    //        outType = "INTEGER";
    //        break;
    //      case "bigint":
    //        outType = "BIGINT";
    //        break;
    //      case "number":
    //        outType = "NUMBER";
    //        break;
    //      case "float":
    //        outType = "FLOAT";
    //        break;
    //      case "double":
    //        outType = "DOUBLE";
    //        break;
    //      case "date":
    //        outType = "DATE";
    //        break;
    //      case "datetime":
    //        outType = "TIMESTAMP";
    //        break;
    //      case "time":
    //        outType = "TIME";
    //        break;
    //      case "timestamp":
    //        outType = "TIMESTAMP";
    //        break;
    //      case "decimal":
    //        outType = "DECIMAL";
    //        break;
    //      default:
    //        outType = "VARCHAR";
    //        break;
    //    }

    // return outType;
  }
}

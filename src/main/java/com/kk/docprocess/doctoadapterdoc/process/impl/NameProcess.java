package com.kk.docprocess.doctoadapterdoc.process.impl;

import java.util.List;

import com.kk.docprocess.docCommon.bean.TableColumnBean;
import com.kk.docprocess.docCommon.bean.TableBean;
import com.kk.utils.SystemSymbol;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/19
 */
public class NameProcess {

  /** 实例对象 */
  public static final NameProcess INSTANCE = new NameProcess();

  /** 换行符 @字段说明 NEXT_LINE */
  protected static final String NEXT_LINE = SystemSymbol.GetLine();

  /** 制表符信息 @字段说明 TABLE_LINE */
  private static final String TABLE_LINE = "\t";

  /**
   * 转换java的类名信息,首字母大写 方法描述
   *
   * @param tableName
   * @return @创建日期 2016年9月27日
   */
  public String toJavaClassName(String tableName) {
    tableName = tableName.toLowerCase();
    String[] strs = tableName.split("_");
    StringBuilder sb = new StringBuilder();

    boolean firstOne = false;

    for (int i = 0; i < strs.length; i++) {
      String string = strs[i];

      // 检查首字母是否为单字符
      if (i == 0) {
        firstOne = string.length() == 1 ? true : false;
      }

      // 首字母大写
      if (string.length() > 1) {

        // 如果首字母为单字符，第二个字符小写
        if (i == 1 && firstOne) {
          sb.append(string);
        }
        // 其他字符都使用驼峰命名法
        else {
          // 中间开头的第一个字母都小写
          String f = string.substring(0, 1).toUpperCase();
          sb.append(f);
          sb.append(string.substring(1));
        }
      } else {
        sb.append(string.toUpperCase());
      }
    }

    return sb.toString();
  }

  /**
   * 转换为action的名字 方法描述
   *
   * @param str
   * @return @创建日期 2016年9月30日
   */
  protected String toActionStr(String str) {
    return str.substring(0, 1).toLowerCase() + str.substring(1);
  }

  /**
   * 转换为java命名规则,首字线小写，其他的首字母大写 方法描述
   *
   * @param str
   * @return @创建日期 2016年9月28日
   */
  public String toJava(String str) {
    String[] strs = str.split("_");
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < strs.length; i++) {
      if (i == 0) {
        sb.append(strs[i].toLowerCase());
      } else {
        sb.append(strs[i].substring(0, 1).toUpperCase());
        sb.append(strs[i].substring(1).toLowerCase());
      }
    }
    return sb.toString();
  }

  /**
   * 转换为java属性get与set命名规则 方法描述
   *
   * @param str
   * @return @创建日期 2016年9月28日
   */
  public String toProJavaName(String str) {
    String[] strs = str.split("_");
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < strs.length; i++) {
      sb.append(strs[i].substring(0, 1).toUpperCase());
      sb.append(strs[i].substring(1).toLowerCase());
    }
    return sb.toString();
  }

  /**
   * 获得表列信息 方法描述
   *
   * @param list
   * @return
   * @throws Exception @创建日期 2016年9月12日
   */
  public List<TableBean> procTableBean(List<TableBean> list) throws Exception {
    // 取得数据库操作类型
    List<TableColumnBean> listColumnTable = null;

    String typeVal = null;
    String type = null;
    int length = 0;
    int leftIndex = 0;

    for (TableBean bean : list) {
      listColumnTable = bean.getColumnList();

      for (TableColumnBean columnBean : listColumnTable) {
        typeVal = columnBean.getType().trim();
        type = typeVal;

        // 寻找INT(12)这类的设置，如果存在则进行设置
        leftIndex = type.indexOf("(");
        if (leftIndex != -1) {
          type = typeVal.substring(0, leftIndex);
          length = Integer.parseInt(typeVal.substring(leftIndex + 1, typeVal.length() - 1));

          columnBean.setType(type);
          columnBean.setLength(length);
        }
      }
    }
    return list;
  }

  /**
   * 获取Spring的名称的信息 方法描述
   *
   * @param name
   * @return @创建日期 2016年9月28日
   */
  protected String getSpringInstanceName(String name) {
    String tmpName = name;

    tmpName = tmpName.substring(0, 1).toLowerCase() + tmpName.substring(1);

    return tmpName;
  }

  /**
   * 进行格式化操作 方法描述
   *
   * @param num 输出\t的数量
   * @return @创建日期 2016年10月12日
   */
  protected String formatMsg(int num) {

    StringBuilder toMsg = new StringBuilder();
    if (num <= 0) {
      num = 1;
    }

    for (int i = 0; i < num; i++) {
      toMsg.append(TABLE_LINE);
    }

    return toMsg.toString();
  }

  /**
   * 转换为java命名规则,首字线小写，其他的首字母大写 方法描述
   *
   * @param str
   * @return @创建日期 2016年9月28日
   */
  public String toJavaNameFirst(String str) {
    StringBuilder sb = new StringBuilder();
    sb.append(str.substring(0, 1).toLowerCase());
    sb.append(str.substring(1));
    return sb.toString();
  }
}

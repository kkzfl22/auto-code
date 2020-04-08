package com.kk.autocode.encode.code.bean.microservice;

import com.kk.autocode.encode.base.TableProcessBase;
import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.bean.EncodeContext;
import com.kk.autocode.encode.code.AutoCodeInf;
import com.kk.autocode.encode.constant.Symbol;
import com.kk.autocode.util.DateUtils;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 生成dao的java代码
 *
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月8日 下午5:38:19
 */
public class JavaCodeRepositoryDaoInfCreate extends TableProcessBase implements AutoCodeInf {

  public static final String DAO_SUFFIX = "DAO";
  public static final String DAO_PACKAGE = "mapper";
  public static final String DAO_NAME = "repositorydao";


  private static final String IMPORT_LIST = "import java.util.List;";
  private static final String DOC = "数据库操作";
  private static final String ADD_DOC = "添加操作";
  private static final String UPD_DOC = "修改操作";
  private static final String DEL_DOC = "删除操作";
  private static final String QUERY_DOC = "查询操作";
  private static final String METHOD_PARAM = " @param param ";
  private static final String METHOD_PARAM_DOC = "参数信息";
  private static final String METHOD_RESULT = " @return 数据库影响的行数";
  private static final String METHOD_QUERY_RESULT = " @return 数据库查询结果集";
  private static final String INT_TYPE = "int";
  private static final String METHOD_INSERT = "insert";
  private static final String METHOD_UPDATE = "update";
  private static final String METHOD_DELETE = "delete";
  private static final String METHOD_QUERY = "query";
  private static final String METHOD_PARAM_NAME = " param";
  private static final String QUERY_LIST_START = "List<";
  private static final String QUERY_LIST_END = ">";



  /**
   * 进行dao的接口代码的生成 方法描述
   *
   * @param path 路径
   * @param basePackage 基础路径
   * @param tableSpaceName 命名空间
   * @throws Exception @创建日期 2016年10月8日
   */
  private void encodeDaoInf(
      String path, String basePackage, String tableSpaceName, EncodeContext context)
      throws Exception {
    File dirFile = new File(path);
    // 如果文件夹存在，则执行删除
    boolean exists = dirFile.exists();
    if (exists) {
      dirFile.delete();
    }
    dirFile.mkdirs();

    Map<String, List<TableColumnDTO>> map = context.getColumnMap();
    Iterator<Entry<String, List<TableColumnDTO>>> tableNameEntry = map.entrySet().iterator();
    while (tableNameEntry.hasNext()) {
      Entry<String, List<TableColumnDTO>> tableName = tableNameEntry.next();
      TableInfoDTO tableInfo = context.getTableMap().get(tableName.getKey());

      StringBuilder sb = new StringBuilder();

      String tableClassName = toJavaClassName(tableName.getKey());

      String serviceName = tableClassName;
      String className = serviceName + DAO_SUFFIX;
      String importBean =
          basePackage
              + JavaCodeRepositoryPoCreate.REPOSITORY_PO_PACKAGE
              + tableClassName
              + JavaCodeRepositoryPoCreate.REPOSITORY_PO;
      String beanParam = tableClassName + JavaCodeRepositoryPoCreate.REPOSITORY_PO;
      String packageStr = basePackage + DAO_PACKAGE;

      // 定义package
      sb.append(JavaCodeKey.PACKAGE)
          .append(Symbol.SPACE)
          .append(packageStr)
          .append(Symbol.SEMICOLON)
          .append(NEXT_LINE);
      // 导入utils
      sb.append(IMPORT_LIST).append(NEXT_LINE);
      sb.append(NEXT_LINE);
      // 导入bean
      sb.append(JavaCodeKey.IMPORT)
          .append(Symbol.SPACE)
          .append(importBean)
          .append(Symbol.SEMICOLON)
          .append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 添加类注释信息
      sb.append(JavaCodeKey.ANNO_CLASS).append(NEXT_LINE);
      sb.append(JavaCodeKey.ANNO_CLASS_MID)
          .append(tableInfo.getTableComment())
          .append(Symbol.BRACKET_LEFT)
          .append(tableName.getKey())
          .append(Symbol.BRACKET_RIGHT)
          .append(DOC)
          .append(NEXT_LINE);
      sb.append(JavaCodeKey.DOC_VERSION).append(NEXT_LINE);
      sb.append(JavaCodeKey.DOC_AUTH).append(NEXT_LINE);
      sb.append(JavaCodeKey.ANNO_OVER).append(NEXT_LINE);

      // 类的定义
      sb.append(JavaCodeKey.PUBLIC)
          .append(Symbol.SPACE)
          .append(JavaCodeKey.INTERFACE)
          .append(Symbol.SPACE)
          .append(className)
          .append(Symbol.SPACE)
          .append(Symbol.BRACK_LEFT)
          .append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 添加方法
      this.insertMethod(sb, tableInfo, beanParam);

      // 修改方法
      this.updateMethod(sb, tableInfo, beanParam);

      // 删除方法
      this.deleteMethod(sb, tableInfo, beanParam);

      // 查询方法
      this.queryMethod(sb, tableInfo, beanParam);

      // 结束
      sb.append(Symbol.BRACK_RIGHT);
      FileWriter fw = new FileWriter(new File(path + className + JavaCodeKey.FILE_SUFFIX));
      fw.write(sb.toString());
      fw.close();
    }
  }

  @Override
  public void autoCode(CreateParamBean param) {
    // 进行自动代码生成
    try {
      String daoInfPath = param.getFileBasePath() + DAO_NAME + Symbol.PATH;
      String javaPackage = param.getJavaPackage();
      this.encodeDaoInf(daoInfPath, javaPackage, param.getTableSpaceName(), param.getContext());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 插入方法
   *
   * @param sb
   * @param tableInfo
   * @param beanParam
   */
  public void insertMethod(StringBuilder sb, TableInfoDTO tableInfo, String beanParam) {

    // 添加insert方法
    sb.append(formatMsg(1)).append(JavaCodeKey.ANNO_CLASS).append(NEXT_LINE);
    // 类注释描述
    sb.append(formatMsg(1))
        .append(JavaCodeKey.ANNO_CLASS_MID)
        .append(tableInfo.getTableComment())
        .append(Symbol.BRACKET_LEFT)
        .append(tableInfo.getTableName())
        .append(Symbol.BRACKET_RIGHT)
        .append(ADD_DOC)
        .append(NEXT_LINE);
    // 添加参数注释
    sb.append(formatMsg(1))
        .append(JavaCodeKey.ANNO_CLASS_MID)
        .append(METHOD_PARAM)
        .append(METHOD_PARAM_DOC)
        .append(NEXT_LINE);
    // 添加返回注释
    sb.append(formatMsg(1))
        .append(JavaCodeKey.ANNO_CLASS_MID)
        .append(METHOD_RESULT)
        .append(NEXT_LINE);
    // 注释结束
    sb.append(formatMsg(1)).append(JavaCodeKey.ANNO_OVER).append(NEXT_LINE);
    // 方法定义
    sb.append(formatMsg(1))
        .append(INT_TYPE)
        .append(Symbol.SPACE)
        .append(METHOD_INSERT)
        .append(Symbol.BRACKET_LEFT)
        .append(beanParam)
        .append(METHOD_PARAM_NAME)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(NEXT_LINE);
  }

  /**
   * 修改方法
   *
   * @param sb
   * @param tableInfo
   */
  public void updateMethod(StringBuilder sb, TableInfoDTO tableInfo, String beanParam) {

    // 修改方法
    sb.append(formatMsg(1)).append(JavaCodeKey.ANNO_CLASS).append(NEXT_LINE);
    // 类描述注释
    sb.append(formatMsg(1))
        .append(JavaCodeKey.ANNO_CLASS_MID)
        .append(tableInfo.getTableComment())
        .append(Symbol.BRACKET_LEFT)
        .append(tableInfo.getTableName())
        .append(Symbol.BRACKET_RIGHT)
        .append(UPD_DOC)
        .append(NEXT_LINE);

    // 添加参数注释
    sb.append(formatMsg(1))
        .append(JavaCodeKey.ANNO_CLASS_MID)
        .append(METHOD_PARAM)
        .append(METHOD_PARAM_DOC)
        .append(NEXT_LINE);
    // 添加返回注释
    sb.append(formatMsg(1))
        .append(JavaCodeKey.ANNO_CLASS_MID)
        .append(METHOD_RESULT)
        .append(NEXT_LINE);
    // 注释结束
    sb.append(formatMsg(1)).append(JavaCodeKey.ANNO_OVER).append(NEXT_LINE);

    // 修改方法定义
    sb.append(formatMsg(1))
        .append(INT_TYPE)
        .append(Symbol.SPACE)
        .append(METHOD_UPDATE)
        .append(Symbol.BRACKET_LEFT)
        .append(beanParam)
        .append(METHOD_PARAM_NAME)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(NEXT_LINE);
    sb.append(NEXT_LINE);
  }

  public void deleteMethod(StringBuilder sb, TableInfoDTO tableInfo, String beanParam) {
    // 删除
    sb.append(formatMsg(1)).append(JavaCodeKey.ANNO_CLASS).append(NEXT_LINE);
    // 类描述注释
    sb.append(formatMsg(1))
        .append(JavaCodeKey.ANNO_CLASS_MID)
        .append(tableInfo.getTableComment())
        .append(Symbol.BRACKET_LEFT)
        .append(tableInfo.getTableName())
        .append(Symbol.BRACKET_RIGHT)
        .append(DEL_DOC)
        .append(NEXT_LINE);

    // 添加参数注释
    sb.append(formatMsg(1))
        .append(JavaCodeKey.ANNO_CLASS_MID)
        .append(METHOD_PARAM)
        .append(METHOD_PARAM_DOC)
        .append(NEXT_LINE);
    // 添加返回注释
    sb.append(formatMsg(1))
        .append(JavaCodeKey.ANNO_CLASS_MID)
        .append(METHOD_RESULT)
        .append(NEXT_LINE);
    // 注释结束
    sb.append(formatMsg(1)).append(JavaCodeKey.ANNO_OVER).append(NEXT_LINE);

    // 删除方法定义
    sb.append(formatMsg(1))
        .append(INT_TYPE)
        .append(Symbol.SPACE)
        .append(METHOD_DELETE)
        .append(Symbol.BRACKET_LEFT)
        .append(beanParam)
        .append(METHOD_PARAM_NAME)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(NEXT_LINE);
  }

  /**
   * 查询方法
   *
   * @param sb
   * @param tableInfo
   * @param beanParam
   */
  public void queryMethod(StringBuilder sb, TableInfoDTO tableInfo, String beanParam) {
    // 普通查询查询
    sb.append(formatMsg(1)).append(JavaCodeKey.ANNO_CLASS).append(NEXT_LINE);
    sb.append(formatMsg(1))
        .append(JavaCodeKey.ANNO_CLASS_MID)
        .append(tableInfo.getTableComment())
        .append(Symbol.BRACKET_LEFT)
        .append(tableInfo.getTableName())
        .append(Symbol.BRACKET_RIGHT)
        .append(QUERY_DOC)
        .append(NEXT_LINE);

    // 添加参数注释
    sb.append(formatMsg(1))
        .append(JavaCodeKey.ANNO_CLASS_MID)
        .append(METHOD_PARAM)
        .append(METHOD_PARAM_DOC)
        .append(NEXT_LINE);
    // 添加返回注释
    sb.append(formatMsg(1))
        .append(JavaCodeKey.ANNO_CLASS_MID)
        .append(METHOD_QUERY_RESULT)
        .append(NEXT_LINE);
    sb.append(formatMsg(1)).append(JavaCodeKey.ANNO_OVER).append(NEXT_LINE);

    // 查询方法的定义
    sb.append(formatMsg(1))
        .append(QUERY_LIST_START)
        .append(beanParam)
        .append(QUERY_LIST_END)
        .append(Symbol.SPACE)
        .append(METHOD_QUERY)
        .append(Symbol.BRACKET_LEFT)
        .append(beanParam)
        .append(Symbol.SPACE)
        .append(METHOD_PARAM_NAME)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(NEXT_LINE);
  }
}

package com.kk.autocode.encode.code.bean;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kk.autocode.encode.base.TableProcessBase;
import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.bean.EncodeContext;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;
import com.kk.autocode.encode.code.AutoCodeInf;
import com.kk.autocode.util.DateUtils;

/**
 * 生成dao的java代码
 *
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月8日 下午5:38:19
 */
public class JavaCodeBeanDaoInfCreate extends TableProcessBase implements AutoCodeInf {

  public static void main(String[] args) throws Exception {
    JavaCodeBeanDaoInfCreate daoInf = new JavaCodeBeanDaoInfCreate();

    args = new String[3];

    args[0] = "D:/java/encode/a10/javabeanDao/";
    args[1] = "com.a10.resource.phone.";
    args[2] = "a10";

    Map<String, List<TableColumnDTO>> map = daoInf.getTableColumnInfoByBean(args[2]);

    Map<String, TableInfoDTO> tableMap = daoInf.getTableInfoByBean(args[2]);

    EncodeContext context = new EncodeContext();

    context.setColumnMap(map);
    context.setTableMap(tableMap);

    daoInf.encodeDaoInf(args[0], args[1], args[2], context);
  }

  /**
   * 进行dao的接口代码的生成 方法描述
   *
   * @param path
   * @param packageStr
   * @param tableSpaceName
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

    // Set<String> tableNames = map.keySet();
    Iterator<Entry<String, List<TableColumnDTO>>> tableNameEntry = map.entrySet().iterator();

    while (tableNameEntry.hasNext()) {
      Entry<String, List<TableColumnDTO>> tableName = tableNameEntry.next();

      TableInfoDTO tableInfo = context.getTableMap().get(tableName.getKey());

      StringBuffer sb = new StringBuffer();

      String tableClassName = toJavaClassName(tableName.getKey());

      String serviceName = tableClassName;
      String className = serviceName + "DAO";

      String importBean = basePackage + "dto." + tableClassName + "DTO";

      String beanParam = tableClassName + "DTO";

      // 当前的包路径信息
      String packageStr = basePackage + "dao";

      sb.append("package ").append(packageStr).append(";").append(NEXT_LINE);

      sb.append("import java.util.List;").append(NEXT_LINE);
      sb.append(NEXT_LINE);

      sb.append("import ").append(importBean).append(";").append(NEXT_LINE);
      // sb.append("import ").append(COMM_SPIT_BEAN).append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 添加类注释信息
      sb.append("/**").append(NEXT_LINE);
      sb.append(" * ")
          .append(tableInfo.getTableComment())
          .append("(")
          .append(tableName.getKey())
          .append(")数据库操作")
          .append(NEXT_LINE);
      sb.append(" * @version 1.0.0").append(NEXT_LINE);
      sb.append(" * @author liujun").append(NEXT_LINE);
      sb.append(" * @date ").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
      sb.append(" */").append(NEXT_LINE);
      sb.append("public interface " + className + " {" + NEXT_LINE);
      sb.append(" " + NEXT_LINE);

      // 添加insert方法
      sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
      sb.append(formatMsg(1)).append(" * ").append(tableInfo.getTableComment()).append("(");
      sb.append(tableName.getKey()).append(")添加操作").append(NEXT_LINE);
      sb.append(formatMsg(1)).append(" * @param param 参数信息").append(NEXT_LINE);
      sb.append(formatMsg(1)).append(" * @return 数据库影响的行数").append(NEXT_LINE);
      sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("int insert(" + beanParam + " param) ;").append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 修改
      sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
      sb.append(formatMsg(1)).append(" * ").append(tableInfo.getTableComment()).append("(");
      sb.append(tableName.getKey()).append(")修改操作").append(NEXT_LINE);

      sb.append(formatMsg(1)).append(" * @param param 参数信息").append(NEXT_LINE);
      sb.append(formatMsg(1)).append(" * @return 数据库影响的行数").append(NEXT_LINE);
      sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("int update(" + beanParam + " param) ;").append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 删除
      sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
      sb.append(formatMsg(1)).append(" * ").append(tableInfo.getTableComment()).append("(");
      sb.append(tableName.getKey()).append(")删除操作").append(NEXT_LINE);

      sb.append(formatMsg(1)).append(" * @param param 参数信息").append(NEXT_LINE);
      sb.append(formatMsg(1)).append(" * @return 数据库影响的行数").append(NEXT_LINE);
      sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("int delete(" + beanParam + " param) ;").append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 普通查询查询
      sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
      sb.append(formatMsg(1)).append(" * ").append(tableInfo.getTableComment()).append("(");
      sb.append(tableName.getKey()).append(")查询操作").append(NEXT_LINE);
      sb.append(formatMsg(1)).append(" * @param param 参数信息").append(NEXT_LINE);
      sb.append(formatMsg(1)).append(" * @return 数据库查询结果集").append(NEXT_LINE);
      sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
      sb.append(formatMsg(1))
          .append("List<" + beanParam + "> query(" + beanParam + " param) ;")
          .append(NEXT_LINE);
      sb.append(NEXT_LINE);

      //      // 分页查询统计
      //      sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
      //      sb.append(formatMsg(1)).append(" * ").append(tableInfo.getTableComment()).append("(");
      //      sb.append(tableName.getKey()).append(")分页查询操作").append(NEXT_LINE);
      //      sb.append(formatMsg(1)).append(" * @param param 参数信息").append(NEXT_LINE);
      //      sb.append(formatMsg(1)).append(" * @return 数据库查询结果集").append(NEXT_LINE);
      //      sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
      //      sb.append(formatMsg(1))
      //          .append("Pagination<" + beanParam + "> queryPage")
      //          .append("(" + beanParam + " param) ;")
      //          .append(NEXT_LINE);
      //      sb.append(NEXT_LINE);
      // 结束
      sb.append("}");
      FileWriter fw = new FileWriter(new File(path + className + ".java"));
      fw.write(sb.toString());
      fw.close();
    }
  }

  @Override
  public void autoCode(CreateParamBean param) {
    // 进行自动代码生成
    try {
      String daoInfPath = param.getFileBasePath() + "javaBeanDAO/";
      String javaPackage = param.getJavaPackage();
      this.encodeDaoInf(daoInfPath, javaPackage, param.getTableSpaceName(), param.getContext());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

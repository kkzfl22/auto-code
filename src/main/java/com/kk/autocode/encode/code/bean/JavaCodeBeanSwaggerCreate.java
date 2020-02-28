package com.kk.autocode.encode.code.bean;

import com.kk.autocode.encode.base.TableProcessBase;
import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.bean.EncodeContext;
import com.kk.autocode.encode.code.AutoCodeInf;
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
 * 生成Bean的java代码
 *
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月8日 下午5:26:10
 */
public class JavaCodeBeanSwaggerCreate extends TableProcessBase implements AutoCodeInf {

  public static void main(String[] args) throws Exception {
    System.out.println(Integer.MAX_VALUE);
    System.out.println(Long.MAX_VALUE);

    args = new String[3];

    args[0] = "D:/java/encode/a10/javabean/";
    args[1] = "com.a10.resource.phone.";
    args[2] = "a10";

    JavaCodeBeanSwaggerCreate serviceImplInstance = new JavaCodeBeanSwaggerCreate();

    Map<String, List<TableColumnDTO>> map = serviceImplInstance.getTableColumnInfoByBean(args[2]);

    Map<String, TableInfoDTO> tableMap = serviceImplInstance.getTableInfoByBean(args[2]);

    EncodeContext context = new EncodeContext();

    context.setColumnMap(map);
    context.setTableMap(tableMap);

    serviceImplInstance.encodeServiceImpl(args[0], args[1], args[2], context);
  }

  private void encodeServiceImpl(
      String path, String basePackageStr, String tableSpace, EncodeContext context)
      throws Exception {

    File dirFile = new File(path);
    // 如果文件夹存在，则执行删除
    boolean exists = dirFile.exists();
    if (exists) {
      dirFile.delete();
    }

    dirFile.mkdirs();

    Map<String, TableInfoDTO> tableMap = context.getTableMap();

    Map<String, List<TableColumnDTO>> map = context.getColumnMap();

    // Set<String> tableNames = map.keySet();
    Iterator<Entry<String, List<TableColumnDTO>>> tableNameEntry = map.entrySet().iterator();

    while (tableNameEntry.hasNext()) {
      Entry<String, List<TableColumnDTO>> tableEntry = tableNameEntry.next();

      // 获取列描述信息
      List<TableColumnDTO> columnList = tableEntry.getValue();

      StringBuilder sb = new StringBuilder();

      String tableName = tableEntry.getKey();

      String tableClassName = toJavaClassName(tableName);

      String serviceName = tableClassName;
      String className = serviceName + "VO";

      sb.append("package ").append(basePackageStr).append("vo").append(";").append(NEXT_LINE);
      sb.append(NEXT_LINE);
      sb.append("import io.swagger.annotations.ApiModel;").append(NEXT_LINE);
      sb.append("import io.swagger.annotations.ApiModelProperty;").append(NEXT_LINE);
      sb.append(NEXT_LINE);

      TableInfoDTO tableMsgBean = tableMap.get(tableName);

      // 添加类注释信息
      sb.append("/**").append(NEXT_LINE);
      sb.append(" * ")
          .append(tableMsgBean.getTableComment())
          .append("(")
          .append(tableName)
          .append(")")
          .append("的实体信息")
          .append(NEXT_LINE);
      sb.append(" * @version 1.0.0").append(NEXT_LINE);
      sb.append(" * @date liujun").append(NEXT_LINE);
      sb.append(" * @since ").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
      sb.append(" */").append(NEXT_LINE);
      sb.append("@ApiModel(\"")
          .append(tableMsgBean.getTableComment())
          .append("(")
          .append(tableName)
          .append(")")
          .append("的实体信息")
          .append("\")")
          .append(NEXT_LINE);
      sb.append("public class " + className + " {");
      sb.append(NEXT_LINE);
      sb.append(" " + NEXT_LINE);
      sb.append(" " + NEXT_LINE);

      // 添加属性的信息
      for (int i = 0; i < columnList.size(); i++) {
        TableColumnDTO tableBean = columnList.get(i);
        String javaName = toJava(tableBean.getColumnName());
        sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
        sb.append(formatMsg(1)).append(" * ").append(tableBean.getColumnMsg()).append(NEXT_LINE);
        sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
        sb.append(formatMsg(1))
            .append("@ApiModelProperty(\"")
            .append(tableBean.getColumnMsg())
            .append("\")")
            .append(NEXT_LINE);
        sb.append(formatMsg(1))
            .append("private ")
            .append(this.getJavaType(tableBean))
            .append(" ")
            .append(javaName)
            .append(this.getJavaDefValue(tableBean))
            .append(";");
        sb.append(NEXT_LINE);
        sb.append(NEXT_LINE);
      }

      // 添加属性set的信息
      for (int i = 0; i < columnList.size(); i++) {
        TableColumnDTO tableBean = columnList.get(i);
        String javaName = toJava(tableBean.getColumnName());
        sb.append(formatMsg(1))
            .append("public void ")
            .append(" set")
            .append(this.toProJavaName(tableBean.getColumnName()));
        sb.append("(").append(this.getJavaType(tableBean));
        sb.append(" ");
        sb.append(this.toJava(tableBean.getColumnName()));
        sb.append(" ){").append(NEXT_LINE);
        sb.append(formatMsg(2))
            .append("this.")
            .append(javaName)
            .append("=")
            .append(javaName)
            .append(";")
            .append(NEXT_LINE);
        sb.append(formatMsg(1)).append("}");
        sb.append(NEXT_LINE);
        sb.append(NEXT_LINE);
      }

      // 添加属性get的信息
      for (int i = 0; i < columnList.size(); i++) {
        TableColumnDTO tableBean = columnList.get(i);
        sb.append(formatMsg(1))
            .append("public  ")
            .append(this.getJavaType(tableBean))
            .append(" get");
        sb.append(this.toProJavaName(tableBean.getColumnName()));
        sb.append("(){");
        sb.append(NEXT_LINE);
        sb.append(formatMsg(2)).append("return this.");
        sb.append(this.toJava(tableBean.getColumnName())).append(";").append(NEXT_LINE);
        sb.append(formatMsg(1)).append("}");
        sb.append(NEXT_LINE);
        sb.append(NEXT_LINE);
      }
      // 生成toString方法
      sb.append(formatMsg(1)).append("@Override").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("public String toString() {").append(NEXT_LINE);
      sb.append(formatMsg(2))
          .append("StringBuilder builder = new StringBuilder();")
          .append(NEXT_LINE);
      sb.append(formatMsg(2))
          .append("builder.append(\"")
          .append(tableClassName)
          .append("[")
          .append("\");")
          .append(NEXT_LINE);
      for (int i = 0; i < columnList.size(); i++) {
        TableColumnDTO tableBean = columnList.get(i);
        sb.append(formatMsg(2)).append("builder.append(\"");
        sb.append(this.toJava(tableBean.getColumnName())).append("=\")");
        sb.append(".append(");
        sb.append(this.toJava(tableBean.getColumnName())).append(")");

        if (i != columnList.size() - 1) {
          sb.append(".append(\",\")");
        }
        sb.append(";");
        sb.append(NEXT_LINE);
      }
      sb.append(formatMsg(2)).append("builder.append(\"]\");").append(NEXT_LINE);
      sb.append(formatMsg(2)).append("return builder.toString();").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("}");
      sb.append(NEXT_LINE);

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
      String daoInfPath = param.getFileBasePath() + "javabeanswagger/";
      String javaPackage = param.getJavaPackage();
      this.encodeServiceImpl(
          daoInfPath, javaPackage, param.getTableSpaceName(), param.getContext());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

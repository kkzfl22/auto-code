package com.kk.autocode.encode.code.bean;

import com.kk.autocode.encode.base.TableProcessBase;
import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.bean.EncodeContext;
import com.kk.autocode.encode.constant.SqlKeyEnum;
import com.kk.autocode.encode.constant.Symbol;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;
import com.kk.autocode.encode.code.AutoCodeInf;
import com.kk.autocode.util.StringUtils;
import com.kk.element.database.type.DataTypeResource;
import com.kk.element.database.type.constant.DatabaseTypeEnum;
import com.kk.element.database.type.constant.DatabaseTypeSourceEnum;
import com.kk.element.database.type.constant.DatabaseTypeSourceMysqlEnum;
import com.kk.element.database.type.pojo.bean.DatabaseTypeMsgBO;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.Map.Entry;

/**
 * 以javabean的方法生成mybatis的mapper代码
 *
 * <p>修改，去掉默认值的限制
 *
 * @since 2018年4月15日 下午3:29:03
 * @version 0.0.1
 * @author liujun
 */
public class JavaCodeBeanMyBatisMapperCreate extends TableProcessBase implements AutoCodeInf {

  /**
   * 方法描述
   *
   * @param args
   * @throws Exception @创建日期 2016年9月28日
   */
  public static void main(String[] args) throws Exception {
    args = new String[4];

    args[0] = "D:/java/encode/a10/beanMapper/";
    args[1] = "com.a10.resource.phone.";
    args[2] = "a10";
    args[3] = "com.a10.resource.phone.";

    JavaCodeBeanMyBatisMapperCreate instance = new JavaCodeBeanMyBatisMapperCreate();

    Map<String, List<TableColumnDTO>> map = instance.getTableColumnInfoByBean(args[3]);

    Map<String, TableInfoDTO> tableMap = instance.getTableInfoByBean(args[3]);

    EncodeContext context = new EncodeContext();

    context.setColumnMap(map);
    context.setTableMap(tableMap);
    // 调用生成mybatis的代码
    instance.mybatisCode(args[0], args[1], args[2], args[3], context);
  }

  /**
   * 用来进行mybatis的xml文件的生成 方法描述
   *
   * @param path 文件路径
   * @param mybatisNamespace mybatis的命名空间
   * @param tableSpaceName 表空间
   * @param basePackage java路径信息
   * @throws Exception 异常
   */
  private void mybatisCode(
      String path,
      String mybatisNamespace,
      String tableSpaceName,
      String basePackage,
      EncodeContext context)
      throws Exception {

    File dirFile = new File(path);
    // 如果文件夹存在，则执行删除
    boolean exists = dirFile.exists();
    if (exists) {
      dirFile.delete();
    }
    dirFile.mkdirs();

    // 获得表信息
    Map<String, List<TableColumnDTO>> srcMap = this.getTableColumnInfoByMap(tableSpaceName);

    // 获取列信息
    Map<String, List<TableColumnDTO>> map = this.parseComment(srcMap);

    Iterator<Entry<String, List<TableColumnDTO>>> IterTable = map.entrySet().iterator();
    while (IterTable.hasNext()) {

      Entry<String, List<TableColumnDTO>> entry = IterTable.next();

      String tableName = entry.getKey();

      TableInfoDTO tableMsg = context.getTableMap().get(tableName);

      StringBuilder sb = new StringBuilder();
      List<TableColumnDTO> tcolumnList = entry.getValue();

      // 获取当前主键列表
      List<TableColumnDTO> primaryKeyList = getPrimaryKey(tcolumnList);

      String beanParam = basePackage + "dto." + toJavaClassName(tableName) + "DTO";

      sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NEXT_LINE);
      sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" " + NEXT_LINE);
      sb.append("\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">" + NEXT_LINE);

      sb.append("<!--").append(tableMsg.getTableComment());
      sb.append("(").append(tableName).append(")数据库操作").append("-->").append(NEXT_LINE);

      sb.append(
          "<mapper namespace=\""
              + mybatisNamespace
              + "dao."
              + toJavaClassName(tableName)
              + "DAO\"> "
              + NEXT_LINE);

      // 定义resultmap信息
      sb.append(formatMsg(1)).append("<resultMap type=\"").append(beanParam).append("\"");

      String resultMapId = this.toJava(tableName) + "ResultMap";

      sb.append(" id=\"").append(resultMapId).append("\">").append(NEXT_LINE);

      List<TableColumnDTO> delKeyList = tcolumnList;

      for (TableColumnDTO tableColumn : primaryKeyList) {

        sb.append(formatMsg(2))
            .append("<!--")
            .append(tableColumn.getColumnMsg())
            .append("-->")
            .append(NEXT_LINE);

        sb.append(formatMsg(2))
            .append("<id property=\"")
            .append(toJava(tableColumn.getColumnName()))
            .append("\" ");
        sb.append("column=\"").append(tableColumn.getColumnName()).append("\"/>").append(NEXT_LINE);

        // 是否为自增长主键
        if (tableColumn.isAutoIncrement()) {
          // 去队主键
          delKeyList.remove(tableColumn);
        }
      }

      for (int i = 0; i < delKeyList.size(); i++) {
        sb.append(formatMsg(2))
            .append("<!--")
            .append(delKeyList.get(i).getColumnMsg())
            .append("-->")
            .append(NEXT_LINE);
        sb.append(formatMsg(2))
            .append("<result property=\"")
            .append(toJava(delKeyList.get(i).getColumnName()))
            .append("\"");
        sb.append(" column=\"")
            .append(delKeyList.get(i).getColumnName())
            .append("\"/>")
            .append(NEXT_LINE);
      }

      sb.append(formatMsg(1)).append("</resultMap>").append(NEXT_LINE);

      // 添加
      sb.append(formatMsg(1)).append("<!--").append(tableMsg.getTableComment());
      sb.append("(").append(tableName).append(")添加操作").append("-->").append(NEXT_LINE);
      sb.append(formatMsg(1))
          .append("<insert id=\"insert\" parameterType=\"" + beanParam + "\">" + NEXT_LINE);
      sb.append(formatMsg(2)).append("insert into " + tableName + " " + NEXT_LINE);
      sb.append(formatMsg(2))
          .append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">")
          .append(NEXT_LINE);

      for (int i = 0; i < tcolumnList.size(); i++) {
        TableColumnDTO tableMaper = tcolumnList.get(i);
        String coumnName = tableMaper.getColumnName();
        String javaName = toJava(coumnName);
        // 添加列注释信息
        sb.append(formatMsg(3))
            .append("<!--")
            .append(tableMaper.getColumnMsg())
            .append("-->")
            .append(NEXT_LINE);

        // 进行类型检查判断
        String switchStr = this.switchType(tableMaper, javaName);

        sb.append(formatMsg(3)).append("<if test=\" " + switchStr + " \">" + NEXT_LINE);
        sb.append(formatMsg(4)).append(coumnName).append(",").append(NEXT_LINE);
        sb.append(formatMsg(3)).append("</if>" + NEXT_LINE);
      }
      sb.append(formatMsg(2)).append("</trim>" + NEXT_LINE);
      sb.append(formatMsg(2))
          .append("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">")
          .append(NEXT_LINE);
      for (int i = 0; i < tcolumnList.size(); i++) {
        TableColumnDTO tableMaper = tcolumnList.get(i);
        String coumnName = tableMaper.getColumnName();
        String javaName = toJava(coumnName);
        String typeName = tableMaper.getDataType();
        // 添加列注释信息
        sb.append(formatMsg(3))
            .append("<!--")
            .append(tableMaper.getColumnMsg())
            .append("-->")
            .append(NEXT_LINE);

        // 进行类型检查判断
        String switchStr = this.switchType(tableMaper, javaName);

        // 添加列的值信息
        sb.append(formatMsg(3)).append("<if test=\" " + switchStr + " \">").append(NEXT_LINE);
        sb.append(formatMsg(4)).append("#{" + javaName + ",jdbcType=" + typeName + "}");
        sb.append(",");
        sb.append(NEXT_LINE);
        sb.append(formatMsg(3)).append("</if>" + NEXT_LINE);
      }
      sb.append(formatMsg(2)).append("</trim>" + NEXT_LINE);
      sb.append(formatMsg(1)).append("</insert>" + NEXT_LINE);
      sb.append(NEXT_LINE);

      // 修改

      sb.append(formatMsg(1)).append("<!--").append(tableMsg.getTableComment());
      sb.append("(").append(tableName).append(")修改操作").append("-->").append(NEXT_LINE);

      sb.append(formatMsg(1))
          .append("<update id=\"update\" parameterType=\"" + beanParam + "\">" + NEXT_LINE);
      sb.append(formatMsg(2)).append("update ").append(tableName).append(NEXT_LINE);
      sb.append(formatMsg(2)).append("<set>").append(NEXT_LINE);
      sb.append(formatMsg(3)).append("<trim  suffixOverrides=\",\">").append(NEXT_LINE);
      for (int i = 0; i < tcolumnList.size(); i++) {
        TableColumnDTO tableMaper = tcolumnList.get(i);
        String coumnName = tableMaper.getColumnName();
        String javaName = toJava(coumnName);
        String typeName = tableMaper.getDataType();
        // 添加列注释信息
        sb.append(formatMsg(4))
            .append("<!--")
            .append(tableMaper.getColumnMsg())
            .append("-->")
            .append(NEXT_LINE);

        // 进行类型检查判断
        String switchStr = this.switchType(tableMaper, javaName);

        // 修改对应的值信息
        sb.append(formatMsg(4)).append("<if test=\" " + switchStr + " \">").append(NEXT_LINE);
        sb.append(formatMsg(5))
            .append(" " + coumnName + " = #{" + javaName + ",jdbcType=" + typeName + "},")
            .append(NEXT_LINE);
        ;
        sb.append(formatMsg(4)).append("</if>").append(NEXT_LINE);
      }
      sb.append(formatMsg(3)).append("</trim>").append(NEXT_LINE);
      sb.append(formatMsg(2)).append("</set>").append(NEXT_LINE);
      sb.append(formatMsg(2)).append("<where> ").append(NEXT_LINE);
      // 以主键做条件
      sb.append(this.addDeleteWhere(primaryKeyList, 3));

      sb.append(formatMsg(2)).append("</where>").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("</update>" + NEXT_LINE);

      sb.append(NEXT_LINE);

      // 删除
      sb.append(formatMsg(1)).append("<!--").append(tableMsg.getTableComment());
      sb.append("(").append(tableName).append(")删除操作").append("-->").append(NEXT_LINE);

      sb.append(formatMsg(1))
          .append("<delete id=\"delete\" parameterType=\"" + beanParam + "\">" + NEXT_LINE);
      sb.append(formatMsg(2)).append("delete from  ");
      sb.append(tableName).append(NEXT_LINE);
      sb.append(formatMsg(2)).append("<where>" + NEXT_LINE);
      // 以主键做条件
      sb.append(this.addDeleteWhere(primaryKeyList, 3));
      sb.append(formatMsg(2)).append("</where>").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("</delete>").append(NEXT_LINE);

      sb.append(NEXT_LINE);

      // 查询
      sb.append(formatMsg(1)).append("<!--").append(tableMsg.getTableComment());
      sb.append("(").append(tableName).append(")查询操作").append("-->").append(NEXT_LINE);

      sb.append(formatMsg(1))
          .append(
              "<select id=\"query\" parameterType=\""
                  + beanParam
                  + "\" resultMap=\""
                  + resultMapId
                  + "\">"
                  + NEXT_LINE);
      sb.append(formatMsg(2)).append("select ").append(NEXT_LINE);

      for (int i = 0; i < tcolumnList.size(); i++) {
        String coumnName = tcolumnList.get(i).getColumnName();
        sb.append(formatMsg(2)).append(coumnName);
        if (i != tcolumnList.size() - 1) {
          sb.append("," + NEXT_LINE);
        }
      }
      sb.append(NEXT_LINE);
      sb.append(formatMsg(2)).append("  from  ").append(NEXT_LINE);
      sb.append(formatMsg(2)).append(tableName).append(NEXT_LINE);
      sb.append(formatMsg(2)).append("<where>").append(NEXT_LINE);
      for (int i = 0; i < tcolumnList.size(); i++) {
        // 添加列注释信息
        sb.append(formatMsg(3)).append("<!--").append(tcolumnList.get(i).getColumnMsg());
        sb.append("-->").append(NEXT_LINE);

        sb.append(this.addWhere(tcolumnList.get(i), 3));
      }
      sb.append(formatMsg(2)).append("</where>" + NEXT_LINE);
      sb.append(formatMsg(1)).append("</select>").append(NEXT_LINE);
      sb.append(NEXT_LINE);

      //      // 分页查询
      //      sb.append(formatMsg(1)).append("<!--").append(tableMsg.getTableComment());
      //      sb.append("(").append(tableName).append(")分页查询操作").append("-->").append(NEXT_LINE);
      //
      //      sb.append(formatMsg(1))
      //          .append(
      //              "<select id=\"page\" parameterType=\""
      //                  + beanParam
      //                  + "\" resultMap=\""
      //                  + resultMapId
      //                  + "\">")
      //          .append(NEXT_LINE);
      //      sb.append(formatMsg(2)).append("select ").append(NEXT_LINE);
      //      for (int i = 0; i < tcolumnList.size(); i++) {
      //        String coumnName = tcolumnList.get(i).getColumnName();
      //        sb.append(formatMsg(2)).append(coumnName);
      //        if (i != tcolumnList.size() - 1) {
      //          sb.append("," + NEXT_LINE);
      //        }
      //      }
      //      sb.append(NEXT_LINE);
      //      sb.append(formatMsg(2)).append("  from  ").append(NEXT_LINE);
      //      sb.append(formatMsg(2)).append(tableName).append(NEXT_LINE);
      //      sb.append(formatMsg(2)).append("<where>" + NEXT_LINE);
      //      for (int i = 0; i < tcolumnList.size(); i++) {
      //        // 添加列注释信息
      //        sb.append(formatMsg(3)).append("<!--").append(tcolumnList.get(i).getColumnMsg());
      //        sb.append("-->").append(NEXT_LINE);
      //        sb.append(this.addWhere(tcolumnList.get(i), 3));
      //      }
      //      sb.append(formatMsg(2)).append("</where>" + NEXT_LINE);
      //      sb.append(formatMsg(2)).append("limit #{pageStart},#{pageSize}").append(NEXT_LINE);
      //      sb.append(formatMsg(1)).append("</select>" + NEXT_LINE);
      //
      //      sb.append(NEXT_LINE);
      //
      //      // 统计
      //      sb.append(formatMsg(1)).append("<!--").append(tableMsg.getTableComment());
      //      sb.append("(").append(tableName).append(")分页查询的统计操作").append("-->").append(NEXT_LINE);
      //
      //      sb.append(formatMsg(1))
      //          .append(
      //              "<select id=\"pageCount\" resultType=\"long\" parameterType=\""
      //                  + beanParam
      //                  + "\">"
      //                  + NEXT_LINE);
      //      sb.append(formatMsg(2)).append(" select count(*)  ").append(NEXT_LINE);
      //      sb.append(formatMsg(2)).append("from ").append(tableName).append(NEXT_LINE);
      //      sb.append(formatMsg(2)).append("<where>" + NEXT_LINE);
      //      for (int i = 0; i < tcolumnList.size(); i++) {
      //        // 添加列注释信息
      //        sb.append(formatMsg(3)).append("<!--").append(tcolumnList.get(i).getColumnMsg());
      //        sb.append("-->").append(NEXT_LINE);
      //        sb.append(this.addWhere(tcolumnList.get(i), 3));
      //      }
      //      sb.append(formatMsg(2)).append("</where>" + NEXT_LINE);
      //      sb.append(formatMsg(1)).append("</select>" + NEXT_LINE);

      sb.append("</mapper>");

      FileWriter fw = new FileWriter(new File(path + toJavaClassName(tableName) + "Mapper.xml"));
      fw.write(sb.toString());
      fw.close();
    }
  }

  /**
   * 添加条件信息信息
   *
   * @param primaryKeyList 主键列表
   * @param tabNum
   * @return
   */
  protected String addWhere(List<TableColumnDTO> primaryKeyList, int tabNum) {

    StringBuilder sb = new StringBuilder();

    for (TableColumnDTO tableMaper : primaryKeyList) {
      // 添加条件
      sb.append(this.addWhere(tableMaper, tabNum));
    }

    return sb.toString();
  }

  /**
   * 添加条件信息信息
   *
   * @param tableColumn 列信息
   * @param tabNum
   * @return
   */
  protected String addWhere(TableColumnDTO tableColumn, int tabNum) {

    StringBuilder sb = new StringBuilder();

    String coumnName = tableColumn.getColumnName();
    String javaName = toJava(coumnName);
    String typeName = tableColumn.getDataType();

    // 获取类型判断信息
    String switchStr = this.switchType(tableColumn, javaName);
    sb.append(formatMsg(tabNum)).append("<if test=\" " + switchStr + " \">" + NEXT_LINE);
    sb.append(formatMsg(tabNum + 1))
        .append(
            "and " + coumnName + " = #{" + javaName + ",jdbcType=" + typeName + "}" + NEXT_LINE);
    sb.append(formatMsg(tabNum)).append("</if>" + NEXT_LINE);

    return sb.toString();
  }

  /**
   * 添加删除的条件信息信息，仅使用主键删除
   *
   * @param primaryKeyList 主键列表明
   * @param tabNum
   * @return
   */
  protected String addDeleteWhere(List<TableColumnDTO> primaryKeyList, int tabNum) {

    StringBuilder sb = new StringBuilder();

    TableColumnDTO column = null;

    for (int i = 0; i < primaryKeyList.size(); i++) {
      column = primaryKeyList.get(i);
      String coumnName = column.getColumnName();
      String javaName = toJava(coumnName);
      String typeName = column.getDataType();

      sb.append(formatMsg(tabNum + 1));
      // 添加条件的连接符
      if (i > 0) {
        sb.append(Symbol.SPACE);
        sb.append(SqlKeyEnum.AND.getKey());
        sb.append(Symbol.SPACE);
      }

      sb.append(coumnName + " = #{" + javaName + ",jdbcType=" + typeName + "}" + NEXT_LINE);
    }
    return sb.toString();
  }

  @Override
  public void autoCode(CreateParamBean param) {
    String daoImplPath = param.getFileBasePath() + "beanMapper/";

    try {
      this.mybatisCode(
          daoImplPath,
          param.getMybatisBaseSpace(),
          param.getTableSpaceName(),
          param.getJavaPackage(),
          param.getContext());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 进行map中的字符及其他的处理
   *
   * @param map
   * @return
   */
  private Map<String, List<TableColumnDTO>> parseComment(Map<String, List<TableColumnDTO>> map) {
    Map<String, List<TableColumnDTO>> result = new HashMap<>(map.size());

    for (Entry<String, List<TableColumnDTO>> entry : map.entrySet()) {
      List<TableColumnDTO> dtoList = new ArrayList<>(entry.getValue().size());
      for (TableColumnDTO columnDto : entry.getValue()) {
        TableColumnDTO newColumnDto = new TableColumnDTO();
        newColumnDto.setDataType(columnDto.getDataType());
        newColumnDto.setTableName(columnDto.getTableName());
        newColumnDto.setAutoIncrement(columnDto.isAutoIncrement());
        newColumnDto.setDataLength(columnDto.getDataLength());
        newColumnDto.setDataScale(columnDto.getDataScale());
        newColumnDto.setColumnMsg(this.specialChar(columnDto.getColumnMsg()));
        newColumnDto.setColumnName(columnDto.getColumnName());
        newColumnDto.setPrimaryKey(columnDto.isPrimaryKey());
        dtoList.add(newColumnDto);
      }
      result.put(entry.getKey(), dtoList);
    }

    return result;
  }

  /**
   * 进行类型的判断
   *
   * @param tableMaper
   * @param javaPropertiesName
   */
  private String switchType(TableColumnDTO tableMaper, String javaPropertiesName) {

    String switchStr = null;
    // 检查类型是否为varchar类型
    DatabaseTypeMsgBO typeBo =
        DataTypeResource.INSTANCE.getTargetTypeinfo(
            DatabaseTypeEnum.DATABASE_MYSQL, DatabaseTypeSourceEnum.DATABASE_TYPE_VARCHAR);

    boolean varcharFlag =
        typeBo.getDbType().equalsIgnoreCase(tableMaper.getDataType()) ? true : false;

    // 检查类型是否为timestamp类型
    DatabaseTypeMsgBO timestampBo =
        DataTypeResource.INSTANCE.getTargetTypeinfo(
            DatabaseTypeEnum.DATABASE_MYSQL, DatabaseTypeSourceEnum.DATABASE_TYPE_TIMESTAMP);

    boolean timestampFlag =
        timestampBo.getDbType().equalsIgnoreCase(tableMaper.getDataType()) ? true : false;

    // 检查char类型
    DatabaseTypeMsgBO charBo =
        DataTypeResource.INSTANCE.getTargetTypeinfo(
            DatabaseTypeEnum.DATABASE_MYSQL, DatabaseTypeSourceEnum.DATABASE_TYPE_CHAR);

    boolean charFlag = charBo.getDbType().equalsIgnoreCase(tableMaper.getDataType()) ? true : false;

    if (varcharFlag || timestampFlag || charFlag) {
      switchStr = javaPropertiesName + " != null and '' != " + javaPropertiesName;
    } else {
      switchStr = javaPropertiesName + " != null ";
    }

    return switchStr;
  }
}

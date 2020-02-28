// package com.kk.autocode.encode.code.map;
//
// import java.io.File;
// import java.io.FileWriter;
// import java.util.Iterator;
// import java.util.List;
// import java.util.Map;
// import java.util.Map.Entry;
//
// import com.kk.autocode.encode.base.TableProcessBase;
// import com.kk.autocode.encode.bean.CreateParamBean;
// import com.kk.element.database.mysql.pojo.TableColumnDTO;
// import com.kk.autocode.encode.code.AutoCodeInf;
//
// public class JavaCodeMyBatisMapperCreate extends TableProcessBase implements AutoCodeInf {
//
//  /**
//   * 方法描述
//   *
//   * @param args
//   * @throws Exception @创建日期 2016年9月28日
//   */
//  public static void main(String[] args) throws Exception {
//
//    JavaCodeMyBatisMapperCreate instance = new JavaCodeMyBatisMapperCreate();
//
//    args = new String[4];
//
//    args[0] = "D:/java/encode/a10/mapMapper/";
//    args[1] = "com.a10.resource.phone.";
//    args[2] = "a10";
//
//    // 调用生成mybatis的代码
//    instance.mybatisCode(args[0], args[1], args[2]);
//  }
//
//  /**
//   * 生成mybatis的代码 方法描述
//   *
//   * @throws Exception @创建日期 2016年9月28日
//   */
//  private void mybatisCode(String path, String nameSpace, String tableSpaceName) throws Exception
// {
//
//    File dirFile = new File(path);
//    // 如果文件夹存在，则执行删除
//    boolean exists = dirFile.exists();
//    if (exists) {
//      dirFile.delete();
//    }
//    dirFile.mkdirs();
//
//    // 获得表信息
//    Map<String, List<TableColumnDTO>> map = this.getTableColumnInfoByMap(tableSpaceName);
//
//    Iterator<Entry<String, List<TableColumnDTO>>> IterTable = map.entrySet().iterator();
//    while (IterTable.hasNext()) {
//
//      Entry<String, List<TableColumnDTO>> entry = IterTable.next();
//
//      String tableName = entry.getKey();
//
//      StringBuffer sb = new StringBuffer();
//      List<TableColumnDTO> tcolumnList = entry.getValue();
//
//      // 获取当前主键
//      List<TableColumnDTO> primaryKeyBean = getPrimaryKey(tcolumnList);
//
//      // 主键的列与对应的值
//      // String idCoumnName = primaryKey.getColumnName();
//      // String idJavaName = this.toJava(idCoumnName);
//
//      sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NEXT_LINE);
//      sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" " + NEXT_LINE);
//      sb.append("\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">" + NEXT_LINE);
//      sb.append(
//          "<mapper namespace=\""
//              + nameSpace
//              + toJavaClassName(tableName)
//              + "Mapper\"> "
//              + NEXT_LINE);
//      // 添加
//      sb.append(formatMsg(1))
//          .append("<!--")
//          .append(tableName)
//          .append("添加操作")
//          .append("-->")
//          .append(NEXT_LINE);
//      sb.append(formatMsg(1)).append("<insert id=\"insert\" parameterType=\"map\">" + NEXT_LINE);
//      sb.append(formatMsg(2)).append("insert into " + tableName + " " + NEXT_LINE);
//      sb.append(formatMsg(2))
//          .append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">")
//          .append(NEXT_LINE);
//      for (int i = 0; i < tcolumnList.size(); i++) {
//        TableColumnDTO tableMaper = tcolumnList.get(i);
//        String coumnName = tableMaper.getColumnName();
//        String javaName = toJava(coumnName);
//        // 添加列注释信息
//        sb.append(formatMsg(3))
//            .append("<!--")
//            .append(tableMaper.getColumnMsg())
//            .append("-->")
//            .append(NEXT_LINE);
//        sb.append(formatMsg(3)).append("<if test=\"" + javaName + " != null\">" + NEXT_LINE);
//        sb.append(formatMsg(4)).append(coumnName).append("," + NEXT_LINE);
//        sb.append(formatMsg(3)).append("</if>" + NEXT_LINE);
//      }
//      sb.append(formatMsg(2)).append("</trim>" + NEXT_LINE);
//      sb.append(formatMsg(2))
//          .append("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">")
//          .append(NEXT_LINE);
//      for (int i = 0; i < tcolumnList.size(); i++) {
//        TableColumnDTO tableMaper = tcolumnList.get(i);
//        String coumnName = tableMaper.getColumnName();
//        String javaName = toJava(coumnName);
//        String typeName = tableMaper.getDataType();
//        // 添加列注释信息
//        sb.append(formatMsg(3))
//            .append("<!--")
//            .append(tableMaper.getColumnMsg())
//            .append("-->")
//            .append(NEXT_LINE);
//        // 添加列的值信息
//        sb.append(formatMsg(3)).append("<if test=\"" + javaName + " != null\">" + NEXT_LINE);
//        sb.append(formatMsg(4)).append("#{" + javaName + ",jdbcType=" + typeName +
// "}").append(",");
//        sb.append(NEXT_LINE);
//        sb.append(formatMsg(3)).append("</if>" + NEXT_LINE);
//      }
//      sb.append(formatMsg(2)).append("</trim>" + NEXT_LINE);
//      sb.append(formatMsg(1)).append("</insert>" + NEXT_LINE);
//
//      sb.append(NEXT_LINE);
//      // 修改
//      sb.append(formatMsg(1))
//          .append("<!--")
//          .append(tableName)
//          .append("修改操作")
//          .append("-->")
//          .append(NEXT_LINE);
//      sb.append(formatMsg(1)).append("<update id=\"update\" parameterType=\"map\">" + NEXT_LINE);
//      sb.append(formatMsg(2)).append("update ").append(tableName).append(NEXT_LINE);
//      sb.append(formatMsg(2)).append("<set>" + NEXT_LINE);
//      sb.append(formatMsg(3)).append("<trim  suffixOverrides=\",\">").append(NEXT_LINE);
//      for (int i = 0; i < tcolumnList.size(); i++) {
//        TableColumnDTO tableMaper = tcolumnList.get(i);
//        String coumnName = tableMaper.getColumnName();
//        String javaName = toJava(coumnName);
//        String typeName = tableMaper.getDataType();
//        // 添加列注释信息
//        sb.append(formatMsg(3))
//            .append("<!--")
//            .append(tableMaper.getColumnMsg())
//            .append("-->")
//            .append(NEXT_LINE);
//        // 修改对应的值信息
//        sb.append(formatMsg(3)).append("<if test=\"" + javaName + " != null\">" + NEXT_LINE);
//        sb.append(formatMsg(4))
//            .append(
//                " " + coumnName + " = #{" + javaName + ",jdbcType=" + typeName + "}," +
// NEXT_LINE);
//        sb.append(formatMsg(3)).append("</if>" + NEXT_LINE);
//      }
//      sb.append(formatMsg(3)).append("</trim>").append(NEXT_LINE);
//      sb.append(formatMsg(2)).append("</set>" + NEXT_LINE);
//      sb.append(formatMsg(2)).append("where ").append(NEXT_LINE);
//      // 添加列注释信息
//      sb.append(formatMsg(2))
//          .append("<!--")
//          .append(primaryKey.getColumnMsg())
//          .append("-->")
//          .append(NEXT_LINE);
//      // 添加列值信息
//      sb.append(formatMsg(2)).append(idCoumnName + "=#{" + idJavaName + "}  ").append(NEXT_LINE);
//      sb.append(formatMsg(1)).append("</update>");
//      sb.append(NEXT_LINE);
//      sb.append(NEXT_LINE);
//
//      // 删除
//      sb.append(formatMsg(1))
//          .append("<!--")
//          .append(tableName)
//          .append("删除操作")
//          .append("-->")
//          .append(NEXT_LINE);
//      sb.append(formatMsg(1)).append("<delete id=\"delete\" parameterType=\"map\">" + NEXT_LINE);
//      sb.append(formatMsg(2)).append("delete from  ").append(tableName).append(NEXT_LINE);
//      sb.append(formatMsg(2)).append("<where>" + NEXT_LINE);
//      sb.append(formatMsg(3)).append(idCoumnName + "=#{" + idJavaName + "}    " + NEXT_LINE);
//      sb.append(formatMsg(2)).append("</where>" + NEXT_LINE);
//      sb.append(formatMsg(1)).append("</delete>" + NEXT_LINE);
//      sb.append(NEXT_LINE);
//
//      // 查询
//      sb.append(formatMsg(1))
//          .append("<!--")
//          .append(tableName)
//          .append("查询操作")
//          .append("-->")
//          .append(NEXT_LINE);
//      sb.append(formatMsg(1))
//          .append("<select id=\"query\" parameterType=\"map\" resultType=\"map\">" + NEXT_LINE);
//      sb.append(formatMsg(2)).append("select ").append(NEXT_LINE);
//
//      // 查询输出列时需要主键
//      // if (primaryKeyBean != null) {
//      // tcolumnList.add(0, primaryKeyBean);
//      // }
//
//      for (int i = 0; i < tcolumnList.size(); i++) {
//        String coumnName = tcolumnList.get(i).getColumnName();
//        sb.append(formatMsg(2)).append(coumnName);
//        if (i != tcolumnList.size() - 1) {
//          sb.append(",");
//        }
//        sb.append(NEXT_LINE);
//      }
//      sb.append(formatMsg(2)).append(" from  ").append(tableName).append(NEXT_LINE);
//      sb.append(formatMsg(2)).append("<where>" + NEXT_LINE);
//      for (int i = 0; i < tcolumnList.size(); i++) {
//        TableColumnDTO tableMaper = tcolumnList.get(i);
//
//        String coumnName = tableMaper.getColumnName();
//        String javaName = toJava(coumnName);
//        String typeName = tableMaper.getDataType();
//
//        // 添加列注释信息
//        sb.append(formatMsg(3))
//            .append("<!--")
//            .append(tableMaper.getColumnMsg())
//            .append("-->")
//            .append(NEXT_LINE);
//
//        sb.append(formatMsg(3)).append("<if test=\"" + javaName + " != null\">" + NEXT_LINE);
//        sb.append(formatMsg(4))
//            .append(
//                "and "
//                    + coumnName
//                    + " = #{"
//                    + javaName
//                    + ",jdbcType="
//                    + typeName
//                    + "}"
//                    + NEXT_LINE);
//        sb.append(formatMsg(3)).append("</if>" + NEXT_LINE);
//      }
//      sb.append(formatMsg(2)).append("</where>" + NEXT_LINE);
//      sb.append(formatMsg(1)).append("</select>" + NEXT_LINE);
//
//      sb.append(NEXT_LINE);
//
//      // 分页查询
//      sb.append(formatMsg(1))
//          .append("<!--")
//          .append(tableName)
//          .append("分页查询操作")
//          .append("-->")
//          .append(NEXT_LINE);
//      sb.append(formatMsg(1))
//          .append("<select id=\"page\" parameterType=\"map\" resultType=\"map\">" + NEXT_LINE);
//      sb.append(formatMsg(2)).append("select ").append(NEXT_LINE);
//      for (int i = 0; i < tcolumnList.size(); i++) {
//        String coumnName = tcolumnList.get(i).getColumnName();
//        sb.append(formatMsg(2)).append(coumnName);
//        if (i != tcolumnList.size() - 1) {
//          sb.append(",");
//        }
//        sb.append(NEXT_LINE);
//      }
//      sb.append(formatMsg(2)).append(" from  ").append(tableName).append(NEXT_LINE);
//      sb.append(formatMsg(2)).append("<where>" + NEXT_LINE);
//      for (int i = 0; i < tcolumnList.size(); i++) {
//        TableColumnDTO tableMaper = tcolumnList.get(i);
//
//        String coumnName = tableMaper.getColumnName();
//        String javaName = toJava(coumnName);
//        String typeName = tableMaper.getDataType();
//
//        // 添加列注释信息
//        sb.append(formatMsg(3))
//            .append("<!--")
//            .append(tableMaper.getColumnMsg())
//            .append("-->")
//            .append(NEXT_LINE);
//
//        sb.append(formatMsg(3)).append("<if test=\"" + javaName + " != null\">" + NEXT_LINE);
//        sb.append(formatMsg(4))
//            .append(
//                "and "
//                    + coumnName
//                    + " = #{"
//                    + javaName
//                    + ",jdbcType="
//                    + typeName
//                    + "}"
//                    + NEXT_LINE);
//        sb.append(formatMsg(3)).append("</if>" + NEXT_LINE);
//      }
//      sb.append(formatMsg(2)).append("</where>" + NEXT_LINE);
//      sb.append(formatMsg(1)).append("</select>" + NEXT_LINE);
//      sb.append(NEXT_LINE);
//
//      // 统计
//      sb.append(formatMsg(1))
//          .append("<!--")
//          .append(tableName)
//          .append("统计操作")
//          .append("-->")
//          .append(NEXT_LINE);
//      sb.append(formatMsg(1))
//          .append(
//              "<select id=\"pageCount\" resultType=\"long\" parameterType=\"map\">" + NEXT_LINE);
//      sb.append(formatMsg(2)).append(" select count(*) from ");
//      sb.append(tableName).append(NEXT_LINE);
//      sb.append(formatMsg(2)).append("<where>" + NEXT_LINE);
//      for (int i = 0; i < tcolumnList.size(); i++) {
//
//        TableColumnDTO tableMaper = tcolumnList.get(i);
//
//        String coumnName = tableMaper.getColumnName();
//        String javaName = toJava(coumnName);
//        String typeName = tableMaper.getDataType();
//
//        sb.append(formatMsg(3))
//            .append("<!--")
//            .append(tableMaper.getColumnMsg())
//            .append("-->")
//            .append(NEXT_LINE);
//        sb.append(formatMsg(3)).append("<if test=\"" + javaName + " != null\">" + NEXT_LINE);
//        sb.append(formatMsg(4))
//            .append(
//                "and "
//                    + coumnName
//                    + " = #{"
//                    + javaName
//                    + ",jdbcType="
//                    + typeName
//                    + "}"
//                    + NEXT_LINE);
//        sb.append(formatMsg(3)).append("</if>" + NEXT_LINE);
//      }
//      sb.append(formatMsg(2)).append("</where>" + NEXT_LINE);
//      sb.append(formatMsg(1)).append("</select>" + NEXT_LINE);
//
//      sb.append("</mapper>");
//
//      FileWriter fw = new FileWriter(new File(path + toJavaClassName(tableName) + "Mapper.xml"));
//      fw.write(sb.toString());
//      fw.close();
//    }
//  }
//
//  @Override
//  public void autoCode(CreateParamBean param) {
//    String daoImplPath = param.getFileBasePath() + "MapMapper/";
//
//    try {
//      this.mybatisCode(daoImplPath, param.getMybatisBaseSpace(), param.getTableSpaceName());
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
// }

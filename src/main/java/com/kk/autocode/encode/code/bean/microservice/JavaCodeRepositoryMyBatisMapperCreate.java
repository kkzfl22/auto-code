package com.kk.autocode.encode.code.bean.microservice;

import com.kk.autocode.encode.base.TableProcessBase;
import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.bean.EncodeContext;
import com.kk.autocode.encode.code.AutoCodeInf;
import com.kk.autocode.encode.constant.SqlKeyEnum;
import com.kk.autocode.encode.constant.Symbol;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;
import com.kk.element.database.type.DataTypeResource;
import com.kk.element.database.type.constant.DatabaseTypeEnum;
import com.kk.element.database.type.constant.DatabaseTypeSourceEnum;
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
public class JavaCodeRepositoryMyBatisMapperCreate extends TableProcessBase implements AutoCodeInf {

  public static final String DOC = "数据库操作";

  private static final String NAMESPACE_START = "<mapper namespace=\"";
  private static final String RESULTMAP = "<resultMap type=\"";
  private static final String RESULTMAP_END = "</resultMap>";
  private static final String REUSLT_SUFFIX_NAME = "ResultMap";
  private static final String ID = "id";
  private static final String PROPERTY = "property";
  private static final String COLUMN = "column";
  private static final String RESULT = "<result ";
  private static final String IF_START = "<if test=\" ";
  private static final String IF_END = "</if>";
  private static final String OPER_ADD = "添加操作";
  private static final String OPER_UPD = "修改操作";
  private static final String OPER_DEL = "删除操作";
  private static final String OPER_QRY = "查询操作";
  private static final String INSERT_XML_START = "<insert id=\"insert\" parameterType=\"";
  private static final String INSERT_SQL_START = "insert into ";
  private static final String TRIM_XML_START =
      "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">";
  private static final String TRIM_VALUE_XML_START =
      "<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">";
  private static final String TRIM_XML_END = "</trim>";
  private static final String JDBC_TYPE = ",jdbcType=";
  private static final String INSERT_XML_END = "</insert>";
  private static final String UPDATE_XML_START = "<update id=\"update\" parameterType=\"";
  private static final String UPDATE_SQL_START = "update";
  private static final String UPDDATE_XML_END = "</update>";
  private static final String UPDATE_SET = "<set>";
  private static final String UPDATE_SET_END = "</set>";
  private static final String UPDATE_TRIM_START = "<trim  suffixOverrides=\",\">";
  private static final String WHERE_START = "<where> ";
  private static final String WHERE_END = "</where> ";

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
      // 获取po的完整路径
      String beanParam =
          basePackage
              + JavaCodeRepositoryPoCreate.REPOSITORY_PO_PACKAGE
              + toJavaClassName(tableName)
              + JavaCodeRepositoryPoCreate.REPOSITORY_PO;
      // 定义头
      sb.append(MyBatisKey.HEAD).append(NEXT_LINE);
      // xml文件头定义
      sb.append(MyBatisKey.DEFINE).append(NEXT_LINE);
      sb.append(NEXT_LINE);
      // xml注释
      sb.append(MyBatisKey.DOC_START)
          .append(tableMsg.getTableComment())
          .append(Symbol.BRACKET_LEFT)
          .append(tableName)
          .append(Symbol.BRACKET_RIGHT)
          .append(DOC)
          .append(MyBatisKey.DOC_END)
          .append(NEXT_LINE);

      String javaClassName = toJavaClassName(tableName);

      // 基础包信息
      String packageStr =
          JavaCodeRepositoryDaoInfCreate.DAO_PACKAGE
              + Symbol.POINT
              + javaClassName
              + JavaCodeRepositoryDaoInfCreate.DAO_SUFFIX;

      sb.append(NAMESPACE_START)
          .append(mybatisNamespace)
          .append(packageStr)
          .append(MyBatisKey.END)
          .append(NEXT_LINE);

      String resultMapId = this.toJava(tableName) + REUSLT_SUFFIX_NAME;

      // 定义查询结果集
      queryResponse(sb, beanParam, resultMapId, tcolumnList, primaryKeyList);
      // 插入数据的方法
      insertMethod(sb, tableMsg, beanParam, tcolumnList);
      // 修改数据的方法
      updateMethod(sb, tableMsg, beanParam, tcolumnList, primaryKeyList);

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

  private void queryResponse(
      StringBuilder sb,
      String beanParam,
      String resultMapId,
      List<TableColumnDTO> tcolumnList,
      List<TableColumnDTO> primaryKeyList) {

    // 定义resultmap信息
    sb.append(formatMsg(1))
        .append(RESULTMAP)
        .append(beanParam)
        .append(Symbol.QUOTE)
        .append(Symbol.SPACE)
        .append(ID)
        .append(Symbol.EQUAL)
        .append(Symbol.QUOTE)
        .append(resultMapId)
        .append(Symbol.QUOTE)
        .append(MyBatisKey.END)
        .append(NEXT_LINE);

    List<TableColumnDTO> delKeyList = tcolumnList;
    // 定义主键信息
    for (TableColumnDTO tableColumn : primaryKeyList) {

      // 定义输出列注释
      sb.append(formatMsg(2))
          .append(MyBatisKey.DOC_START)
          .append(tableColumn.getColumnMsg())
          .append(MyBatisKey.DOC_END)
          .append(NEXT_LINE);

      // 定义列的对应
      sb.append(formatMsg(2))
          .append(MyBatisKey.START)
          .append(Symbol.SPACE)
          .append(PROPERTY)
          .append(Symbol.EQUAL)
          .append(Symbol.QUOTE)
          .append(toJava(tableColumn.getColumnName()))
          .append(Symbol.QUOTE)
          .append(Symbol.SPACE)
          .append(COLUMN)
          .append(Symbol.EQUAL)
          .append(Symbol.QUOTE)
          .append(tableColumn.getColumnName())
          .append(Symbol.QUOTE)
          .append(MyBatisKey.OVER)
          .append(NEXT_LINE);

      // 是否为自增长主键
      if (tableColumn.isAutoIncrement()) {
        // 去队主键
        delKeyList.remove(tableColumn);
      }
    }

    // 定义查询列信息
    for (int i = 0; i < delKeyList.size(); i++) {
      // 输出注释
      sb.append(formatMsg(2))
          .append(MyBatisKey.DOC_START)
          .append(delKeyList.get(i).getColumnMsg())
          .append(MyBatisKey.DOC_END)
          .append(NEXT_LINE);
      // 输出列信息
      sb.append(formatMsg(2))
          .append(RESULT)
          .append(PROPERTY)
          .append(Symbol.EQUAL)
          .append(Symbol.QUOTE)
          .append(toJava(delKeyList.get(i).getColumnName()))
          .append(Symbol.QUOTE)
          .append(Symbol.SPACE)
          .append(COLUMN)
          .append(Symbol.EQUAL)
          .append(Symbol.QUOTE)
          .append(delKeyList.get(i).getColumnName())
          .append(Symbol.QUOTE)
          .append(MyBatisKey.OVER)
          .append(NEXT_LINE);
    }

    // 结束
    sb.append(formatMsg(1)).append(RESULTMAP_END).append(NEXT_LINE);
  }

  private void insertMethod(
      StringBuilder sb, TableInfoDTO tableMsg, String beanParam, List<TableColumnDTO> tcolumnList) {
    // 添加操作的注释
    sb.append(formatMsg(1))
        .append(MyBatisKey.DOC_START)
        .append(tableMsg.getTableComment())
        .append(Symbol.BRACKET_LEFT)
        .append(tableMsg.getTableName())
        .append(Symbol.BRACKET_RIGHT)
        .append(OPER_ADD)
        .append(MyBatisKey.DOC_END)
        .append(NEXT_LINE);
    // 插入的开始语句
    sb.append(formatMsg(1))
        .append(INSERT_XML_START)
        .append(beanParam)
        .append(Symbol.QUOTE)
        .append(MyBatisKey.END)
        .append(NEXT_LINE);
    // 插入语句的开始
    sb.append(formatMsg(2))
        .append(INSERT_SQL_START)
        .append(tableMsg.getTableName())
        .append(Symbol.SPACE)
        .append(NEXT_LINE);
    // 清除多余的逗号
    sb.append(formatMsg(2)).append(TRIM_XML_START).append(NEXT_LINE);

    for (int i = 0; i < tcolumnList.size(); i++) {
      TableColumnDTO tableMaper = tcolumnList.get(i);
      String coumnName = tableMaper.getColumnName();
      String javaName = toJava(coumnName);
      // 添加列注释信息
      sb.append(formatMsg(3))
          .append(MyBatisKey.DOC_START)
          .append(tableMaper.getColumnMsg())
          .append(MyBatisKey.DOC_END)
          .append(NEXT_LINE);

      // 进行类型检查判断
      String switchStr = this.switchType(tableMaper, javaName);

      // 生成判断并添加列
      sb.append(formatMsg(3))
          .append(IF_START)
          .append(switchStr)
          .append(Symbol.QUOTE)
          .append(MyBatisKey.END);
      sb.append(formatMsg(4)).append(coumnName).append(Symbol.COMMA).append(NEXT_LINE);
      sb.append(formatMsg(3)).append(IF_END).append(NEXT_LINE);
    }
    sb.append(formatMsg(2)).append(TRIM_XML_END).append(NEXT_LINE);
    sb.append(formatMsg(2)).append(TRIM_VALUE_XML_START).append(NEXT_LINE);
    for (int i = 0; i < tcolumnList.size(); i++) {
      TableColumnDTO tableMaper = tcolumnList.get(i);
      String coumnName = tableMaper.getColumnName();
      String javaName = toJava(coumnName);
      String typeName = tableMaper.getDataType();
      // 添加列注释信息
      sb.append(formatMsg(3))
          .append(MyBatisKey.DOC_START)
          .append(tableMaper.getColumnMsg())
          .append(MyBatisKey.DOC_END)
          .append(NEXT_LINE);

      // 进行类型检查判断
      String switchStr = this.switchType(tableMaper, javaName);

      // 添加列的值信息
      sb.append(formatMsg(3))
          .append(IF_START)
          .append(switchStr)
          .append(Symbol.SPACE)
          .append(Symbol.QUOTE)
          .append(MyBatisKey.END)
          .append(NEXT_LINE);
      sb.append(formatMsg(4))
          .append(Symbol.POUND)
          .append(Symbol.BRACK_LEFT)
          .append(javaName)
          .append(JDBC_TYPE)
          .append(Symbol.BRACK_RIGHT);

      sb.append(Symbol.COMMA);
      sb.append(NEXT_LINE);
      sb.append(formatMsg(3)).append(IF_END).append(NEXT_LINE);
    }
    sb.append(formatMsg(2)).append(TRIM_XML_END).append(NEXT_LINE);
    sb.append(formatMsg(1)).append(INSERT_XML_END).append(NEXT_LINE);
    sb.append(NEXT_LINE);
  }

  /**
   * 进行生成修改数据方法
   *
   * @param sb
   * @param tableMsg
   * @param beanParam
   * @param tcolumnList
   * @param primaryKeyList
   */
  private void updateMethod(
      StringBuilder sb,
      TableInfoDTO tableMsg,
      String beanParam,
      List<TableColumnDTO> tcolumnList,
      List<TableColumnDTO> primaryKeyList) {
    // 修改
    sb.append(formatMsg(1))
        .append(MyBatisKey.DOC_START)
        .append(tableMsg.getTableComment())
        .append(Symbol.BRACKET_LEFT)
        .append(tableMsg.getTableName())
        .append(Symbol.BRACKET_RIGHT)
        .append(OPER_UPD)
        .append(MyBatisKey.DOC_END)
        .append(NEXT_LINE);

    sb.append(formatMsg(1))
        .append(UPDATE_XML_START)
        .append(beanParam)
        .append(Symbol.QUOTE)
        .append(MyBatisKey.END)
        .append(NEXT_LINE);

    // update SQL信息
    sb.append(formatMsg(2))
        .append(UPDATE_SQL_START)
        .append(Symbol.SPACE)
        .append(tableMsg.getTableName())
        .append(NEXT_LINE);
    // 设置
    sb.append(formatMsg(2)).append(UPDATE_SET).append(NEXT_LINE);
    // trim开始
    sb.append(formatMsg(3)).append(UPDATE_TRIM_START).append(NEXT_LINE);
    for (int i = 0; i < tcolumnList.size(); i++) {
      TableColumnDTO tableMaper = tcolumnList.get(i);
      String columnName = tableMaper.getColumnName();
      String javaName = toJava(columnName);
      String typeName = tableMaper.getDataType();
      // 添加列注释信息
      sb.append(formatMsg(4))
          .append(MyBatisKey.DOC_START)
          .append(tableMaper.getColumnMsg())
          .append(MyBatisKey.DOC_END)
          .append(NEXT_LINE);

      // 进行类型检查判断
      String switchStr = this.switchType(tableMaper, javaName);

      // 先判断
      sb.append(formatMsg(4))
          .append(IF_START)
          .append(switchStr)
          .append(Symbol.QUOTE)
          .append(MyBatisKey.END)
          .append(NEXT_LINE);
      // 修改对应的值信息
      sb.append(formatMsg(5))
          .append(Symbol.SPACE)
          .append(columnName)
          .append(Symbol.POUND)
          .append(Symbol.BRACK_LEFT)
          .append(javaName)
          .append(JDBC_TYPE)
          .append(typeName)
          .append(Symbol.BRACK_RIGHT)
          .append(Symbol.COMMA);

      sb.append(formatMsg(4)).append(IF_END).append(NEXT_LINE);
    }
    sb.append(formatMsg(3)).append(TRIM_XML_END).append(NEXT_LINE);
    sb.append(formatMsg(2)).append(UPDATE_SET_END).append(NEXT_LINE);
    sb.append(formatMsg(2)).append(WHERE_START).append(NEXT_LINE);
    // 以主键做条件
    sb.append(this.addDeleteWhere(primaryKeyList, 3));

    sb.append(formatMsg(2)).append(WHERE_END).append(NEXT_LINE);
    sb.append(formatMsg(1)).append(UPDDATE_XML_END).append(NEXT_LINE);

    sb.append(NEXT_LINE);
  }
}

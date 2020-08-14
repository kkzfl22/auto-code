package com.kk.autocode.encode.code.microservice;

import com.kk.autocode.encode.base.TableProcessBase;
import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.bean.EncodeContext;
import com.kk.autocode.encode.code.AutoCodeInf;
import com.kk.autocode.encode.constant.*;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;
import com.kk.element.database.type.DataTypeResource;
import com.kk.element.database.type.constant.DatabaseTypeEnum;
import com.kk.element.database.type.constant.DatabaseTypeSourceEnum;
import com.kk.element.database.type.pojo.bean.DatabaseTypeMsgBO;

import java.io.File;
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
  public static final String MYBATIS_SUFFIX_NAME = "Mapper.xml";
  public static final String OUT_DIR = "beanMapper";

  private static final String NAMESPACE_START = "<mapper namespace=\"";
  private static final String NAMESPACE_END = "</mapper>";
  private static final String RESULT_MAP = "<resultMap type=\"";
  private static final String RESULT_MAP_END = "</resultMap>";
  private static final String RESULT_SUFFIX_NAME = "ResultMap";
  private static final String ID = "id";
  private static final String PROPERTY = "property";
  private static final String ID_PROPERTY = "id property";
  private static final String COLUMN = "column";
  private static final String RESULT = "<result ";
  private static final String IF_START = "<if test=\" ";
  private static final String IF_END = "</if>";
  private static final String OPER_ADD = "添加操作";
  private static final String OPER_UPD = "修改操作";
  private static final String OPER_DEL = "删除操作";
  private static final String OPER_QRY = "查询操作";
  private static final String OPER_QRY_ID = "根据id查询操作";
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
  private static final String DELETE_XML_START = "<delete id=\"delete\" parameterType=\"";
  private static final String DELETE_XML_END = "</delete>";
  private static final String DELETE_SQL = "delete from  ";
  private static final String QUERY_XML_START = "<select id=\"queryPage\" parameterType=\"";
  private static final String QUERY_ID_XML_START = "<select id=\"queryById\" parameterType=\"";
  private static final String QUERY_XML_END = "</select>";
  private static final String QUERY_RSP_MAPPER = "\" resultMap=\"";
  private static final String QUERY_SQL = "select ";
  private static final String QUERY_KEY_FROM = " from ";
  private static final String KEY_AND = " and ";

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
    Map<String, List<TableColumnDTO>> srcMap = context.getColumnMap();

    // 获取列信息
    Map<String, List<TableColumnDTO>> map = this.parseComment(srcMap);

    Iterator<Entry<String, List<TableColumnDTO>>> IterTable = map.entrySet().iterator();
    while (IterTable.hasNext()) {
      Entry<String, List<TableColumnDTO>> entry = IterTable.next();
      String tableName = entry.getKey();
      TableInfoDTO tableMsg = context.getTableMap().get(tableName);
      StringBuilder sb = new StringBuilder();
      String javaClassName = toJavaClassName(tableName);
      String resultMapId = this.toJava(tableName) + RESULT_SUFFIX_NAME;

      List<TableColumnDTO> columnList = entry.getValue();
      // 获取当前主键列表
      List<TableColumnDTO> primaryKeyList = getPrimaryKey(columnList);
      // 获取po的完整路径
      String beanParam =
          basePackage
              + JavaCodeRepositoryPoCreate.REPOSITORY_PO_PACKAGE
              + Symbol.POINT
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

      // 基础包信息
      String packageStr =
          JavaCodeRepositoryDaoInfCreate.DAO_PACKAGE
              + Symbol.POINT
              + javaClassName
              + JavaCodeRepositoryDaoInfCreate.DAO_SUFFIX;

      // 定义命名空间
      sb.append(NAMESPACE_START)
          .append(mybatisNamespace)
          .append(packageStr)
          .append(Symbol.QUOTE)
          .append(MyBatisKey.END)
          .append(NEXT_LINE);

      // 定义查询结果集
      queryResponse(sb, beanParam, resultMapId, columnList, primaryKeyList);
      // 插入数据的方法
      insertMethod(sb, tableMsg, beanParam, columnList);
      // 修改数据的方法
      updateMethod(sb, tableMsg, beanParam, columnList, primaryKeyList);
      // 删除的方法
      deleteMethod(sb, tableMsg, beanParam, primaryKeyList);
      // 查询的方法
      queryPageMethod(sb, tableMsg, beanParam, resultMapId, columnList, primaryKeyList);

      // 当前查询如果为单主键，则添加根据id的查询方法
      if (primaryKeyList != null) {
        this.queryByIdMethod(sb, tableMsg, beanParam, resultMapId, columnList, primaryKeyList);
      }

      sb.append(NAMESPACE_END);

      String fileOut = path + toJavaClassName(tableName) + MYBATIS_SUFFIX_NAME;
      FileUtils.writeFile(fileOut, sb);
    }
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

    // 值的判断操作
    sb.append(formatMsg(tabNum))
        .append(IF_START)
        .append(switchStr)
        .append(Symbol.QUOTE)
        .append(MyBatisKey.END)
        .append(NEXT_LINE);
    // 添加where的条件项
    sb.append(formatMsg(tabNum + 1))
        .append(KEY_AND)
        .append(coumnName)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.POUND)
        .append(Symbol.BRACE_LEFT)
        .append(javaName)
        .append(JDBC_TYPE)
        .append(typeName)
        .append(Symbol.BRACE_RIGHT)
        .append(NEXT_LINE);

    sb.append(formatMsg(tabNum)).append(IF_END).append(NEXT_LINE);

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

      // 添加列相关的信息
      sb.append(coumnName)
          .append(Symbol.SPACE)
          .append(Symbol.EQUAL)
          .append(Symbol.SPACE)
          .append(Symbol.POUND)
          .append(Symbol.BRACE_LEFT)
          .append(javaName)
          .append(JDBC_TYPE)
          .append(typeName)
          .append(Symbol.BRACE_RIGHT)
          .append(NEXT_LINE);
    }
    return sb.toString();
  }

  @Override
  public void autoCode(CreateParamBean param) {
    String daoImplPath = param.getFileBasePath() + OUT_DIR + Symbol.PATH;

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
        newColumnDto.setNullFlag(columnDto.isNullFlag());
        newColumnDto.setDefaultValue(columnDto.getDefaultValue());
        dtoList.add(newColumnDto);
      }
      result.put(entry.getKey(), dtoList);
    }

    return result;
  }

  /**
   * 进行类型的判断
   *
   * @param tableMapper
   * @param javaPropertiesName
   */
  private String switchType(TableColumnDTO tableMapper, String javaPropertiesName) {

    String switchStr = null;
    // 检查类型是否为varchar类型
    DatabaseTypeMsgBO typeBo =
        DataTypeResource.INSTANCE.getTargetTypeinfo(
            DatabaseTypeEnum.DATABASE_MYSQL, DatabaseTypeSourceEnum.DATABASE_TYPE_VARCHAR);

    boolean varcharFlag =
        typeBo.getDbType().equalsIgnoreCase(tableMapper.getDataType()) ? true : false;

    // 检查类型是否为timestamp类型
    DatabaseTypeMsgBO timestampBo =
        DataTypeResource.INSTANCE.getTargetTypeinfo(
            DatabaseTypeEnum.DATABASE_MYSQL, DatabaseTypeSourceEnum.DATABASE_TYPE_TIMESTAMP);

    boolean timestampFlag =
        timestampBo.getDbType().equalsIgnoreCase(tableMapper.getDataType()) ? true : false;

    // 检查char类型
    DatabaseTypeMsgBO charBo =
        DataTypeResource.INSTANCE.getTargetTypeinfo(
            DatabaseTypeEnum.DATABASE_MYSQL, DatabaseTypeSourceEnum.DATABASE_TYPE_CHAR);

    boolean charFlag =
        charBo.getDbType().equalsIgnoreCase(tableMapper.getDataType()) ? true : false;

    if (varcharFlag || timestampFlag || charFlag) {
      switchStr =
          javaPropertiesName
              + Symbol.SPACE
              + Symbol.EQUAL_NOT
              + Symbol.SPACE
              + MyBatisKey.NULL_VALUE
              + Symbol.SPACE
              + SqlKeyEnum.AND.getKey()
              + Symbol.SPACE
              + MyBatisKey.EMPTY_VALUE
              + Symbol.EQUAL_NOT
              + Symbol.SPACE
              + javaPropertiesName;
    } else {
      switchStr =
          javaPropertiesName
              + Symbol.SPACE
              + Symbol.EQUAL_NOT
              + Symbol.SPACE
              + MyBatisKey.NULL_VALUE;
    }

    return switchStr;
  }

  private void queryResponse(
      StringBuilder sb,
      String beanParam,
      String resultMapId,
      List<TableColumnDTO> columnList,
      List<TableColumnDTO> primaryKeyList) {

    // 定义resultmap信息
    sb.append(formatMsg(1))
        .append(RESULT_MAP)
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

    List<TableColumnDTO> delKeyList = columnList;
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
          .append(ID_PROPERTY)
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
    sb.append(formatMsg(1)).append(RESULT_MAP_END).append(NEXT_LINE);
  }

  private void insertMethod(
      StringBuilder sb, TableInfoDTO tableMsg, String beanParam, List<TableColumnDTO> columnList) {
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

    for (int i = 0; i < columnList.size(); i++) {
      TableColumnDTO tableMaper = columnList.get(i);
      String coumnName = tableMaper.getColumnName();
      String javaName = toJava(coumnName);
      // 添加表注释信息
      sb.append(formatMsg(3))
          .append(MyBatisKey.DOC_START)
          .append(tableMaper.getColumnMsg())
          .append(MyBatisKey.DOC_END)
          .append(NEXT_LINE);

      if (tableMaper.isNullFlag()) {

        // 进行类型检查判断
        String switchStr = this.switchType(tableMaper, javaName);

        // 生成判断并添加列
        sb.append(formatMsg(3))
            .append(IF_START)
            .append(switchStr)
            .append(Symbol.QUOTE)
            .append(MyBatisKey.END)
            .append(NEXT_LINE);
        sb.append(formatMsg(4)).append(coumnName).append(Symbol.COMMA).append(NEXT_LINE);
        sb.append(formatMsg(3)).append(IF_END);
      } else {
        sb.append(formatMsg(3)).append(coumnName).append(Symbol.COMMA);
      }

      sb.append(NEXT_LINE);
    }
    sb.append(formatMsg(2)).append(TRIM_XML_END).append(NEXT_LINE);
    sb.append(formatMsg(2)).append(TRIM_VALUE_XML_START).append(NEXT_LINE);
    for (int i = 0; i < columnList.size(); i++) {
      TableColumnDTO tableMaper = columnList.get(i);
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

      // 获取数据库当前列能否为空,不则为空，则直接写死
      if (tableMaper.isNullFlag()) {
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
            .append(Symbol.BRACE_LEFT)
            .append(javaName)
            .append(JDBC_TYPE)
            .append(typeName)
            .append(Symbol.BRACE_RIGHT);

        sb.append(Symbol.COMMA);
        sb.append(NEXT_LINE);
        sb.append(formatMsg(3)).append(IF_END);
      }
      // 当不允许为空时，则直接填写
      else {
        sb.append(formatMsg(3))
            .append(Symbol.POUND)
            .append(Symbol.BRACE_LEFT)
            .append(javaName)
            .append(JDBC_TYPE)
            .append(typeName)
            .append(Symbol.BRACE_RIGHT)
            .append(Symbol.COMMA);
        ;
      }
      // 换行符
      sb.append(NEXT_LINE);
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
   * @param columnList
   * @param primaryKeyList
   */
  private void updateMethod(
      StringBuilder sb,
      TableInfoDTO tableMsg,
      String beanParam,
      List<TableColumnDTO> columnList,
      List<TableColumnDTO> primaryKeyList) {

    List<TableColumnDTO> copyColumnList = new ArrayList<>(columnList);
    // 数据不能修改主键
    copyColumnList.removeAll(primaryKeyList);

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

    for (int i = 0; i < copyColumnList.size(); i++) {
      TableColumnDTO tableMaper = copyColumnList.get(i);
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
          .append(Symbol.SPACE)
          .append(Symbol.EQUAL)
          .append(Symbol.SPACE)
          .append(Symbol.POUND)
          .append(Symbol.BRACE_LEFT)
          .append(javaName)
          .append(JDBC_TYPE)
          .append(typeName)
          .append(Symbol.BRACE_RIGHT)
          .append(Symbol.COMMA)
          .append(NEXT_LINE);

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

  private void deleteMethod(
      StringBuilder sb,
      TableInfoDTO tableMsg,
      String beanParam,
      List<TableColumnDTO> primaryKeyList) {

    // 删除
    sb.append(formatMsg(1))
        .append(MyBatisKey.DOC_START)
        .append(tableMsg.getTableComment())
        .append(Symbol.BRACKET_LEFT)
        .append(tableMsg.getTableName())
        .append(Symbol.BRACKET_RIGHT)
        .append(OPER_DEL)
        .append(MyBatisKey.DOC_END)
        .append(NEXT_LINE);

    // 删除的xml文件的开始
    sb.append(formatMsg(1))
        .append(DELETE_XML_START)
        .append(beanParam)
        .append(Symbol.QUOTE)
        .append(MyBatisKey.END)
        .append(NEXT_LINE);

    sb.append(formatMsg(2)).append(DELETE_SQL).append(tableMsg.getTableName()).append(NEXT_LINE);
    sb.append(formatMsg(2)).append(WHERE_START).append(NEXT_LINE);
    // 以主键做条件
    sb.append(this.addDeleteWhere(primaryKeyList, 3));
    sb.append(formatMsg(2)).append(WHERE_END).append(NEXT_LINE);
    sb.append(formatMsg(1)).append(DELETE_XML_END).append(NEXT_LINE);

    sb.append(NEXT_LINE);
  }

  /**
   * 分页查询方法
   *
   * @param sb
   * @param tableMsg
   * @param beanParam
   * @param resultMapId
   * @param columnList
   * @param keyColumn
   */
  private void queryPageMethod(
      StringBuilder sb,
      TableInfoDTO tableMsg,
      String beanParam,
      String resultMapId,
      List<TableColumnDTO> columnList,
      List<TableColumnDTO> keyColumn) {
    // 查询注释
    sb.append(formatMsg(1))
        .append(MyBatisKey.DOC_START)
        .append(tableMsg.getTableComment())
        .append(Symbol.BRACKET_LEFT)
        .append(tableMsg.getTableName())
        .append(Symbol.BRACKET_RIGHT)
        .append(OPER_QRY)
        .append(MyBatisKey.DOC_END)
        .append(NEXT_LINE);

    // 查询开始
    sb.append(formatMsg(1))
        .append(QUERY_XML_START)
        .append(beanParam)
        .append(QUERY_RSP_MAPPER)
        .append(resultMapId)
        .append(Symbol.QUOTE)
        .append(MyBatisKey.END)
        .append(NEXT_LINE);

    // 查询的列字段
    sb.append(formatMsg(2)).append(QUERY_SQL).append(NEXT_LINE);
    for (int i = 0; i < columnList.size(); i++) {
      String columnName = columnList.get(i).getColumnName();
      sb.append(formatMsg(2)).append(columnName);
      if (i != columnList.size() - 1) {
        sb.append(Symbol.COMMA).append(NEXT_LINE);
      }
    }
    sb.append(NEXT_LINE);
    sb.append(formatMsg(2)).append(QUERY_KEY_FROM).append(NEXT_LINE);
    sb.append(formatMsg(2)).append(tableMsg.getTableName()).append(NEXT_LINE);
    sb.append(formatMsg(2)).append(WHERE_START).append(NEXT_LINE);
    // 添加where条件,以主键做查询条件
    sb.append(this.addDeleteWhere(keyColumn, 3));

    sb.append(formatMsg(2)).append(WHERE_END).append(NEXT_LINE);
    sb.append(formatMsg(1)).append(QUERY_XML_END).append(NEXT_LINE);
    sb.append(NEXT_LINE);
  }

  private void queryByIdMethod(
      StringBuilder sb,
      TableInfoDTO tableMsg,
      String beanParam,
      String resultMapId,
      List<TableColumnDTO> columnList,
      List<TableColumnDTO> keyColumn) {
    // 查询注释
    sb.append(formatMsg(1))
        .append(MyBatisKey.DOC_START)
        .append(tableMsg.getTableComment())
        .append(Symbol.BRACKET_LEFT)
        .append(tableMsg.getTableName())
        .append(Symbol.BRACKET_RIGHT)
        .append(OPER_QRY_ID)
        .append(MyBatisKey.DOC_END)
        .append(NEXT_LINE);

    // 查询开始
    sb.append(formatMsg(1))
        .append(QUERY_ID_XML_START)
        .append(beanParam)
        .append(QUERY_RSP_MAPPER)
        .append(resultMapId)
        .append(Symbol.QUOTE)
        .append(MyBatisKey.END)
        .append(NEXT_LINE);

    // 查询的列字段
    sb.append(formatMsg(2)).append(QUERY_SQL).append(NEXT_LINE);
    for (int i = 0; i < columnList.size(); i++) {
      String columnName = columnList.get(i).getColumnName();
      sb.append(formatMsg(2)).append(columnName);
      if (i != columnList.size() - 1) {
        sb.append(Symbol.COMMA).append(NEXT_LINE);
      }
    }
    sb.append(NEXT_LINE);
    sb.append(formatMsg(2)).append(QUERY_KEY_FROM).append(NEXT_LINE);
    sb.append(formatMsg(2)).append(tableMsg.getTableName()).append(NEXT_LINE);
    sb.append(formatMsg(2)).append(WHERE_START).append(NEXT_LINE);
    // 添加where条件 以主键做条件
    sb.append(this.addDeleteWhere(keyColumn, 3));

    sb.append(formatMsg(2)).append(WHERE_END).append(NEXT_LINE);
    sb.append(formatMsg(1)).append(QUERY_XML_END).append(NEXT_LINE);
    sb.append(NEXT_LINE);
  }

  private String getKeyType(TableColumnDTO key) {
    // 获取主键的java类型
    String tempType = key.getDataType().toLowerCase();
    // 1,从数据库的类型中获取
    StandardTypeEnum standardType = MysqlDataTypeEnum.databaseToStandKey(tempType);
    // 类型信息
    return MybatisParamTypeEnum.getMybatisParamType(standardType);
  }
}

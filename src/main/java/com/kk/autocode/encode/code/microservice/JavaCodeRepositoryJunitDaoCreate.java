package com.kk.autocode.encode.code.microservice;

import com.kk.autocode.encode.base.TableProcessBase;
import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.bean.EncodeContext;
import com.kk.autocode.encode.code.AutoCodeInf;
import com.kk.autocode.encode.constant.CreateCfg;
import com.kk.autocode.encode.constant.CreateCommKey;
import com.kk.autocode.encode.constant.Symbol;
import com.kk.docprocess.doctoadapterdoc.process.impl.ValueProcess;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 生成单元测试相关的代码
 *
 * @since 2018年4月15日 下午4:11:42
 * @version 0.0.1
 * @author liujun
 */
public class JavaCodeRepositoryJunitDaoCreate extends TableProcessBase implements AutoCodeInf {

  public static final String PKG_NAME = "javabeanJunitDAO";

  private static final String DAO_TEST_NAME = "Test";
  private static final String IMPORT_UTILS = "import java.util.List;";
  private static final String IMPORT_JUNIT = "import org.junit.*;";
  private static final String IMPORT_RANDOM_STRING_UTILS =
      "import org.apache.commons.lang3.RandomStringUtils;";
  private static final String IMPORT_RANDOM_UTILS = "import org.apache.commons.lang3.RandomUtils;";
  private static final String IMPORT_ANNOTATION =
      "import org.springframework.beans.factory.annotation.Autowired;";
  private static final String TEST_PARENT = "TestParent";
  private static final String DATABASE_DOC = "数据库操作单元测试";
  private static final String EXTEND_PARENT = " extends TestParent ";
  private static final String AUTOWIRED = "@Autowired";
  private static final String INSTANCE_NAME = "instDao";
  private static final String INSTANCE_NAME_PO = "operPo";
  private static final String INSTANCE_NAME_PO_TMP = "paramPo";
  private static final String JUNIT_BEFORE = "@Before";
  private static final String BEFORE_SETPO = "void beforeSetPo()";
  private static final String GET_DATA_METHD = "getDataBean()";
  private static final String PARAM_BEAN = "paramBean";
  private static final String ANNO_TEST = "@Test";
  private static final String INSERT_METHOD = "public void testInsert()  {";
  private static final String UPDATE_METHOD = "public void testUpdate()  {";
  private static final String DELETE_METHOD = "public void testDelete()  {";
  private static final String QUERY_METHOD = "public void testQueryPage()  {";
  private static final String QUERY_ID_METHOD = "public void testQueryById()  {";
  private static final String TMP_VAR_NAME = "rs";
  private static final String METHOD_INSERT = "insert";
  private static final String METHOD_UPDATE = "update";
  private static final String METHOD_DELETE = "delete";
  private static final String METHOD_QUERY = "queryPage";
  private static final String METHOD_QUERY_ID = "queryById";
  private static final String UPD_ASSERT = "Assert.assertEquals(1, ";
  private static final String ASSERT = "Assert.assertEquals( ";
  private static final String TMP_VAR_NAME_UPD = "rsUpd";
  private static final String QUERY_LIST_NAME = "queryRsp";
  private static final String TMP_GET_PO_NAME = "rsGetBean";
  private static final String QUERY_ASSERT = "Assert.assertNotNull(";
  private static final String ANNO_AFTER = "@After";
  private static final String GET_ITEM_INDEX = ".get(0)";
  private static final String GET_KEY = "get";
  private static final String SET_KEY = "set";

  private void enTestDaoCode(
      String path, String basePackageStr, String tableSpace, EncodeContext context)
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
      List<TableColumnDTO> columnList = tableName.getValue();
      // 获取当前主键列表
      List<TableColumnDTO> primaryKeyList = getPrimaryKey(columnList);
      StringBuilder sb = new StringBuilder();
      TableInfoDTO tableInfo = context.getTableMap().get(tableName.getKey());
      String tableClassName = toJavaClassName(tableName.getKey());
      String importPoBean =
          basePackageStr
              + JavaCodeRepositoryPoCreate.REPOSITORY_PO_PACKAGE
              + Symbol.POINT
              + tableClassName
              + JavaCodeRepositoryPoCreate.REPOSITORY_PO;
      String beanPoName = tableClassName + JavaCodeRepositoryPoCreate.REPOSITORY_PO;
      String serviceName = tableClassName + JavaCodeRepositoryDaoInfCreate.DAO_SUFFIX;
      String className = serviceName + DAO_TEST_NAME;
      String packageStr = basePackageStr + JavaCodeRepositoryDaoInfCreate.DAO_PACKAGE;
      String importDAOBean =
          basePackageStr + JavaCodeRepositoryDaoInfCreate.DAO_PACKAGE + Symbol.POINT + serviceName;

      // 需要客外导入的名
      List<String> dataImport =
          (List<String>) context.getDataMap().get(CreateCommKey.JUNIT_IMPORT_KEY.getKey());
      // 定义包及导入包
      packageMethod(sb, packageStr, importPoBean, importDAOBean, dataImport);
      // 添加类注释信息
      sb.append(JavaCodeKey.ANNO_CLASS).append(NEXT_LINE);
      sb.append(JavaCodeKey.ANNO_CLASS_MID)
          .append(tableInfo.getTableComment())
          .append(Symbol.BRACKET_LEFT)
          .append(tableName.getKey())
          .append(Symbol.BRACKET_RIGHT)
          .append(DATABASE_DOC)
          .append(NEXT_LINE);
      sb.append(JavaCodeKey.DOC_VERSION).append(NEXT_LINE);
      sb.append(JavaCodeKey.DOC_AUTH).append(NEXT_LINE);
      sb.append(JavaCodeKey.ANNO_OVER).append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 添加类的信息
      sb.append(JavaCodeKey.ClASS_START)
          .append(className)
          .append(EXTEND_PARENT)
          .append(Symbol.BRACE_LEFT)
          .append(NEXT_LINE)
          .append(NEXT_LINE);

      // 操作数据之前的初始化相关的工作
      beforeMethod(sb, tableClassName, beanPoName, columnList);

      // 添加数据的方法
      insertMethod(sb);

      // 修改数据的方法
      updateMethod(sb, beanPoName, columnList);

      // 查询方法
      queryMethod(sb, beanPoName, columnList);

      // 当前查询如果为单主键，则添加根据id的查询方法
      if (primaryKeyList != null && primaryKeyList.size() <= CreateCfg.ONE_KEY_FLAG) {
        this.queryByIdMethod(sb, beanPoName, columnList, primaryKeyList.get(0));
      }

      // 删除方法
      deleteMethod(sb, beanPoName);

      // 结束
      sb.append(Symbol.BRACE_RIGHT);

      // 数据输出操作
      String outPath = path + className + JavaCodeKey.FILE_SUFFIX;
      FileUtils.writeFile(outPath, sb);
    }
  }

  @Override
  public void autoCode(CreateParamBean param) {

    // 进行自动代码生成
    try {
      String daoInfPath = param.getFileBasePath() + PKG_NAME + Symbol.PATH;
      String javaPackage = param.getJavaPackage();
      this.enTestDaoCode(daoInfPath, javaPackage, param.getTableSpaceName(), param.getContext());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 定义包及导入包
   *
   * @param sb
   * @param packageStr
   * @param importPoBean
   * @param importDAOBean
   * @param importData 导入的包信息
   */
  private void packageMethod(
      StringBuilder sb,
      String packageStr,
      String importPoBean,
      String importDAOBean,
      List<String> importData) {
    // 定义包
    sb.append(JavaCodeKey.PACKAGE)
        .append(Symbol.SPACE)
        .append(packageStr)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE)
        .append(NEXT_LINE);

    sb.append(IMPORT_UTILS).append(NEXT_LINE).append(NEXT_LINE);
    sb.append(IMPORT_JUNIT).append(NEXT_LINE);
    sb.append(IMPORT_RANDOM_STRING_UTILS).append(NEXT_LINE);
    sb.append(IMPORT_RANDOM_UTILS).append(NEXT_LINE);
    sb.append(IMPORT_ANNOTATION).append(NEXT_LINE);
    sb.append(NEXT_LINE);
    // 导入po的包
    sb.append(JavaCodeKey.IMPORT)
        .append(Symbol.SPACE)
        .append(importPoBean)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    // 导入dao的包
    sb.append(JavaCodeKey.IMPORT)
        .append(Symbol.SPACE)
        .append(importDAOBean)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);

    if (null != importData && !importData.isEmpty()) {
      for (String item : importData) {
        sb.append(JavaCodeKey.IMPORT)
            .append(Symbol.SPACE)
            .append(item)
            .append(Symbol.SEMICOLON)
            .append(NEXT_LINE);
      }
    }

    sb.append(NEXT_LINE);
  }

  private void beforeMethod(
      StringBuilder sb, String tableClassName, String beanPoName, List<TableColumnDTO> columnList) {
    // 添加前置方法
    sb.append(formatMsg(1));
    sb.append(AUTOWIRED);
    sb.append(NEXT_LINE);
    // 实体的spring注入
    sb.append(formatMsg(1))
        .append(JavaCodeKey.PRIVATE)
        .append(Symbol.SPACE)
        .append(tableClassName)
        .append(JavaCodeRepositoryDaoInfCreate.DAO_SUFFIX)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(NEXT_LINE);

    // 定义属性的类型
    sb.append(formatMsg(1))
        .append(JavaCodeKey.PRIVATE)
        .append(Symbol.SPACE)
        .append(beanPoName)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME_PO)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    // 添加初始化实体对象的方法
    sb.append(formatMsg(1)).append(JUNIT_BEFORE).append(NEXT_LINE);
    sb.append(formatMsg(1))
        .append(JavaCodeKey.PUBLIC)
        .append(Symbol.SPACE)
        .append(BEFORE_SETPO)
        .append(Symbol.BRACE_LEFT)
        .append(NEXT_LINE);

    sb.append(formatMsg(2))
        .append(INSTANCE_NAME_PO)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(JavaCodeKey.THIS)
        .append(Symbol.POINT)
        .append(GET_DATA_METHD)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(formatMsg(1)).append(Symbol.BRACE_RIGHT).append(NEXT_LINE);
    sb.append(NEXT_LINE);

    sb.append(formatMsg(1))
        .append(JavaCodeKey.PUBLIC)
        .append(Symbol.SPACE)
        .append(beanPoName)
        .append(Symbol.SPACE)
        .append(GET_DATA_METHD)
        .append(Symbol.BRACE_LEFT)
        .append(NEXT_LINE);

    // 属性相关值的获取操作
    sb.append(formatMsg(2))
        .append(beanPoName)
        .append(Symbol.SPACE)
        .append(PARAM_BEAN)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(JavaCodeKey.NEW)
        .append(Symbol.SPACE)
        .append(beanPoName)
        .append(Symbol.BRACKET_LEFT)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    this.outProperties(sb, columnList);
    sb.append(formatMsg(2))
        .append(JavaCodeKey.RETURN)
        .append(Symbol.SPACE)
        .append(PARAM_BEAN)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(formatMsg(1)).append(Symbol.BRACE_RIGHT).append(NEXT_LINE);
    sb.append(NEXT_LINE);
  }

  public void insertMethod(StringBuilder sb) {
    // 添加insert方法
    sb.append(formatMsg(1)).append(ANNO_TEST).append(NEXT_LINE);
    sb.append(formatMsg(1)).append(INSERT_METHOD).append(NEXT_LINE);
    // 方法调用
    sb.append(formatMsg(2))
        .append(JavaCodeKey.INT_TYPE)
        .append(Symbol.SPACE)
        .append(TMP_VAR_NAME)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME)
        .append(Symbol.POINT)
        .append(METHOD_INSERT)
        .append(Symbol.BRACKET_LEFT)
        .append(INSTANCE_NAME_PO)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);

    sb.append(formatMsg(2))
        .append(UPD_ASSERT)
        .append(TMP_VAR_NAME)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(formatMsg(1)).append(Symbol.BRACE_RIGHT).append(NEXT_LINE).append(NEXT_LINE);
  }

  private void updateMethod(StringBuilder sb, String beanPoName, List<TableColumnDTO> columnList) {
    // 修改
    sb.append(formatMsg(1)).append(ANNO_TEST).append(NEXT_LINE);
    sb.append(formatMsg(1)).append(UPDATE_METHOD).append(NEXT_LINE);
    // insert方法调用
    sb.append(formatMsg(2))
        .append(JavaCodeKey.INT_TYPE)
        .append(Symbol.SPACE)
        .append(TMP_VAR_NAME)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME)
        .append(Symbol.POINT)
        .append(METHOD_INSERT)
        .append(Symbol.BRACKET_LEFT)
        .append(INSTANCE_NAME_PO)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    // 结果检查
    sb.append(formatMsg(2))
        .append(UPD_ASSERT)
        .append(TMP_VAR_NAME)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(formatMsg(2))
        .append(beanPoName)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME_PO_TMP)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME_PO)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    // 输出普通列信息
    this.updateProperties(sb, columnList);

    sb.append(formatMsg(2))
        .append(JavaCodeKey.INT_TYPE)
        .append(Symbol.SPACE)
        .append(TMP_VAR_NAME_UPD)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME)
        .append(Symbol.POINT)
        .append(METHOD_UPDATE)
        .append(Symbol.BRACKET_LEFT)
        .append(INSTANCE_NAME_PO_TMP)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);

    sb.append(formatMsg(2))
        .append(UPD_ASSERT)
        .append(TMP_VAR_NAME_UPD)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(formatMsg(1)).append(Symbol.BRACE_RIGHT);

    sb.append(NEXT_LINE);
    sb.append(NEXT_LINE);
  }

  /**
   * 输出属性文件信息
   *
   * @param sb 字符串
   * @param tableColumnList 列信息
   */
  private void updateProperties(StringBuilder sb, List<TableColumnDTO> tableColumnList) {

    // 拷贝集合
    List<TableColumnDTO> columnList = copyList(tableColumnList);

    TableColumnDTO columnInfo = null;

    for (int i = 0; i < columnList.size(); i++) {
      columnInfo = columnList.get(i);
      // 跳过当前的主键列
      if (columnInfo.isPrimaryKey()) {
        continue;
      }

      String javaType = this.getJavaType(columnInfo);

      String value = createValue(columnList.get(i));

      // 添加当前的属性值的注释
      sb.append(formatMsg(2)).append(Symbol.PATH).append(Symbol.PATH);
      sb.append(columnList.get(i).getColumnMsg());
      sb.append(NEXT_LINE);
      // 添加生成代码的方法
      sb.append(formatMsg(2))
          .append(INSTANCE_NAME_PO_TMP)
          .append(Symbol.POINT)
          .append(SET_KEY)
          .append(this.toProJavaName(columnList.get(i).getColumnName()))
          .append(Symbol.BRACKET_LEFT);
      sb.append(ValueProcess.INSTANCE.parseJavaValue(javaType, value));

      sb.append(Symbol.BRACKET_RIGHT).append(Symbol.SEMICOLON).append(NEXT_LINE);
    }
  }

  private void queryMethod(StringBuilder sb, String beanPoName, List<TableColumnDTO> columnList) {
    // 查询
    sb.append(formatMsg(1)).append(ANNO_TEST).append(NEXT_LINE);
    sb.append(formatMsg(1)).append(QUERY_METHOD).append(NEXT_LINE);
    // 调用插入数据
    sb.append(formatMsg(2))
        .append(JavaCodeKey.INT_TYPE)
        .append(Symbol.SPACE)
        .append(TMP_VAR_NAME)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME)
        .append(Symbol.POINT)
        .append(METHOD_INSERT)
        .append(Symbol.BRACKET_LEFT)
        .append(INSTANCE_NAME_PO)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);

    // 检查插入结果
    sb.append(formatMsg(2))
        .append(UPD_ASSERT)
        .append(TMP_VAR_NAME)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(NEXT_LINE);
    // 临时变量
    sb.append(formatMsg(2))
        .append(beanPoName)
        .append(Symbol.SPACE)
        .append(PARAM_BEAN)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME_PO)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    // 执行查询
    sb.append(formatMsg(2))
        .append(JavaCodeKey.LIST_TYPE)
        .append(beanPoName)
        .append(JavaCodeKey.LIST_TYPE_END)
        .append(Symbol.SPACE)
        .append(QUERY_LIST_NAME)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME)
        .append(Symbol.POINT)
        .append(METHOD_QUERY)
        .append(Symbol.BRACKET_LEFT)
        .append(PARAM_BEAN)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);

    sb.append(formatMsg(2))
        .append(QUERY_ASSERT)
        .append(QUERY_LIST_NAME)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);

    sb.append(formatMsg(2))
        .append(beanPoName)
        .append(Symbol.SPACE)
        .append(TMP_GET_PO_NAME)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(QUERY_LIST_NAME)
        .append(GET_ITEM_INDEX)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);

    // 生成单元测试中的数据对比
    junitEquals(sb, columnList, beanPoName);
    sb.append(formatMsg(1)).append(Symbol.BRACE_RIGHT);

    sb.append(NEXT_LINE);
    sb.append(NEXT_LINE);
  }

  private void queryByIdMethod(
      StringBuilder sb,
      String beanPoName,
      List<TableColumnDTO> columnList,
      TableColumnDTO primaryKey) {
    // 查询
    sb.append(formatMsg(1)).append(ANNO_TEST).append(NEXT_LINE);
    // 定义方法头
    sb.append(formatMsg(1)).append(QUERY_ID_METHOD).append(NEXT_LINE);
    // 调用插入数据
    sb.append(formatMsg(2))
        .append(JavaCodeKey.INT_TYPE)
        .append(Symbol.SPACE)
        .append(TMP_VAR_NAME)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME)
        .append(Symbol.POINT)
        .append(METHOD_INSERT)
        .append(Symbol.BRACKET_LEFT)
        .append(INSTANCE_NAME_PO)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);

    // 检查插入结果
    sb.append(formatMsg(2))
        .append(UPD_ASSERT)
        .append(TMP_VAR_NAME)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(NEXT_LINE);

    // 获取主键的java类型
    String keyIdName = Symbol.POINT + GET_KEY + this.toProJavaName(primaryKey.getColumnName());

    // 执行查询
    sb.append(formatMsg(2))
        .append(beanPoName)
        .append(Symbol.SPACE)
        .append(TMP_GET_PO_NAME)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME)
        .append(Symbol.POINT)
        .append(METHOD_QUERY_ID)
        .append(Symbol.BRACKET_LEFT)
        .append(INSTANCE_NAME_PO)
        .append(keyIdName)
        .append(Symbol.BRACKET_LEFT)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);

    // 生成单元测试中的数据对比
    junitEquals(sb, columnList, beanPoName);
    sb.append(formatMsg(1)).append(Symbol.BRACE_RIGHT);

    sb.append(NEXT_LINE);
    sb.append(NEXT_LINE);
  }

  private void deleteMethod(StringBuilder sb, String beanPoName) {
    // 删除
    sb.append(formatMsg(1)).append(ANNO_AFTER).append(NEXT_LINE);
    sb.append(formatMsg(1)).append(DELETE_METHOD).append(NEXT_LINE);
    sb.append(formatMsg(2))
        .append(beanPoName)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME_PO_TMP)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME_PO)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(formatMsg(2))
        .append(JavaCodeKey.INT_TYPE)
        .append(Symbol.SPACE)
        .append(TMP_VAR_NAME)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(INSTANCE_NAME)
        .append(Symbol.POINT)
        .append(METHOD_DELETE)
        .append(Symbol.BRACKET_LEFT)
        .append(INSTANCE_NAME_PO_TMP)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(formatMsg(2))
        .append(UPD_ASSERT)
        .append(TMP_VAR_NAME)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);

    sb.append(formatMsg(1)).append(Symbol.BRACE_RIGHT);

    sb.append(NEXT_LINE);
    sb.append(NEXT_LINE);
  }

  /**
   * 输出属性文件信息
   *
   * @param sb 字符串
   * @param columnListParam 列信息
   * @param beanDtoName javaBean的名称
   */
  private void junitEquals(
      StringBuilder sb, List<TableColumnDTO> columnListParam, String beanDtoName) {

    // 拷贝集合
    List<TableColumnDTO> columnList = copyList(columnListParam);

    for (int i = 0; i < columnList.size(); i++) {

      // 添加当前的属性值的注释
      sb.append(formatMsg(2)).append(Symbol.PATH).append(Symbol.PATH);
      sb.append(columnList.get(i).getColumnMsg());
      sb.append(NEXT_LINE);

      // 添加生成代码的方法
      sb.append(formatMsg(2));
      sb.append(ASSERT);
      sb.append(TMP_GET_PO_NAME);
      sb.append(Symbol.POINT);
      sb.append(GET_KEY)
          .append(this.toProJavaName(columnList.get(i).getColumnName()))
          .append(Symbol.BRACKET_LEFT)
          .append(Symbol.BRACKET_RIGHT)
          .append(Symbol.COMMA);

      sb.append(INSTANCE_NAME_PO);
      sb.append(Symbol.POINT).append(GET_KEY);
      sb.append(this.toProJavaName(columnList.get(i).getColumnName()))
          .append(Symbol.BRACKET_LEFT)
          .append(Symbol.BRACKET_RIGHT)
          .append(Symbol.BRACKET_RIGHT)
          .append(Symbol.SEMICOLON);
      sb.append(NEXT_LINE);
    }
  }
}

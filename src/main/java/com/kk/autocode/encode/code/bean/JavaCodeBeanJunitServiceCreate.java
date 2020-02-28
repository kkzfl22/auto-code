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

/** 生成junit测试代码 */
public class JavaCodeBeanJunitServiceCreate extends TableProcessBase implements AutoCodeInf {

  public static void main(String[] args) throws Exception {
    JavaCodeBeanJunitServiceCreate instance = new JavaCodeBeanJunitServiceCreate();

    args = new String[3];

    args[0] = "D:/java/encode/a10/javabeanJunitService/";
    args[1] = "com.a10.resource.phone.";
    args[2] = "a10";

    JavaCodeBeanDaoImplCreate daoImplInstance = new JavaCodeBeanDaoImplCreate();

    Map<String, List<TableColumnDTO>> map = daoImplInstance.getTableColumnInfoByBean(args[3]);

    Map<String, TableInfoDTO> tableMap = daoImplInstance.getTableInfoByBean(args[3]);

    EncodeContext context = new EncodeContext();

    context.setColumnMap(map);
    context.setTableMap(tableMap);

    instance.enTestServiceCode(args[0], args[1], args[2], context);
  }

  private void enTestServiceCode(
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

    // Set<String> tableNames = map.keySet();
    Iterator<Entry<String, List<TableColumnDTO>>> tableNameEntry = map.entrySet().iterator();

    while (tableNameEntry.hasNext()) {
      Entry<String, List<TableColumnDTO>> tableName = tableNameEntry.next();

      List<TableColumnDTO> columnList = tableName.getValue();
      StringBuilder sb = new StringBuilder();

      TableInfoDTO tableInfo = context.getTableMap().get(tableName.getKey());

      String tableClassName = toJavaClassName(tableName.getKey());

      String serviceName = tableClassName + "Service";
      String className = serviceName + "Test";
      // 主键列表
      List<TableColumnDTO> primaryKeyList = this.getPrimaryKey(columnList);

      String importServiceBean = basePackageStr + "service." + serviceName;
      String importBean = basePackageStr + BASE_BENA_NAME + "." + tableClassName + "DTO";
      String beanDtoName = tableClassName + "DTO";
      String packageStr = basePackageStr + "service";

      sb.append("package ").append(packageStr).append(";").append(NEXT_LINE);
      sb.append(NEXT_LINE);

      sb.append("import java.util.List;").append(NEXT_LINE);
      sb.append(NEXT_LINE);

      sb.append("import org.junit.*;").append(NEXT_LINE);
      sb.append("import java.time.LocalDateTime;").append(NEXT_LINE);
      sb.append("import java.time.format.DateTimeFormatter;").append(NEXT_LINE);
      sb.append("import org.apache.commons.lang3.RandomStringUtils;").append(NEXT_LINE);
      sb.append("import org.apache.commons.lang3.RandomUtils;").append(NEXT_LINE);
      sb.append("import org.springframework.beans.factory.annotation.Autowired;").append(NEXT_LINE);
      sb.append("import com.github.pagehelper.PageHelper;").append(NEXT_LINE);
      sb.append(NEXT_LINE);
      // sb.append("import ").append(COMM_SPIT_BEAN).append(NEXT_LINE);
      sb.append("import ").append(importBean).append(";").append(NEXT_LINE);
      sb.append("import ").append(importServiceBean).append(";").append(NEXT_LINE);
      sb.append("import ")
          .append(basePackageStr)
          .append("TestParent")
          .append(";")
          .append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 添加类注释信息
      sb.append("/**").append(NEXT_LINE);
      sb.append(" * ").append(tableInfo.getTableComment()).append("(");
      sb.append(tableName.getKey()).append(")数据库服务操作单元测试").append(NEXT_LINE);
      sb.append(" * @version 1.0.0").append(NEXT_LINE);
      sb.append(" * @author liujun").append(NEXT_LINE);
      sb.append(" * @date ").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
      sb.append(" */").append(NEXT_LINE);
      sb.append(NEXT_LINE);
      sb.append("public class " + className + " extends TestParent {" + NEXT_LINE);

      sb.append(NEXT_LINE);

      // 添加前置方法
      sb.append(formatMsg(1));
      sb.append("@Autowired");
      sb.append(NEXT_LINE);
      sb.append(formatMsg(1))
          .append(" private  " + tableClassName + "Service serviceInstance;" + NEXT_LINE);
      sb.append(NEXT_LINE);

      // 添加属性设置方法
      sb.append(formatMsg(1)).append("private  " + beanDtoName + " operDto;" + NEXT_LINE);
      sb.append(NEXT_LINE);

      // 添加初始化实体对象的方法
      sb.append(formatMsg(1)).append("@Before").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("public  void beforeSetPojo() { ").append(NEXT_LINE);
      sb.append(formatMsg(2)).append("operDto = this.getDataBean(); ").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("}" + NEXT_LINE);
      sb.append(NEXT_LINE);

      sb.append(formatMsg(1)).append("public ").append(beanDtoName);
      sb.append(" getDataBean()").append("{").append(NEXT_LINE);

      // 属性相关值的设置操作
      sb.append(formatMsg(2)).append(beanDtoName).append(" paramBean = new ");
      sb.append(beanDtoName).append("();").append(NEXT_LINE);
      this.outProperties(sb, columnList);
      sb.append(formatMsg(2)).append("return paramBean;").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 添加insert方法
      sb.append(formatMsg(1)).append("@Test").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("public void testInsert()  {").append(NEXT_LINE);

      // 执行添加代码操作
      sb.append(formatMsg(2)).append("boolean rs = serviceInstance.insert(operDto);" + NEXT_LINE);
      sb.append(formatMsg(2)).append("Assert.assertEquals(true, rs);").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("}");

      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);
      // 修改
      sb.append(formatMsg(1)).append("@Test" + NEXT_LINE);
      sb.append(formatMsg(1)).append("public void testUpdate()  {").append(NEXT_LINE);

      // 执行添加代码操作
      sb.append(formatMsg(2)).append("boolean rs = serviceInstance.insert(operDto);" + NEXT_LINE);
      sb.append(formatMsg(2)).append("Assert.assertEquals(true, rs);").append(NEXT_LINE);

      sb.append(formatMsg(2)).append(beanDtoName);
      sb.append(" paramBean= operDto;").append(NEXT_LINE);

      // 调生生成修改的方法列
      this.outUpdateProperties(sb, columnList);

      sb.append(formatMsg(2))
          .append("boolean rsUpd = serviceInstance.update(paramBean);" + NEXT_LINE);
      sb.append(formatMsg(2)).append("Assert.assertEquals(true, rsUpd);").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("}");

      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 查询
      sb.append(formatMsg(1)).append("@Test" + NEXT_LINE);
      sb.append(formatMsg(1)).append("public void testQuery()  {").append(NEXT_LINE);
      sb.append(formatMsg(2))
          .append("boolean rs = serviceInstance.insert(operDto);")
          .append(NEXT_LINE);
      sb.append(formatMsg(2)).append("Assert.assertEquals(true, rs);").append(NEXT_LINE);
      sb.append(NEXT_LINE);

      sb.append(formatMsg(2)).append(beanDtoName);
      sb.append(" paramBean= operDto;").append(NEXT_LINE);
      sb.append(formatMsg(2))
          .append(
              "List<" + beanDtoName + "> rsQuery = serviceInstance.query(paramBean);" + NEXT_LINE);
      sb.append(formatMsg(2)).append("Assert.assertNotNull(rsQuery);").append(NEXT_LINE);
      // 生成单元测试中的数据对比
      outJunitEquals(sb, columnList, beanDtoName);
      sb.append(formatMsg(1)).append("}");

      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 分页查询
      sb.append(formatMsg(1)).append("@Test" + NEXT_LINE);
      sb.append(formatMsg(1)).append("public void testPageQuery() {");
      sb.append(NEXT_LINE);

      sb.append(formatMsg(2))
          .append("boolean rs = serviceInstance.insert(operDto);")
          .append(NEXT_LINE);
      sb.append(formatMsg(2)).append("Assert.assertEquals(true, rs);").append(NEXT_LINE);
      sb.append(NEXT_LINE);

      sb.append(formatMsg(2)).append(beanDtoName);
      sb.append(" paramBean= operDto;").append(NEXT_LINE);
      sb.append(formatMsg(2)).append(" int pageNum = 1;").append(NEXT_LINE);

      sb.append(formatMsg(2)).append("//设置页数,及每页大小").append(NEXT_LINE);
      sb.append(formatMsg(2)).append("PageHelper.startPage(1, pageNum);").append(NEXT_LINE);
      sb.append(formatMsg(2))
          .append(
              "List<" + beanDtoName + "> rsQuery = serviceInstance.query(paramBean);" + NEXT_LINE);

      sb.append(formatMsg(2)).append("Assert.assertNotNull(rsQuery);").append(NEXT_LINE);
      // 生成单元测试中的数据对比
      outJunitEquals(sb, columnList, beanDtoName);
      sb.append(formatMsg(1)).append("}");
      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 删除
      sb.append(formatMsg(1)).append("@After" + NEXT_LINE);
      sb.append(formatMsg(1)).append("public void testDelete()  {").append(NEXT_LINE);
      sb.append(formatMsg(2)).append(beanDtoName).append(" paramBean = operDto;").append(NEXT_LINE);
      sb.append(formatMsg(2)).append("boolean rs = serviceInstance.delete(paramBean);" + NEXT_LINE);
      sb.append(formatMsg(2)).append("Assert.assertEquals(true, rs);").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("}");

      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 结束
      sb.append("}");
      FileWriter fw = new FileWriter(new File(path + className + ".java"));
      fw.write(sb.toString());
      fw.close();
    }
  }

  /**
   * 转换参数信息 方法描述
   *
   * @param tableBean
   * @param javaName
   * @param sb @创建日期 2016年10月12日
   */
  private void parseParamValueStr(TableColumnDTO tableBean, String value, StringBuilder sb) {

    // 得到数据库对应的java类型
    String javaType = this.getJavaType(tableBean);

    switch (javaType) {
      case "int":
        sb.append(value);
        break;
      case "long":
        sb.append(value);
        break;
      case "float":
        sb.append(value);
        break;
      default:
        sb.append("\"").append(value).append("\"");
        break;
    }
  }

  @Override
  public void autoCode(CreateParamBean param) {
    // 进行自动代码生成
    try {
      String daoInfPath = param.getFileBasePath() + "javabeanJunitService/";
      String javaPackage = param.getJavaPackage();
      this.enTestServiceCode(
          daoInfPath, javaPackage, param.getTableSpaceName(), param.getContext());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

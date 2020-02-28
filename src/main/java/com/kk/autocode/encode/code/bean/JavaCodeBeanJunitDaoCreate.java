package com.kk.autocode.encode.code.bean;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kk.autocode.encode.base.TableProcessBase;
import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.bean.EncodeContext;
import com.kk.docprocess.doctoadapterdoc.process.impl.ValueProcess;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;
import com.kk.autocode.encode.code.AutoCodeInf;
import com.kk.autocode.util.DateUtils;

/**
 * 生成junit测试代码
 *
 * @since 2018年4月15日 下午4:11:42
 * @version 0.0.1
 * @author liujun
 */
public class JavaCodeBeanJunitDaoCreate extends TableProcessBase implements AutoCodeInf {

  public static void main(String[] args) throws Exception {
    JavaCodeBeanJunitDaoCreate instance = new JavaCodeBeanJunitDaoCreate();

    args = new String[3];

    args[0] = "D:/java/encode/a10/javabeanJunitDao/";
    args[1] = "com.a10.resource.officeroom.";
    args[2] = "a10";

    JavaCodeBeanDaoImplCreate daoImplInstance = new JavaCodeBeanDaoImplCreate();

    Map<String, List<TableColumnDTO>> map = daoImplInstance.getTableColumnInfoByBean(args[3]);

    Map<String, TableInfoDTO> tableMap = daoImplInstance.getTableInfoByBean(args[3]);

    EncodeContext context = new EncodeContext();

    context.setColumnMap(map);
    context.setTableMap(tableMap);

    instance.enTestDaoCode(args[0], args[1], args[2], context);
  }

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
      StringBuilder sb = new StringBuilder();

      TableInfoDTO tableInfo = context.getTableMap().get(tableName.getKey());

      String tableClassName = toJavaClassName(tableName.getKey());

      String importBean = basePackageStr + BASE_BENA_NAME + "." + tableClassName + "DTO";

      String beanDtoName = tableClassName + "DTO";

      String serviceName = tableClassName + "DAO";
      String className = serviceName + "Test";

      // 获取主键列列表l
      List<TableColumnDTO> primaryKeyList = this.getPrimaryKey(columnList);

      String packageStr = basePackageStr + "dao";

      String importDAOBean = basePackageStr + "dao." + serviceName;

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
      sb.append(NEXT_LINE);
      sb.append("import ").append(importBean).append(";").append(NEXT_LINE);
      sb.append("import ").append(importDAOBean).append(";").append(NEXT_LINE);
      sb.append("import ")
          .append(basePackageStr)
          .append("TestParent")
          .append(";")
          .append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 添加类注释信息
      sb.append("/**").append(NEXT_LINE);
      sb.append(" * ").append(tableInfo.getTableComment()).append("(");
      sb.append(tableName.getKey()).append(")数据库操作单元测试").append(NEXT_LINE);
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
      sb.append(formatMsg(1)).append("private  " + tableClassName + "DAO instDao;" + NEXT_LINE);
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
      sb.append(formatMsg(2)).append("int rs = instDao.insert(operDto);" + NEXT_LINE);
      sb.append(formatMsg(2)).append("Assert.assertEquals(1, rs);").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("}");

      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);
      // 修改
      sb.append(formatMsg(1)).append("@Test" + NEXT_LINE);
      sb.append(formatMsg(1)).append("public void testUpdate()  {").append(NEXT_LINE);

      // 执行添加代码操作
      sb.append(formatMsg(2)).append("int rs = instDao.insert(operDto);" + NEXT_LINE);
      sb.append(formatMsg(2)).append("Assert.assertEquals(1, rs);").append(NEXT_LINE);

      sb.append(formatMsg(2)).append(beanDtoName);
      sb.append(" paramBean= operDto;").append(NEXT_LINE);
      // 输出普通列信息
      this.outUpdateProperties(sb, columnList);

      sb.append(formatMsg(2)).append("int rsUpd = instDao.update(paramBean);" + NEXT_LINE);
      sb.append(formatMsg(2)).append("Assert.assertEquals(1, rsUpd);").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("}");

      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 查询
      sb.append(formatMsg(1)).append("@Test" + NEXT_LINE);
      sb.append(formatMsg(1)).append("public void testQuery()  {").append(NEXT_LINE);
      sb.append(formatMsg(2)).append("int rs = instDao.insert(operDto);").append(NEXT_LINE);
      sb.append(formatMsg(2)).append("Assert.assertEquals(1, rs);").append(NEXT_LINE);
      sb.append(NEXT_LINE);

      sb.append(formatMsg(2)).append(beanDtoName);
      sb.append(" paramBean= operDto;").append(NEXT_LINE);
      sb.append(formatMsg(2))
          .append("List<" + beanDtoName + "> rsQuery = instDao.query(paramBean);" + NEXT_LINE);
      sb.append(formatMsg(2)).append("Assert.assertNotNull(rsQuery);").append(NEXT_LINE);
      // 生成单元测试中的数据对比
      outJunitEquals(sb, columnList, beanDtoName);
      //      sb.append(formatMsg(2)).append(beanDtoName).append(" rsGetBean =
      // rsQuery.get(0);").append(NEXT_LINE);
      //      sb.append(formatMsg(2)).append("Assert.assertEquals(rsGetBean.);").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("}");

      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 删除
      sb.append(formatMsg(1)).append("@After" + NEXT_LINE);
      sb.append(formatMsg(1)).append("public void testDelete()  {").append(NEXT_LINE);
      sb.append(formatMsg(2)).append(beanDtoName).append(" paramBean = operDto;").append(NEXT_LINE);
      sb.append(formatMsg(2)).append("int rs = instDao.delete(paramBean);" + NEXT_LINE);
      sb.append(formatMsg(2)).append("Assert.assertEquals(1, rs);").append(NEXT_LINE);
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

  @Override
  public void autoCode(CreateParamBean param) {

    // 进行自动代码生成
    try {
      String daoInfPath = param.getFileBasePath() + "javabeanJunitDAO/";
      String javaPackage = param.getJavaPackage();
      this.enTestDaoCode(daoInfPath, javaPackage, param.getTableSpaceName(), param.getContext());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

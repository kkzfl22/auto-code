package com.kk.autocode.encode.code.microservice;

import com.kk.autocode.encode.base.TableProcessBase;
import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.bean.EncodeContext;
import com.kk.autocode.encode.code.AutoCodeInf;
import com.kk.autocode.encode.constant.CreateCommKey;
import com.kk.autocode.encode.constant.Symbol;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 数据库实体与领域层的对象转换
 *
 * @author liujun
 * @version 0.0.1
 * @since 2020/04/08
 */
public class JavaCodePOAssemblerCreate extends TableProcessBase implements AutoCodeInf {

  private static final String DOC_ANNO_MSG = "的数据库实体与领域实体转化";

  private static final String FILE_PATH = "repositoryDoAssembler";

  /** 将领域数据转换为领域数据 */
  private static final String TOPO = "toPO";

  private static final String TODO = "toDO";

  private static final String PO_NAME = "po";
  private static final String DOMAIN_NAME = "doEntity";

  /** 传输层的实体名称 */
  public static final String ASSEMBLER_DTO_PACKAGE = "repository.assembler";

  /** 实体转化的后缀名 */
  public static final String ASSEMBLER_PO = "POAssembler";

  /** 文件路径信息 */
  private String filePath;

  private void encodeServiceImpl(
      String path, String basePackageStrtmp, String tableSpace, EncodeContext context)
      throws Exception {

    File dirFile = new File(path);
    // 如果文件夹存在，则执行删除
    boolean exists = dirFile.exists();
    if (exists) {
      dirFile.delete();
    }

    dirFile.mkdirs();

    String basePackageStr = basePackageStrtmp;

    Map<String, TableInfoDTO> tableMap = context.getTableMap();
    Map<String, List<TableColumnDTO>> map = context.getColumnMap();
    Iterator<Entry<String, List<TableColumnDTO>>> tableNameEntry = map.entrySet().iterator();

    while (tableNameEntry.hasNext()) {
      Entry<String, List<TableColumnDTO>> tableEntry = tableNameEntry.next();

      // 获取列描述信息
      List<TableColumnDTO> columnList = tableEntry.getValue();

      StringBuilder sb = new StringBuilder();

      String tableName = tableEntry.getKey();

      String tableClassName = toJavaClassName(tableName);

      String serviceName = tableClassName;
      String className = serviceName + ASSEMBLER_PO;

      // po的传输层的类名
      String poClassName = tableClassName + JavaCodeRepositoryPoCreate.REPOSITORY_PO;
      // do领域层的类名
      String doClassName = tableClassName + JavaCodeDOCreate.DOMAIN_DO;

      // 定义包
      sb.append(JavaCodeKey.PACKAGE)
          .append(Symbol.SPACE)
          .append(basePackageStr)
          .append(ASSEMBLER_DTO_PACKAGE)
          .append(Symbol.SEMICOLON)
          .append(NEXT_LINE);
      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 1,导包
      // 导入dto的包
      sb.append(JavaCodeKey.IMPORT)
          .append(Symbol.SPACE)
          .append(basePackageStr)
          .append(JavaCodeRepositoryPoCreate.REPOSITORY_PO_PACKAGE)
          .append(Symbol.POINT)
          .append(poClassName)
          .append(Symbol.SEMICOLON)
          .append(NEXT_LINE);
      // 导入领域层的包
      sb.append(JavaCodeKey.IMPORT)
          .append(Symbol.SPACE)
          .append(basePackageStrtmp)
          .append(JavaCodeDOCreate.DOMAIN_DO_PACKAGE)
          .append(Symbol.POINT)
          .append(doClassName)
          .append(Symbol.SEMICOLON)
          .append(NEXT_LINE)
          .append(NEXT_LINE);

      TableInfoDTO tableMsgBean = tableMap.get(tableName);

      // 添加类注释信息
      sb.append(JavaCodeKey.ANNO_CLASS)
          .append(NEXT_LINE)
          .append(JavaCodeKey.ANNO_CLASS_MID)
          .append(tableMsgBean.getTableComment())
          .append(DOC_ANNO_MSG)
          .append(NEXT_LINE)
          .append(JavaCodeKey.ANNO_CLASS_MID)
          .append(NEXT_LINE)
          .append(JavaCodeKey.DOC_VERSION)
          .append(NEXT_LINE)
          .append(JavaCodeKey.DOC_AUTH)
          .append(NEXT_LINE)
          .append(JavaCodeKey.ANNO_OVER)
          .append(NEXT_LINE);

      sb.append(JavaCodeKey.ClASS_START).append(className).append(Symbol.BRACE_LEFT);
      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 添加将数据转化为领域对象的服务
      setdoentityData(sb, doClassName, poClassName, columnList);

      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);

      setPOData(sb, doClassName, poClassName, columnList);

      // 结束
      sb.append(Symbol.BRACE_RIGHT);

      String fileOut = path + className + JavaCodeKey.FILE_SUFFIX;
      FileUtils.writeFile(fileOut, sb);
    }
  }

  private void setdoentityData(
      StringBuilder sb, String doClassName, String poClassName, List<TableColumnDTO> columnList) {

    // 添加toDo方法，即将数据转化为领域层对象
    sb.append(formatMsg(1)).append(JavaCodeKey.ANNO_CLASS).append(NEXT_LINE);
    sb.append(formatMsg(1))
        .append(JavaCodeKey.ANNO_CLASS_MID)
        .append("将持久层的对象转化为领域层的对象")
        .append(NEXT_LINE);
    sb.append(formatMsg(1)).append(JavaCodeKey.ANNO_OVER).append(NEXT_LINE);
    sb.append(formatMsg(1))
        .append(JavaCodeKey.PUBLIC)
        .append(Symbol.SPACE)
        .append(JavaCodeKey.STATIC)
        .append(Symbol.SPACE)
        .append(doClassName)
        .append(Symbol.SPACE)
        .append(TODO)
        .append(Symbol.BRACKET_LEFT)
        .append(poClassName)
        .append(Symbol.SPACE)
        .append(PO_NAME)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.BRACE_LEFT)
        .append(NEXT_LINE);

    // 声明领域层实体对象
    sb.append(formatMsg(2))
        .append(doClassName)
        .append(Symbol.SPACE)
        .append(DOMAIN_NAME)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(JavaCodeKey.NEW)
        .append(Symbol.SPACE)
        .append(doClassName)
        .append(Symbol.BRACKET_LEFT)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);

    // 检查传递的参数是否为空，为空，则返回声明的对象
    sb.append(formatMsg(2))
        .append(JavaCodeKey.IF)
        .append(Symbol.BRACKET_LEFT)
        .append(JavaCodeKey.NULL)
        .append(Symbol.EQUALS)
        .append(Symbol.SPACE)
        .append(PO_NAME)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.BRACE_LEFT)
        .append(NEXT_LINE);
    sb.append(formatMsg(3))
        .append(JavaCodeKey.RETURN)
        .append(Symbol.SPACE)
        .append(DOMAIN_NAME)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(formatMsg(2)).append(Symbol.BRACE_RIGHT).append(NEXT_LINE);

    // 添加属性的信息
    for (int i = 0; i < columnList.size(); i++) {
      TableColumnDTO tableBean = columnList.get(i);
      String javaName = toProJavaName(tableBean.getColumnName());

      sb.append(formatMsg(2))
          .append(DOMAIN_NAME)
          .append(Symbol.POINT)
          .append(BusiJavaCodeKey.SET)
          .append(javaName)
          .append(Symbol.BRACKET_LEFT)
          .append(PO_NAME)
          .append(Symbol.POINT)
          .append(BusiJavaCodeKey.GET)
          .append(javaName)
          .append(Symbol.BRACKET_LEFT)
          .append(Symbol.BRACKET_RIGHT)
          .append(Symbol.BRACKET_RIGHT)
          .append(Symbol.SEMICOLON);
      sb.append(NEXT_LINE);
    }
    // 添加返回
    sb.append(formatMsg(2))
        .append(JavaCodeKey.RETURN)
        .append(Symbol.SPACE)
        .append(DOMAIN_NAME)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(formatMsg(1)).append(Symbol.BRACE_RIGHT).append(NEXT_LINE);
  }

  /**
   * 设置数据传输相关的信息
   *
   * @param sb
   * @param doClassName
   * @param poClassName
   * @param columnList
   */
  private void setPOData(
      StringBuilder sb, String doClassName, String poClassName, List<TableColumnDTO> columnList) {

    // 添加toDo方法，即将数据转化为领域层对象
    sb.append(formatMsg(1)).append(JavaCodeKey.ANNO_CLASS).append(NEXT_LINE);
    sb.append(formatMsg(1))
        .append(JavaCodeKey.ANNO_CLASS_MID)
        .append("将领域层的对象转换为持久层的实体")
        .append(NEXT_LINE);
    sb.append(formatMsg(1)).append(JavaCodeKey.ANNO_OVER).append(NEXT_LINE);
    sb.append(formatMsg(1))
        .append(JavaCodeKey.PUBLIC)
        .append(Symbol.SPACE)
        .append(JavaCodeKey.STATIC)
        .append(Symbol.SPACE)
        .append(poClassName)
        .append(Symbol.SPACE)
        .append(TOPO)
        .append(Symbol.BRACKET_LEFT)
        .append(doClassName)
        .append(Symbol.SPACE)
        .append(DOMAIN_NAME)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.BRACE_LEFT)
        .append(NEXT_LINE);

    // 声明领域层实体对象
    sb.append(formatMsg(2))
        .append(poClassName)
        .append(Symbol.SPACE)
        .append(PO_NAME)
        .append(Symbol.SPACE)
        .append(Symbol.EQUAL)
        .append(Symbol.SPACE)
        .append(JavaCodeKey.NEW)
        .append(Symbol.SPACE)
        .append(poClassName)
        .append(Symbol.BRACKET_LEFT)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);

    // 检查传递的参数是否为空，为空，则返回声明的对象
    sb.append(formatMsg(2))
        .append(JavaCodeKey.IF)
        .append(Symbol.BRACKET_LEFT)
        .append(JavaCodeKey.NULL)
        .append(Symbol.EQUALS)
        .append(Symbol.SPACE)
        .append(DOMAIN_NAME)
        .append(Symbol.BRACKET_RIGHT)
        .append(Symbol.BRACE_LEFT)
        .append(NEXT_LINE);
    sb.append(formatMsg(3))
        .append(JavaCodeKey.RETURN)
        .append(Symbol.SPACE)
        .append(PO_NAME)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(formatMsg(2)).append(Symbol.BRACE_RIGHT).append(NEXT_LINE);

    // 添加属性的信息
    for (int i = 0; i < columnList.size(); i++) {
      TableColumnDTO tableBean = columnList.get(i);
      String javaName = toProJavaName(tableBean.getColumnName());

      sb.append(formatMsg(2))
          .append(PO_NAME)
          .append(Symbol.POINT)
          .append(BusiJavaCodeKey.SET)
          .append(javaName)
          .append(Symbol.BRACKET_LEFT)
          .append(DOMAIN_NAME)
          .append(Symbol.POINT)
          .append(BusiJavaCodeKey.GET)
          .append(javaName)
          .append(Symbol.BRACKET_LEFT)
          .append(Symbol.BRACKET_RIGHT)
          .append(Symbol.BRACKET_RIGHT)
          .append(Symbol.SEMICOLON);
      sb.append(NEXT_LINE);
    }
    // 添加返回
    sb.append(formatMsg(2))
        .append(JavaCodeKey.RETURN)
        .append(Symbol.SPACE)
        .append(PO_NAME)
        .append(Symbol.SEMICOLON)
        .append(NEXT_LINE);
    sb.append(formatMsg(1)).append(Symbol.BRACE_RIGHT).append(NEXT_LINE);
  }

  @Override
  public void autoCode(CreateParamBean param) {
    // 进行自动代码生成
    try {
      String daoInfPath = param.getFileBasePath() + FILE_PATH + Symbol.PATH;
      String javaPackage = param.getJavaPackage();
      this.encodeServiceImpl(
          daoInfPath, javaPackage, param.getTableSpaceName(), param.getContext());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

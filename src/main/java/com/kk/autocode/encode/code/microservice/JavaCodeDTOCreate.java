package com.kk.autocode.encode.code.microservice;

import com.kk.autocode.encode.base.TableProcessBase;
import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.bean.EncodeContext;
import com.kk.autocode.encode.code.AutoCodeInf;
import com.kk.autocode.encode.constant.Symbol;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 创建数据库访问层的PO对象
 *
 * @author liujun
 * @version 0.0.1
 * @since 2020/04/08
 */
public class JavaCodeDTOCreate extends TableProcessBase implements AutoCodeInf {

  private static final String DOC_ANNO_MSG = "的实体传输实体信息";

  private static final String FILE_PATH = "interfacesdto";

  /** 传输层的实体名称 */
  public static final String REPOSITORY_DTO_PACKAGE = "interfaces.dto";

  /** 实体的后缀名 */
  public static final String REPOSITORY_PO = "DTO";

  /** 文件路径信息 */
  private String filePath;

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
    Iterator<Entry<String, List<TableColumnDTO>>> tableNameEntry = map.entrySet().iterator();

    while (tableNameEntry.hasNext()) {
      Entry<String, List<TableColumnDTO>> tableEntry = tableNameEntry.next();

      // 获取列描述信息
      List<TableColumnDTO> columnList = tableEntry.getValue();

      StringBuilder sb = new StringBuilder();

      String tableName = tableEntry.getKey();

      String tableClassName = toJavaClassName(tableName);

      String serviceName = tableClassName;
      String className = serviceName + REPOSITORY_PO;

      // 定义包
      sb.append(JavaCodeKey.PACKAGE)
          .append(Symbol.SPACE)
          .append(basePackageStr)
          .append(REPOSITORY_DTO_PACKAGE)
          .append(Symbol.SEMICOLON)
          .append(NEXT_LINE);
      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 1,导包
      sb.append(JavaCodeKey.BEAN_IMPORT_DATA).append(NEXT_LINE);
      sb.append(JavaCodeKey.BEAN_IMPORT_TOSTRING).append(NEXT_LINE);

      TableInfoDTO tableMsgBean = tableMap.get(tableName);

      // 添加类注释信息
      sb.append(JavaCodeKey.ANNO_CLASS)
          .append(NEXT_LINE)
          .append(JavaCodeKey.ANNO_CLASS_MID)
          .append(tableMsgBean.getTableComment())
          .append(Symbol.BRACKET_LEFT)
          .append(tableName)
          .append(Symbol.BRACKET_RIGHT)
          .append(DOC_ANNO_MSG)
          .append(NEXT_LINE)
          .append(JavaCodeKey.DOC_VERSION)
          .append(NEXT_LINE)
          .append(JavaCodeKey.DOC_AUTH)
          .append(NEXT_LINE)
          .append(JavaCodeKey.ANNO_OVER)
          .append(NEXT_LINE);

      // 引入@data和@toString
      sb.append(JavaCodeKey.BEAN_USE_DATA).append(NEXT_LINE);
      sb.append(JavaCodeKey.BEAN_USE_TOSTRING).append(NEXT_LINE);

      sb.append(JavaCodeKey.ClASS_START).append(className).append(Symbol.BRACE_LEFT);
      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);

      // 添加属性的信息
      for (int i = 0; i < columnList.size(); i++) {
        TableColumnDTO tableBean = columnList.get(i);
        String javaName = toJava(tableBean.getColumnName());
        sb.append(formatMsg(1)).append(JavaCodeKey.ANNO_CLASS).append(NEXT_LINE);
        sb.append(formatMsg(1))
            .append(JavaCodeKey.ANNO_CLASS_MID)
            .append(tableBean.getColumnMsg())
            .append(NEXT_LINE);
        sb.append(formatMsg(1)).append(JavaCodeKey.ANNO_OVER).append(NEXT_LINE);
        sb.append(formatMsg(1))
            .append(JavaCodeKey.PRIVATE)
            .append(Symbol.SPACE)
            .append(this.getJavaType(tableBean))
            .append(Symbol.SPACE)
            .append(javaName)
            .append(this.getJavaDefValue(tableBean))
            .append(Symbol.SEMICOLON);
        sb.append(NEXT_LINE);
        sb.append(NEXT_LINE);
      }

      // 结束
      sb.append(Symbol.BRACE_RIGHT);

      String fileOut = path + className + JavaCodeKey.FILE_SUFFIX;
      FileUtils.writeFile(fileOut, sb);
    }
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

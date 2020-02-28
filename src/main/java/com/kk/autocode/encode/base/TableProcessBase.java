package com.kk.autocode.encode.base;

import com.kk.autocode.console.ConfigEnum;
import com.kk.autocode.db.DbProcessInf;
import com.kk.autocode.db.mysql.DbMysqlProcessImpl;
import com.kk.autocode.db.oracle.DbOracleProcessImpl;
import com.kk.docprocess.doctoadapterdoc.process.impl.ValueProcess;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;
import com.kk.docprocess.doctoadapterdoc.process.impl.NameProcess;
import com.kk.element.common.constant.PropertyEnum;
import com.kk.element.common.properties.PropertiesUtils;
import com.kk.utils.SystemSymbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableProcessBase {

  /** 换行符 @字段说明 NEXT_LINE */
  protected static final String NEXT_LINE = SystemSymbol.GetLine();

  /** 制表符信息 @字段说明 TABLE_LINE */
  private static final String TABLE_LINE = "\t";

  /** 公共引入 @字段说明 COMM_SPIT_BEAN */
  protected static final String COMM_SPIT_BEAN = "com.omuit.common.bean.Pagination;";

  /** 进行dao的的包路径的引入 @字段说明 BASE_DAO_PATH */
  protected static final String BASE_DAO_PATH = "com.omuit.common.dao.BaseDAOImpl;";

  /** bean的名称 */
  protected static final String BASE_BENA_NAME = "dto";

  /** 数据库操作 @字段说明 dbProcess */
  private final DbProcessInf dbProcess;

  public TableProcessBase() {
    // 取得配制信息
    String dbType = PropertiesUtils.getInstance().getValue(PropertyEnum.DB_TYPE);

    // 如果当前为mysql,则执行mysql的信息
    if (ConfigEnum.DB_TYPE_MYSQL.getKey().equals(dbType)) {
      dbProcess = new DbMysqlProcessImpl();
    }
    // 如果当前为oracle，则执行oracle的查询
    else if (ConfigEnum.DB_TYPE_ORACLE.getKey().equals(dbType)) {
      dbProcess = new DbOracleProcessImpl();
    }
    // 默认使用mysql
    else {
      dbProcess = new DbMysqlProcessImpl();
    }
  }

  /**
   * 检查是否为联合多主键 方法描述
   *
   * @param list
   * @return @创建日期 2016年10月10日
   */
  protected boolean checkMorePrimaryKey(List<TableColumnDTO> list) {

    if (null != list) {
      int keyNum = 0;
      for (TableColumnDTO TableBean : list) {
        if (TableBean.isPrimaryKey()) {
          keyNum = keyNum + 1;
        }
      }

      // 如果当前的主键不只一个，则说明为联合主键
      if (keyNum > 1) {
        return true;
      }
    }
    return false;
  }

  /**
   * 获取主键的key的信息 方法描述
   *
   * <p>支持单主键与联合主键
   *
   * @param list
   * @return @创建日期 2016年9月28日
   */
  protected List<TableColumnDTO> getPrimaryKey(List<TableColumnDTO> list) {

    List<TableColumnDTO> primaryKey = new ArrayList<>();

    if (list == null || list.isEmpty()) {
      return primaryKey;
    }

    for (TableColumnDTO tableBean : list) {
      if (tableBean.isPrimaryKey()) {
        primaryKey.add(tableBean);
      }
    }

    // 如果未定义主键，就使用第一个列
    if (primaryKey.isEmpty()) {
      primaryKey.add(list.get(0));
    }

    return primaryKey;
  }

  /**
   * 转换java的类名信息,首字母大写 方法描述
   *
   * @param tableName
   * @return @创建日期 2016年9月27日
   */
  protected String toJavaClassName(String tableName) {

    return NameProcess.INSTANCE.toProJavaName(tableName);
  }

  /**
   * 转换为action的名字 方法描述
   *
   * @param str
   * @return @创建日期 2016年9月30日
   */
  protected String toActionStr(String str) {
    return str.substring(0, 1).toLowerCase() + str.substring(1);
  }

  /**
   * 转换为java命名规则,首字线小写，其他的首字母大写 方法描述
   *
   * @param str
   * @return @创建日期 2016年9月28日
   */
  protected String toJava(String str) {
    String[] strs = str.split("_");
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < strs.length; i++) {
      if (i == 0) {
        sb.append(strs[i]);
      } else {
        sb.append(strs[i].substring(0, 1).toUpperCase());
        sb.append(strs[i].substring(1));
      }
    }
    return sb.toString();
  }

  /**
   * 转换为java属性get与set命名规则 方法描述
   *
   * @param str
   * @return @创建日期 2016年9月28日
   */
  protected String toProJavaName(String str) {
    String[] strs = str.split("_");
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < strs.length; i++) {
      sb.append(strs[i].substring(0, 1).toUpperCase());
      sb.append(strs[i].substring(1));
    }
    return sb.toString();
  }

  /**
   * 获得表列信息 方法描述
   *
   * @return
   * @throws Exception @创建日期 2016年9月12日
   */
  public Map<String, List<TableColumnDTO>> getTableColumnInfoByBean(String tableSpace)
      throws Exception {
    // 取得数据库操作类型
    Map<String, List<TableColumnDTO>> map = dbProcess.getTableColumnByBean(tableSpace);
    return map;
  }

  /**
   * 获得表信息 方法描述
   *
   * @return
   * @throws Exception @创建日期 2016年9月12日
   */
  public Map<String, TableInfoDTO> getTableInfoByBean(String tableSpace) throws Exception {
    // 取得数据库操作类型
    Map<String, TableInfoDTO> map = dbProcess.getTableInfo(tableSpace);
    return map;
  }

  /**
   * 获得列信息 方法描述
   *
   * @param tableSpace 参数
   * @throws Exception @创建日期 2016年9月12日
   */
  protected Map<String, List<TableColumnDTO>> getTableColumnInfoByMap(String tableSpace)
      throws Exception {
    return dbProcess.getTableColumnInfoByMap(tableSpace);
  }

  /**
   * 根据类型生成值的信息 方法描述
   *
   * @param bean 参数
   * @return @创建日期 2016年9月27日
   */
  protected String createValue(TableColumnDTO bean) {
    return dbProcess.createValue(bean);
  }

  /**
   * 得到java的数据类型 方法描述
   *
   * @param bean
   * @return @创建日期 2016年10月9日
   */
  protected String getJavaType(TableColumnDTO bean) {
    return dbProcess.parseJavaType(bean);
  }

  /**
   * 得到java的默认值 方法描述
   *
   * @param bean
   * @return @创建日期 2016年10月9日
   */
  protected String getJavaDefValue(TableColumnDTO bean) {
    return dbProcess.getJavaDefValue(bean);
  }

  /**
   * 获取Spring的名称的信息 方法描述
   *
   * @param name
   * @return @创建日期 2016年9月28日
   */
  protected String getSpringInstanceName(String name) {
    String tmpName = name;

    tmpName = tmpName.substring(0, 1).toLowerCase() + tmpName.substring(1);

    return tmpName;
  }

  /**
   * 进行格式化操作 方法描述
   *
   * @param num 输出\t的数量
   * @return @创建日期 2016年10月12日
   */
  protected String formatMsg(int num) {

    StringBuilder toMsg = new StringBuilder();
    if (num <= 0) {
      num = 1;
    }

    for (int i = 0; i < num; i++) {
      toMsg.append(TABLE_LINE);
    }

    return toMsg.toString();
  }

  /**
   * 进行特殊字符的处理操作
   *
   * @param comment 特殊字符
   * @return
   */
  protected String specialChar(String comment) {
    String result = comment;

    // 替换掉-号替换成*号
    result = result.replaceAll("-", "*");

    return result;
  }

  protected List<TableColumnDTO> copyList(List<TableColumnDTO> dataList) {
    List<TableColumnDTO> list = new ArrayList<>();

    for (TableColumnDTO columnDto : dataList) {
      list.add(columnDto);
    }

    return list;
  }

  /**
   * 输出属性文件信息
   *
   * @param sb 字符串
   * @param columnListParam 列信息
   */
  protected void outProperties(StringBuilder sb, List<TableColumnDTO> columnListParam) {

    // 拷贝集合
    List<TableColumnDTO> columnList = copyList(columnListParam);

    TableColumnDTO columnInfo = null;

    for (int i = 0; i < columnList.size(); i++) {

      // 检查当前列是否为自增长
      columnInfo = columnList.get(i);

      // 如果当前列为自增长，则不设置
      if (columnInfo.isAutoIncrement()) {
        continue;
      }

      String javaType = this.getJavaType(columnInfo);

      String value = createValue(columnList.get(i));

      // 添加当前的属性值的注释
      sb.append(formatMsg(2)).append("//");
      sb.append(columnList.get(i).getColumnMsg());
      sb.append(NEXT_LINE);
      // 添加生成代码的方法
      sb.append(formatMsg(2))
          .append("paramBean.set")
          .append(this.toProJavaName(columnList.get(i).getColumnName()))
          .append("(");
      sb.append(ValueProcess.INSTANCE.parseJavaValue(javaType, value));

      sb.append(");").append(NEXT_LINE);
    }
  }

  /**
   * 输出属性文件信息
   *
   * @param sb 字符串
   * @param tableColumnList 列信息
   */
  protected void outUpdateProperties(StringBuilder sb, List<TableColumnDTO> tableColumnList) {

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
      sb.append(formatMsg(2)).append("//");
      sb.append(columnList.get(i).getColumnMsg());
      sb.append(NEXT_LINE);
      // 添加生成代码的方法
      sb.append(formatMsg(2))
          .append("paramBean.set")
          .append(this.toProJavaName(columnList.get(i).getColumnName()))
          .append("(");
      sb.append(ValueProcess.INSTANCE.parseJavaValue(javaType, value));

      sb.append(");").append(NEXT_LINE);
    }
  }

  /**
   * 输出属性文件信息
   *
   * @param sb 字符串
   * @param columnListParam 列信息
   * @param beanDtoName javaBean的名称
   */
  protected void outJunitEquals(
      StringBuilder sb, List<TableColumnDTO> columnListParam, String beanDtoName) {

    // 拷贝集合
    List<TableColumnDTO> columnList = copyList(columnListParam);

    sb.append(formatMsg(2))
        .append(beanDtoName)
        .append(" rsGetBean = rsQuery.get(0);")
        .append(NEXT_LINE);

    for (int i = 0; i < columnList.size(); i++) {

      // 添加当前的属性值的注释
      sb.append(formatMsg(2)).append("//");
      sb.append(columnList.get(i).getColumnMsg());
      sb.append(NEXT_LINE);

      // 添加生成代码的方法
      sb.append(formatMsg(2));
      sb.append("Assert.assertEquals(");
      sb.append("rsGetBean.get")
          .append(this.toProJavaName(columnList.get(i).getColumnName()))
          .append("()");
      sb.append(",");
      sb.append("operDto.get");
      sb.append(this.toProJavaName(columnList.get(i).getColumnName())).append("()");
      sb.append(");");
      sb.append(NEXT_LINE);
    }
  }
}

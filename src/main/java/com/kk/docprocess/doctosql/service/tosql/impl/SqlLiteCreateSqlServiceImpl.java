package com.kk.docprocess.doctosql.service.tosql.impl;

import com.kk.autocode.util.StringUtils;
import com.kk.docprocess.docCommon.bean.TableBean;
import com.kk.docprocess.doctosql.service.tosql.CreateSqlService;
import com.kk.utils.SystemSymbol;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/** 用来生成sqllite相关的数据库操作 */
public class SqlLiteCreateSqlServiceImpl implements CreateSqlService {

  /** 是否有删除表的语句 @字段说明 ISDROP */
  private final boolean IS_DROP;

  /**
   * 换行符 方法描述
   *
   * @return @创建日期 2016年10月26日
   */
  private static final String LINE = SystemSymbol.GetLine();

  /** 制表符 @字段说明 TAB */
  private static final String TAB = "\t";

  /** 数据库列名长度限制 @字段说明 COLUMN_MAX_LENGTH */
  private static final int COLUMN_NAME_MAX_LENGTH = 30;

  /** 对齐进行字符补齐 @字段说明 ALIGN_LENGTH */
  private static final int ALIGN_LENGTH = 20;

  public SqlLiteCreateSqlServiceImpl(boolean idDrop) {
    this.IS_DROP = idDrop;
  }

  @Override
  public String toSqlStr(List<TableBean> wordTableList) {
    if (null != wordTableList && !wordTableList.isEmpty()) {
      String msg =
          wordTableList.stream().map(map -> toTableSqlFun(map)).reduce("", (s1, s2) -> s1 + s2);

      return msg;
    }
    return null;
  }

  /**
   * 使用函数式编程
   *
   * <p>Function<T, R>——接收T对象，返回R对象 方法描述
   *
   * @param word
   * @return @创建日期 2016年10月26日
   */
  public String toTableSqlFun(TableBean word) {
    StringBuilder sql = new StringBuilder();

    // 添加注释
    sql.append("-- ").append(word.getTableMsg()).append(LINE);

    if (IS_DROP) {
      sql.append("drop table IF EXISTS  ").append(word.getTableName()).append(";").append(LINE);
    }

    sql.append(LINE);
    sql.append(LINE);

    AtomicBoolean isautoIncre = new AtomicBoolean(false);
    sql.append("create table ").append(word.getTableName()).append("").append(LINE);
    sql.append("(").append(LINE);

    word.getColumnList().stream()
        .forEach(
            s -> {
              // 如果当前名称为空，则跳过
              if (org.apache.commons.lang3.StringUtils.isEmpty(s.getColumnName())) {
                return;
              }

              sql.append(TAB).append(columnFormarValue(s.getColumnName()));
              sql.append(TAB).append(alignFormarValue(s.getType()));
              // 是否允许为空
              if ("N".equals(s.getIsNullFlag())) {
                sql.append(alignFormarValue("not null "));
              } else {
                sql.append(alignFormarValue());
              }
              // 设置默认值
              if (!StringUtils.isEmpty(s.getDefaultValue())) {
                String defaultValue = "DEFAULT " + s.getDefaultValue();
                sql.append(alignFormarValue(defaultValue));
              } else {
                sql.append(alignFormarValue());
              }

              // 尾换行符
              sql.append(",");
              sql.append(LINE);
            });

    // 检查是否有主键,在没有主键的情况下去掉尾结束符
    if (!StringUtils.isEmpty(word.getPrimaryKey())) {
      // 检查是否存在逗号，
      String PkName = word.getPrimaryKey();
      PkName = PkName.replaceAll(",", "_");
      PkName = PkName.replaceAll(" ", "");
      // String pkName = "PK_" + word.getTableName() + PkName;
      // sql.append(TAB).append("constraint ").append(pkName);
      sql.append("  primary key (").append(word.getPrimaryKey()).append(")");
    } else {
      sql.setCharAt(sql.length() - LINE.length() - 1, ' ');
    }
    sql.append(LINE);
    sql.append(");");
    sql.append(LINE);
    sql.append(LINE);

    sql.append(LINE);
    sql.append(LINE);

    return sql.toString();
  }

  /**
   * 进行列名格式化 方法描述
   *
   * @param value
   * @return @创建日期 2016年10月26日
   */
  private String columnFormarValue(String value) {
    StringBuilder result = new StringBuilder();
    result.append(value);

    for (int i = value.length(); i < COLUMN_NAME_MAX_LENGTH; i++) {
      result.append(" ");
    }

    return result.toString();
  }

  /**
   * 进行列名格式化 方法描述
   *
   * @param value
   * @return @创建日期 2016年10月26日
   */
  private String alignFormarValue(String value) {
    StringBuilder result = new StringBuilder();
    result.append(value);

    for (int i = value.length(); i < ALIGN_LENGTH; i++) {
      result.append(" ");
    }

    return result.toString();
  }

  /**
   * 进行列名格式化 方法描述
   *
   * @return @创建日期 2016年10月26日
   */
  private String alignFormarValue() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < ALIGN_LENGTH; i++) {
      result.append(" ");
    }

    return result.toString();
  }
}

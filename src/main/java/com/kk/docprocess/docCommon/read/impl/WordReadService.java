package com.kk.docprocess.docCommon.read.impl;

import com.kk.docprocess.docCommon.bean.TableBean;
import com.kk.docprocess.docCommon.bean.TableColumnBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableRow;

import java.util.Collections;

/**
 * 进行单元格的数据读取
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/06/07
 */
public class WordReadService {

  public static final WordReadService INSTANCE = new WordReadService();

  /**
   * 获取数据信息
   *
   * @param tb
   * @return
   */
  public TableBean getTableInfo(Table tb) {

    TableBean table = null;
    // 迭代行，默认从0开始
    for (int i = 0; i < tb.numRows(); i++) {
      TableRow tr = tb.getRow(i);

      if (i == 0) {
        // 首行获取表数据
        table = this.getTable(tr);
      }
      // 此行为描述列头信息，需跳过
      else if (i == 1) {
        continue;
      }
      // 其他都为列描述信息
      else {
        // 添加其他列集合信息
        table.addColumnList(this.getColumnInfo(tr));
      }
    } // end for

    // 针对列信息进行排序
    if (!table.getColumnList().isEmpty()) {
      Collections.sort(table.getColumnList());
    }

    return table;
  }

  /**
   * 获取列信息 方法描述
   *
   * @param tr
   * @return @创建日期 2016年10月26日
   */
  private TableColumnBean getColumnInfo(TableRow tr) {
    TableColumnBean column = new TableColumnBean();

    // 序号
    String value = this.getCellValue(tr.getCell(0));
    if (!StringUtils.isEmpty(value)) {
      value = value.replace(" ", "");
      if (StringUtils.isNumeric(value)) {
        column.setSeqNum(Integer.parseInt(value));
      }
    }

    // 列名
    column.setColumnName(this.getCellValue(tr.getCell(1)));

    // 类型
    column.setType(this.getCellValue(tr.getCell(2)));

    // 根据数据库的类型获取
    // column.setDbType(TypeProcess.INSTANCE.);

    // 是否为空(Y/N)
    column.setIsNullFlag(this.getCellValue(tr.getCell(3)));

    // 默认值
    column.setDefaultValue(this.getCellValue(tr.getCell(4)));

    // 是否为自增加主键(Y/N)
    column.setAutoInctFlag(this.getCellValue(tr.getCell(5)));

    if (tr.numCells() > 6) {
      // 描述
      column.setDesc(this.getCellValue(tr.getCell(6)).trim());
    }

    return column;
  }

  /**
   * 得到表的基本信息 方法描述
   *
   * @param tr 行数据信息
   * @return @创建日期 2016年10月26日
   */
  private TableBean getTable(TableRow tr) {
    TableBean tableBean = new TableBean();

    // 表名
    tableBean.setTableName(this.getCellValue(tr.getCell(1)).toLowerCase());

    // 描述
    tableBean.setTableMsg(this.getCellValue(tr.getCell(3)));

    // 主键
    tableBean.setPrimaryKey(this.getCellValue(tr.getCell(5)));

    return tableBean;
  }

  /**
   * 获取单元格中的数据 方法描述
   *
   * @param td
   * @return @创建日期 2016年10月26日
   */
  private String getCellValue(TableCell td) {

    String msg = td.text();

    if (msg.indexOf("\r") != -1) {
      msg = msg.replaceAll("\r", ", ");
    }

    if (msg.indexOf("\n") != -1) {
      msg = msg.replaceAll("\n", "。");
    }

    return msg.substring(0, msg.length() - 1);
  }
}

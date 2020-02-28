package com.kk.element.database.mysql.service;

import com.kk.element.database.mysql.DAO.MySqlTableInfoDaoImpl;
import com.kk.element.database.mysql.pojo.MysqlTableInfoDTO;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * mysql表的服务信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/13
 */
public class MySqlTableInfoService {

  /** 静态方法 */
  public static final MySqlTableInfoService INSTANCE = new MySqlTableInfoService();

  public List<TableInfoDTO> selectAllTable(String databaseName) {

    // 1,查询所有表的信息
    List<TableInfoDTO> tableInfoList = MySqlTableInfoDaoImpl.INSTANCE.selectTableInfo(databaseName);

    // 2,查询所有表的列信息
    Map<String, List<TableColumnDTO>> columnMap =
        MysqlTableColumnService.INSTANCE.groupTableNameByColumn(databaseName);

    List<TableColumnDTO> columnList = null;

    // 3,将表与列的信息进行合并
    for (TableInfoDTO tableInfoItem : tableInfoList) {
      columnList = tableInfoItem.getColumnList();

      if (null == columnList) {
        tableInfoItem.setColumnList(columnMap.get(tableInfoItem.getTableName()));
      }
    }

    return tableInfoList;
  }
}

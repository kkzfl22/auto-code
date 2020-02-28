package com.kk.element.database.mysql.service;

import com.kk.element.database.mysql.DAO.MySqlTableColumnDaoImpl;
import com.kk.element.database.mysql.DAO.MySqlTableInfoDaoImpl;
import com.kk.element.database.mysql.pojo.MysqlTableColumnDTO;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进行mysql的表的列查询操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/13
 */
public class MysqlTableColumnService {

  /** 实例信息 */
  public static final MysqlTableColumnService INSTANCE = new MysqlTableColumnService();

  /**
   * 按表名对表列信息进行分组操作
   *
   * @return 分组的一列信息
   */
  public Map<String, List<TableColumnDTO>> groupTableNameByColumn(String databaseName) {

    List<TableColumnDTO> columnList =
        MySqlTableColumnDaoImpl.INSTANCE.selectTableColumn(databaseName);

    if (null != columnList && !columnList.isEmpty()) {
      Map<String, List<TableColumnDTO>> result = new HashMap<>();

      List<TableColumnDTO> list;

      for (TableColumnDTO tableColumnItem : columnList) {
        list = result.get(tableColumnItem.getTableName());

        if (null == list) {
          list = new ArrayList<>();
          result.put(tableColumnItem.getTableName(), list);
        }

        list.add(tableColumnItem);
      }

      return result;
    }

    return null;
  }
}

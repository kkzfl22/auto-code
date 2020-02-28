package com.kk.docprocess.docCommon.database.impl;

import com.kk.docprocess.docCommon.bean.TableBean;
import com.kk.docprocess.docCommon.bean.TableColumnBean;
import com.kk.docprocess.docCommon.database.MysqlTableService;
import com.kk.docprocess.doctoadapterdoc.process.impl.TypeProcess;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;
import com.kk.element.database.mysql.service.MySqlTableInfoService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行mysql表的服务获取
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/14
 */
public class MysqlTableServiceImpl implements MysqlTableService {

  public static final MysqlTableServiceImpl INSTANCE = new MysqlTableServiceImpl();

  @Override
  public List<TableBean> getTableInfo(String databaseName) throws Exception {

    List<TableInfoDTO> listTableInfo = MySqlTableInfoService.INSTANCE.selectAllTable(databaseName);

    return this.parseListTableInfo(listTableInfo);
  }

  /**
   * 进行集合的转换操作
   *
   * @param listTableInfo 集合信息
   * @return 结果信息
   */
  private List<TableBean> parseListTableInfo(List<TableInfoDTO> listTableInfo) {

    List<TableBean> list = new ArrayList<>(listTableInfo.size());

    for (TableInfoDTO dtoItem : listTableInfo) {
      // 进行转换操作
      list.add(parseTableInfo(dtoItem));
    }

    return list;
  }

  /**
   * 进行表的转换操作
   *
   * @param tableDto 表信息
   * @return 转换后的表信息
   */
  private TableBean parseTableInfo(TableInfoDTO tableDto) {
    TableBean table = new TableBean();
    table.setTableName(tableDto.getTableName());
    table.setTableMsg(tableDto.getTableComment());

    List<TableColumnBean> list = new ArrayList<>(tableDto.getColumnList().size());

    int index = 0;
    for (TableColumnDTO columnDto : tableDto.getColumnList()) {
      list.add(this.parseTableColumn(columnDto, index));
      index++;

      this.checkAndSetPrimaryKey(columnDto, table);
    }
    table.setColumnList(list);

    return table;
  }

  /**
   * 进行列的转换操作
   *
   * @param columnDto 列信息
   * @return 转换后的列信息
   */
  private TableColumnBean parseTableColumn(TableColumnDTO columnDto, int index) {
    TableColumnBean columnBean = new TableColumnBean();
    columnBean.setColumnName(columnDto.getColumnName());
    columnBean.setDbType(columnDto.getDataType());
    columnBean.setType(TypeProcess.INSTANCE.getJavaType(columnDto.getDataType()));
    columnBean.setDesc(columnDto.getColumnMsg());
    columnBean.setSeqNum(index);
    columnBean.setLength(columnDto.getDataLength());

    return columnBean;
  }

  /**
   * 检查并设置主键操作
   *
   * @param dto
   * @param tableBean
   */
  private void checkAndSetPrimaryKey(TableColumnDTO dto, TableBean tableBean) {
    if (dto.isPrimaryKey()) {
      if (StringUtils.isNotEmpty(tableBean.getPrimaryKey())) {
        tableBean.setPrimaryKey(tableBean.getPrimaryKey() + "," + tableBean.getTableName());
      } else {
        tableBean.setPrimaryKey(tableBean.getPrimaryKey());
      }
    }
  }
}

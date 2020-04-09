package com.kk.autocode.encode.bean;

import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成代码的上下文对象信息
 *
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月8日 下午5:05:20
 */
public class EncodeContext {

  /** 列描述的map信息 */
  private Map<String, List<TableColumnDTO>> columnMap;

  /** 表的描述的map信息 */
  private Map<String, TableInfoDTO> tableMap;

  /** 公共的临时参数 */
  private Map<String, Object> dataMap = new HashMap<>();

  public EncodeContext() {
    super();
  }

  public EncodeContext(
      Map<String, List<TableColumnDTO>> columnMap, Map<String, TableInfoDTO> tableMap) {
    super();
    this.columnMap = columnMap;
    this.tableMap = tableMap;
  }

  public Map<String, List<TableColumnDTO>> getColumnMap() {
    return columnMap;
  }

  public void setColumnMap(Map<String, List<TableColumnDTO>> columnMap) {
    this.columnMap = columnMap;
  }

  public Map<String, TableInfoDTO> getTableMap() {
    return tableMap;
  }

  public void setTableMap(Map<String, TableInfoDTO> tableMap) {
    this.tableMap = tableMap;
  }

  public Map<String, Object> getDataMap() {
    return dataMap;
  }
}

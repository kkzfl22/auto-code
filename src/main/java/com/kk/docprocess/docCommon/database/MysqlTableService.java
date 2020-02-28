package com.kk.docprocess.docCommon.database;

import com.kk.docprocess.docCommon.bean.TableBean;

import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/14
 */
public interface MysqlTableService {

  /**
   * 获取表中的数据信息转换为java数据信息
   *
   * @param databaseName 表空间
   * @return 返回数据信息
   * @throws Exception
   */
  List<TableBean> getTableInfo(String databaseName) throws Exception;
}

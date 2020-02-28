package com.kk.docprocess.doctosql.service.tosql;

import java.util.List;

import com.kk.docprocess.docCommon.bean.TableBean;

/**
 * 生成SQL服务
 *
 * @author liujun
 * @version 0.0.1
 */
public interface CreateSqlService {

  /**
   * 生成SQL的方法 方法描述
   *
   * @param wordTableList
   * @return @创建日期 2016年10月26日
   */
  String toSqlStr(List<TableBean> wordTableList);
}

package com.kk.docprocess.docCommon.read;

import org.apache.poi.hwpf.usermodel.Table;

import com.kk.docprocess.docCommon.bean.TableBean;

/** 单元格读取服务 */
public interface TableReadService {

  /**
   * 得到table的信息 方法描述
   *
   * @param tb
   * @return
   * @throws Exception @创建日期 2016年10月26日
   */
   TableBean getTableInfo(Table tb);
}

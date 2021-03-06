package com.kk.docprocess.docCommon.read.impl;

import com.kk.docprocess.docCommon.bean.TableBean;
import com.kk.docprocess.docCommon.read.TableReadService;
import org.apache.poi.hwpf.usermodel.Table;

/**
 * 进行hbase的doc文件中的table读取操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/06/07
 */
public class WordHbaseTableReadServiceImpl implements TableReadService {

  @Override
  public TableBean getTableInfo(Table tb) {

    TableBean table = WordReadService.INSTANCE.getTableInfo(tb);

    return table;
  }
}

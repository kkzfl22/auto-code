package com.kk.docprocess.docCommon.read;

import java.io.InputStream;
import java.util.List;

import com.kk.docprocess.docCommon.bean.TableBean;

/**
 * 进行文件读取服务
 *
 * @author liujun
 * @version 0.0.1
 * @since 2019/03/14
 */
public interface WordFileReadService {

  /**
   * 进行doc文件中在表格数据读取操作 方法描述
   *
   * @param input doc路径信息
   * @return
   * @throws Exception @创建日期 2016年10月26日
   */
  List<TableBean> readDocToList(InputStream input) throws Exception;
}

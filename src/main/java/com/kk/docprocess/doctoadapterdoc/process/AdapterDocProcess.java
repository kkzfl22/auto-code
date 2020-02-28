package com.kk.docprocess.doctoadapterdoc.process;

import java.util.List;
import java.util.Map;

import com.kk.docprocess.docCommon.bean.TableBean;
import com.kk.docprocess.doctoadapterdoc.bean.AdapterBean;

/**
 * 进行接口文档的生成的相关信息
 *
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月11日 下午12:28:16
 */
public interface AdapterDocProcess {

  /**
   * 进行自动化生成接口文档信息
   *
   * @param list 集合信息
   * @return 响应接口结果
   */
  Map<String, List<AdapterBean>> adapterProcess(List<TableBean> list);
}

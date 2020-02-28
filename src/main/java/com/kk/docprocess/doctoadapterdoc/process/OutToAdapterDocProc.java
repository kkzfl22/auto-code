package com.kk.docprocess.doctoadapterdoc.process;

import java.util.List;
import java.util.Map;

import com.kk.docprocess.doctoadapterdoc.bean.AdapterBean;

/**
 * 输出接口文档信息
 *
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月11日 下午3:01:07
 */
public interface OutToAdapterDocProc {

  /**
   * 输出信息到文件中
   *
   * @param apaterMap
   */
  void outputAdapterDoc(Map<String, List<AdapterBean>> apaterMap, String outPath);
}

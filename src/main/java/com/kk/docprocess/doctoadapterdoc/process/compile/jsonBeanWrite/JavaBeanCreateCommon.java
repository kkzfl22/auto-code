package com.kk.docprocess.doctoadapterdoc.process.compile.jsonBeanWrite;

import com.kk.autocode.util.StringUtils;
import com.kk.docprocess.doctoadapterdoc.bean.ParamBase;
import com.kk.docprocess.doctoadapterdoc.process.impl.NameProcess;

/**
 * 公共操作的java代码
 *
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月8日 下午5:26:10
 */
public class JavaBeanCreateCommon {

  /** 制表符信息 @字段说明 TABLE_LINE */
  protected static final String TABLE_LINE = "\t";

  protected static final String NEXT_LINE = "\r\n";

  /**
   * 转换为java命名规则,首字线小写，其他的首字母大写 方法描述
   *
   * @param str
   * @return @创建日期 2016年9月28日
   */
  protected String toJava(String str) {
    return NameProcess.INSTANCE.toJavaNameFirst(str);
  }



  /**
   * 转换为java属性get与set命名规则 方法描述
   *
   * @param str
   * @return @创建日期 2016年9月28日
   */
  protected String toProJavaName(String str) {
    String[] strs = str.split("_");
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < strs.length; i++) {
      sb.append(strs[i].substring(0, 1).toUpperCase());
      sb.append(strs[i].substring(1));
    }
    return sb.toString();
  }

  /**
   * 进行格式化操作 方法描述
   *
   * @param num 输出\t的数量
   * @return @创建日期 2016年10月12日
   */
  protected String formatMsg(int num) {

    StringBuilder toMsg = new StringBuilder();
    if (num <= 0) {
      num = 1;
    }

    for (int i = 0; i < num; i++) {
      toMsg.append(TABLE_LINE);
    }

    return toMsg.toString();
  }
}

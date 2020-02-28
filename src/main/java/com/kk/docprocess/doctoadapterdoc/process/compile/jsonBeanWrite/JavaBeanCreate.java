package com.kk.docprocess.doctoadapterdoc.process.compile.jsonBeanWrite;

import java.util.List;

import com.kk.docprocess.doctoadapterdoc.bean.ParamBase;
import com.kk.docprocess.doctoadapterdoc.process.impl.ValueProcess;

/**
 * 生成Bean的java代码
 *
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月8日 下午5:26:10
 */
public class JavaBeanCreate extends JavaBeanCreateCommon {

  /** 实例对象 */
  public static final JavaBeanCreate INSTANCE = new JavaBeanCreate();

  /**
   * 进行代码的生成，对象的信息
   *
   * @param columnList 集合信息
   * @param name javabean的名称
   * @return
   * @throws Exception
   */
  public String encodeServiceImpl(List<ParamBase> columnList, String name) throws Exception {

    StringBuilder sb = new StringBuilder();

    sb.append("public class ").append(name).append(" {").append(NEXT_LINE);
    sb.append(NEXT_LINE);

    // 添加属性的信息
    for (int i = 0; i < columnList.size(); i++) {
      ParamBase tableBean = columnList.get(i);
      sb.append(formatMsg(1))
          .append("private ")
          .append(tableBean.getParamType())
          .append(" ")
          .append(toJava(tableBean.getParamName()))
          .append(" = ")
          .append(
              ValueProcess.INSTANCE.getJavaDefValue(
                  tableBean.getDefValue(), tableBean.getParamType()))
          .append(";");
      sb.append(NEXT_LINE);
    }

    sb.append(NEXT_LINE);

    // 添加属性set的信息
    for (int i = 0; i < columnList.size(); i++) {
      ParamBase tableBean = columnList.get(i);
      String javaName = toJava(tableBean.getParamName());
      sb.append(formatMsg(1))
          .append("public void ")
          .append(" set")
          .append(this.toProJavaName(tableBean.getParamName()));
      sb.append("(").append(tableBean.getParamType());
      sb.append(" ");
      sb.append(javaName);
      sb.append(" ){").append(NEXT_LINE);
      sb.append(formatMsg(2))
          .append("this.")
          .append(javaName)
          .append("=")
          .append(javaName)
          .append(";")
          .append(NEXT_LINE);
      sb.append(formatMsg(1)).append("}");
      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);
    }

    // 添加属性get的信息
    for (int i = 0; i < columnList.size(); i++) {
      ParamBase tableBean = columnList.get(i);
      String javaName = toJava(tableBean.getParamName());
      sb.append(formatMsg(1)).append("public  ").append(tableBean.getParamType()).append(" get");
      sb.append(this.toProJavaName(tableBean.getParamName()));
      sb.append("(){");
      sb.append(NEXT_LINE);
      sb.append(formatMsg(2)).append("return this.");
      sb.append(javaName).append(";").append(NEXT_LINE);
      sb.append(formatMsg(1)).append("}");
      sb.append(NEXT_LINE);
      sb.append(NEXT_LINE);
    }

    // 结束
    sb.append(NEXT_LINE);

    sb.append("}").append(NEXT_LINE);

    return sb.toString();
  }
}

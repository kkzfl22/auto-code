package com.kk.docprocess.doctoadapterdoc.process.compile.jsonBeanWrite;

import java.util.List;

import com.kk.docprocess.doctoadapterdoc.bean.ParamBase;
import com.kk.docprocess.doctoadapterdoc.console.ProcEnum;
import com.kk.docprocess.doctoadapterdoc.process.impl.ValueProcess;

/**
 * 生成查询结果集Bean的java代码
 *
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月8日 下午5:26:10
 */
public class JavaBeanQueryCreate extends JavaBeanCreateCommon {

  /** 实例对象 */
  public static final JavaBeanQueryCreate INSTANCE = new JavaBeanQueryCreate();

  /**
   * 进行代码的生成，对象的信息
   *
   * @param columnList 集合信息
   * @param name javabean的名称
   * @return
   * @throws Exception
   */
  public String encodeServiceImpl(List<ParamBase> columnList, String name, ProcEnum proc)
      throws Exception {

    StringBuilder sb = new StringBuilder();

    sb.append("import java.util.List;").append(NEXT_LINE);
    sb.append("import java.util.ArrayList;").append(NEXT_LINE);
    sb.append(NEXT_LINE);

    sb.append("public class ").append(name).append(" {").append(NEXT_LINE);
    sb.append(NEXT_LINE);

    sb.append(NEXT_LINE);

    // 添加属性的信息
    for (int i = 0; i < columnList.size(); i++) {
      ParamBase tableBean = columnList.get(i);

      // 如果当前前集合类型则
      if (!"list".equals(tableBean.getParamName())) {
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

      } else {
        sb.append(formatMsg(1))
            .append("private List<")
            .append(getBeanName(tableBean.getParamType()))
            .append(proc.getKey())
            .append("> ")
            .append("list = new ArrayList<>();");
      }
      sb.append(NEXT_LINE);
    }
    // 需要再多生成一个实体的引用

    sb.append(NEXT_LINE);

    // 添加属性的信息
    for (int i = 0; i < columnList.size(); i++) {
      ParamBase tableBean = columnList.get(i);
      // 如果当前前集合类型则
      if ("list".equals(tableBean.getParamName())) {
        sb.append(formatMsg(1)).append("public void init(){").append(NEXT_LINE);
        sb.append(formatMsg(2))
            .append(getBeanName(tableBean.getParamType()))
            .append(proc.getKey())
            .append(" items = new ")
            .append(getBeanName(tableBean.getParamType()))
            .append(proc.getKey());
        sb.append("();").append(NEXT_LINE);
        sb.append(formatMsg(2)).append("this.list.add(items);").append(NEXT_LINE);
        sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
        break;
      }
    }

    // 添加属性set的信息
    for (int i = 0; i < columnList.size(); i++) {
      ParamBase tableBean = columnList.get(i);
      String javaName = toJava(tableBean.getParamName());
      sb.append(formatMsg(1))
          .append("public void ")
          .append(" set")
          .append(this.toProJavaName(tableBean.getParamName()));
      sb.append("(");

      if ("list".equals(tableBean.getParamName())) {
        sb.append("List<")
            .append(getBeanName(tableBean.getParamType()))
            .append(proc.getKey())
            .append("> ");
      } else {
        sb.append(tableBean.getParamType());
      }
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
      sb.append(formatMsg(1)).append("public  ");

      if ("list".equals(tableBean.getParamName())) {
        sb.append("List<")
            .append(getBeanName(tableBean.getParamType()))
            .append(proc.getKey())
            .append("> ");
      } else {
        sb.append(tableBean.getParamType());
      }

      sb.append(" get");
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

  public String getBeanName(String type) {
    String tmpStr = type;

    return tmpStr.replaceAll("\\[\\]", "");
  }
}

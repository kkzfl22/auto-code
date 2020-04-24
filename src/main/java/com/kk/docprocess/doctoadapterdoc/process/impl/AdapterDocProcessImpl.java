package com.kk.docprocess.doctoadapterdoc.process.impl;

import com.kk.docprocess.docCommon.bean.TableBean;
import com.kk.docprocess.docCommon.bean.TableColumnBean;

import com.kk.docprocess.doctoadapterdoc.bean.AdapterBean;
import com.kk.docprocess.doctoadapterdoc.bean.AdapterResponse;
import com.kk.docprocess.doctoadapterdoc.bean.ParamBase;
import com.kk.docprocess.doctoadapterdoc.console.ProcEnum;
import com.kk.docprocess.doctoadapterdoc.process.AdapterDocProcess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进行接口文档的自动化生成工作
 *
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月11日 下午12:31:05
 */
public class AdapterDocProcessImpl implements AdapterDocProcess {

  @Override
  public Map<String, List<AdapterBean>> adapterProcess(List<TableBean> list) {

    Map<String, List<AdapterBean>> adapterMap = new HashMap<>();

    // 首先生成接口的请求参数
    try {
      List<TableBean> procList = NameProcess.INSTANCE.procTableBean(list);

      TableBean wordTableBean = null;

      for (int i = 0; i < procList.size(); i++) {

        wordTableBean = procList.get(i);

        List<AdapterBean> adapterList = adapterMap.get(wordTableBean.getTableMsg());

        if (null == adapterList) {
          adapterList = new ArrayList<>();
        }

        adapterList.add(parseAdapterBean(wordTableBean, ProcEnum.INSERT));
        adapterList.add(parseAdapterBean(wordTableBean, ProcEnum.UPDATE));
        adapterList.add(parseAdapterBean(wordTableBean, ProcEnum.DELETE));
        adapterList.add(parseAdapterBean(wordTableBean, ProcEnum.QUERY));
        adapterList.add(parseAdapterBean(wordTableBean, ProcEnum.QUERYPAGE));

        adapterMap.put(wordTableBean.getTableMsg(), adapterList);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return adapterMap;
  }

  /**
   * 进行一个完整的请求的转换
   *
   * @param wordTableBean
   * @param proc 属性参数
   * @return
   */
  private AdapterBean parseAdapterBean(TableBean wordTableBean, ProcEnum proc) {

    String baseNameUrl = NameProcess.INSTANCE.toJava(wordTableBean.getTableName());
    AdapterBean adapter = new AdapterBean();

    adapter.setProc(proc);

    // 类名命令，首字母大字，其他小写
    adapter.setName(NameProcess.INSTANCE.toProJavaName(wordTableBean.getTableName()));

    // 设置url信息
    adapter.setUrl(
        baseNameUrl
            + ProcEnum.PROC_URL_SUFFIX.getKey()
            + "/"
            + proc.getKey()
            + ProcEnum.PROC_SUFFIX.getKey());
    adapter.setMsg(wordTableBean.getTableMsg() + "——" + proc.getMsg());

    // 设置请求参数信息
    if (ProcEnum.INSERT.getKey().equals(proc.getKey())) {
      adapter.setRequest(getAdapterParam(wordTableBean.getColumnList(), true));
    } else {
      if (ProcEnum.QUERYPAGE.getKey().equals(proc.getKey())) {
        List<ParamBase> pageList = getAdapterParam(wordTableBean.getColumnList(), false);
        int LastSize = Integer.parseInt(pageList.get(pageList.size() - 1).getParamSeq());
        ParamBase pageNum =
            new ParamBase((LastSize + 1) + "", "pageNum", "int", "N", "", "分页查询当前页");
        pageNum.setDbType("int");
        pageList.add(pageNum);
        ParamBase pageSize =
            new ParamBase((LastSize + 2) + "", "pageSize", "int", "N", "", "每页显示的条数");
        pageSize.setDbType("int");
        pageList.add(pageSize);
        adapter.setRequest(pageList);
      } else {
        adapter.setRequest(getAdapterParam(wordTableBean.getColumnList(), false));
      }
    }

    // 设置响应参数，首先区分是普通的增删改操作，还是查询操作,因为普通的增删改仅有响应头，内容没有，而查询会有内容信息
    AdapterResponse rsp = new AdapterResponse();

    List<ParamBase> commList = new ArrayList<>();

    ParamBase result = new ParamBase("1", "result", "boolean", "N", "", "当前操作是否成功,true成功,false失败");
    result.setDbType("boolean");
    commList.add(result);
    ParamBase errorCode = new ParamBase("2", "code", "int", "Y", "", "当前操作的错误码");
    errorCode.setDbType("int");
    commList.add(errorCode);
    ParamBase errorMsg = new ParamBase("3", "msg", "String", "Y", "", "错误信息");
    errorMsg.setDbType("varchar");
    commList.add(errorMsg);

    if (ProcEnum.QUERYPAGE.getKey().equals(proc.getKey())) {
      String className = NameProcess.INSTANCE.toJavaClassName(wordTableBean.getTableName());
      ParamBase count = new ParamBase("4", "count", "int", "Y", "", "查询结果的条数");
      count.setDbType("int");
      commList.add(count);
      ParamBase list = new ParamBase("5", "data", className + "[]", "Y", "", "查询结果");
      list.setDbType("varchar");
      commList.add(list);
    }

    if (ProcEnum.QUERY.getKey().equals(proc.getKey())) {
      String className = NameProcess.INSTANCE.toJavaClassName(wordTableBean.getTableName());
      ParamBase count = new ParamBase("4", "count", "int", "Y", "", "查询结果的条数");
      count.setDbType("int");
      commList.add(count);
      ParamBase list = new ParamBase("5", "data", className, "Y", "", "查询结果");
      list.setDbType("varchar");
      commList.add(list);

      rsp.setFlag(ProcEnum.QUERY.getKey());
    }

    // 设置公共结果
    rsp.setCommRsp(commList);

    if (ProcEnum.QUERY.getKey().equals(proc.getKey())
        || ProcEnum.QUERYPAGE.getKey().equals(proc.getKey())) {
      // 设置具体的响应结果信息
      rsp.setRspDataList(getAdapterParam(wordTableBean.getColumnList(), false));
    }

    adapter.setResponse(rsp);

    return adapter;
  }

  /**
   * 进行的转换成打印的bean信息
   *
   * @param columnList 列信息
   * @param addProc 是否为添加
   * @return 转换后需要打印的javabean
   */
  private List<ParamBase> getAdapterParam(List<TableColumnBean> columnList, boolean addProc) {
    List<ParamBase> listParam = new ArrayList<>();

    ParamBase paramBean = null;

    TableColumnBean tableColumnBean = null;

    int index = 1;
    for (int i = 0; i < columnList.size(); i++) {

      tableColumnBean = columnList.get(i);

      paramBean = new ParamBase();

      // 如果当前是增加，不需要主键不设置
      if (addProc) {
        if ("y".equalsIgnoreCase(tableColumnBean.getAutoInctFlag())) {
          continue;
        }
      }
      paramBean.setParamSeq(String.valueOf(index));
      paramBean.setParamName(NameProcess.INSTANCE.toJava(tableColumnBean.getColumnName()));
      paramBean.setDbType(tableColumnBean.getType().toLowerCase());
      paramBean.setParamType(TypeProcess.INSTANCE.getJavaType(tableColumnBean.getDbType()));
      paramBean.setNullFlag(tableColumnBean.getIsNullFlag());
      paramBean.setMsg(tableColumnBean.getDesc());

      listParam.add(paramBean);

      index++;
    }

    return listParam;
  }
}

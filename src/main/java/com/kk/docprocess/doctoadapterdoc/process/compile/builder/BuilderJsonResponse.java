package com.kk.docprocess.doctoadapterdoc.process.compile.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kk.docprocess.doctoadapterdoc.bean.AdapterResponse;
import com.kk.docprocess.doctoadapterdoc.bean.ParamBase;
import com.kk.docprocess.doctoadapterdoc.console.ProcEnum;
import com.kk.docprocess.doctoadapterdoc.process.compile.jsonBeanWrite.JavaBeanCreate;
import com.kk.docprocess.doctoadapterdoc.process.compile.jsonBeanWrite.JavaBeanQueryCreate;
import com.kk.docprocess.doctoadapterdoc.process.impl.ValueProcess;
import com.kk.utils.FileWriteUtils;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 编译响应的动态构建
 *
 * @since 2018年5月12日 下午8:23:13
 * @version 0.0.1
 * @author liujun
 */
public class BuilderJsonResponse {

  private static final String DATA = "data";

  /** 实例对象 */
  public static final BuilderJsonResponse INSTANCE = new BuilderJsonResponse();

  /** */
  private Gson gson = new GsonBuilder().setPrettyPrinting().create();

  @SuppressWarnings({"rawtypes", "unchecked"})
  public String[] buildResponse(AdapterResponse response) {

    JsonObject result = new JsonObject();

    // 添加属性的信息
    for (int i = 0; i < response.getCommRsp().size(); i++) {
      ParamBase tableBean = response.getCommRsp().get(i);

      setProperties(tableBean, result);
    }

    // 当检查到当前为查询详细标识时
    if (null != response.getRspDataList() && ProcEnum.QUERY.getKey().equals(response.getFlag())) {
      JsonObject dataListObject = new JsonObject();
      for (ParamBase paramData : response.getRspDataList()) {
        setProperties(paramData, dataListObject);
      }
      result.add(DATA, dataListObject);
    } else {
      if (null != response.getRspDataList() && response.getRspDataList().size() > 1) {
        JsonObject dataListObject = new JsonObject();
        JsonArray array = new JsonArray();
        for (ParamBase paramData : response.getRspDataList()) {
          setProperties(paramData, dataListObject);
        }
        array.add(dataListObject);
        result.add(DATA, array);
      }
    }

    String dataValue = gson.toJson(result);

    return dataValue.split("\n");
  }

  /**
   * 设置属性信息
   *
   * @param tableBean 列信息
   * @param result 结果对象信息
   */
  private void setProperties(ParamBase tableBean, JsonObject result) {
    String value =
        ValueProcess.INSTANCE.getJavaDefValue(tableBean.getDefValue(), tableBean.getDbType());

    result.addProperty(tableBean.getParamName(), value);
  }
}

package com.kk.docprocess.doctoadapterdoc.process.compile.builder;

import com.google.gson.Gson;
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

  /** 实例对象 */
  public static final BuilderJsonResponse INSTANCE = new BuilderJsonResponse();

  /** */
  private Gson gson = new Gson();

  @SuppressWarnings({"rawtypes", "unchecked"})
  public String buildResponse(AdapterResponse response) {

    JsonObject result = new JsonObject();

    // 添加属性的信息
    for (int i = 0; i < response.getCommRsp().size(); i++) {
      ParamBase tableBean = response.getCommRsp().get(i);

      setProperties(tableBean, result);
    }

    JsonArray array = new JsonArray();
    JsonObject dataListObject = new JsonObject();

    if (null != response.getRspDataList()) {
      for (ParamBase paramData : response.getRspDataList()) {
        setProperties(paramData, dataListObject);
      }
      array.add(dataListObject);
      result.add("list", array);
    }

    return gson.toJson(result);
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

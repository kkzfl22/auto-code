package com.kk.docprocess.doctoadapterdoc.process.compile.builder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kk.docprocess.doctoadapterdoc.bean.ParamBase;
import com.kk.docprocess.doctoadapterdoc.console.ProcEnum;
import com.kk.docprocess.doctoadapterdoc.process.compile.jsonBeanWrite.JavaBeanCreate;
import com.kk.docprocess.doctoadapterdoc.process.impl.ValueProcess;
import com.kk.utils.FileWriteUtils;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 通过直接构建json字符串来返回，不通过编译生成java文件 */
public class BuilderJsonRequest {

  /** 实例对象 */
  public static final BuilderJsonRequest INSTANCE = new BuilderJsonRequest();

  /** */
  private Gson gson = new Gson();

  @SuppressWarnings({"rawtypes", "unchecked"})
  public String buildRequest(List<ParamBase> columnList) {

    JsonObject result = new JsonObject();

    // 添加属性的信息
    for (int i = 0; i < columnList.size(); i++) {
      ParamBase tableBean = columnList.get(i);
      //
      setProperties(tableBean, result);
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

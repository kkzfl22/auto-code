package com.kk.docprocess.doctoadapterdoc.process.compile.builder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.google.gson.Gson;
import com.kk.docprocess.doctoadapterdoc.bean.AdapterResponse;
import com.kk.docprocess.doctoadapterdoc.console.ProcEnum;
import com.kk.docprocess.doctoadapterdoc.process.compile.jsonBeanWrite.JavaBeanCreate;
import com.kk.docprocess.doctoadapterdoc.process.compile.jsonBeanWrite.JavaBeanQueryCreate;
import com.kk.utils.FileWriteUtils;

/**
 * 编译响应的动态构建
 *
 * @since 2018年5月12日 下午8:23:13
 * @version 0.0.1
 * @author liujun
 */
public class BuilderComperResponse {

  /** 实例对象 */
  public static final BuilderComperResponse INSTANCE = new BuilderComperResponse();

  /** 基础的jsonbean操作 */
  private JavaBeanCreate jsonBean = new JavaBeanCreate();

  /** 进行json的javabean创建操作 */
  private JavaBeanQueryCreate jsonQuerybean = new JavaBeanQueryCreate();

  /** */
  private Gson gson = new Gson();

  @SuppressWarnings({"rawtypes", "unchecked"})
  public String buildResponse(AdapterResponse response, String namekey, ProcEnum proc) {

    try {
      String procName = namekey + proc.getKey();
      // 获得当前的基路径
      String basePath = this.getClass().getClassLoader().getResource(".").getPath();

      if (null != response.getRspDataList() && !response.getRspDataList().isEmpty()) {

        // 1 ，获得编译信息中集合中的源码文件
        String encode = jsonBean.encodeServiceImpl(response.getRspDataList(), procName);
        // 进行动态编译操作
        compileCode(basePath, procName, encode);
      }

      String rspBase = namekey + proc.getKey() + "comm";
      // 3,进行公共的集合的编译
      String encodeBase = jsonQuerybean.encodeServiceImpl(response.getCommRsp(), rspBase, proc);

      // 进行动态编译操作
      compileCode(basePath, rspBase, encodeBase);

      // 动态执行
      Class klass = Class.forName(rspBase);
      Object instance = null;

      if (null != response.getRspDataList() && !response.getRspDataList().isEmpty()) {
        // 调用初始化方法
        Method initMethod = klass.getMethod("init", new Class[0]);
        instance = klass.newInstance();
        initMethod.invoke(instance, new Object[0]);
      } else {
        instance = klass.newInstance();
      }

      String objJson = gson.toJson(instance);

      // 将文件删除
      new File(basePath, rspBase + ".java").delete();
      new File(basePath, rspBase + ".class").delete();
      new File(basePath, procName + ".java").delete();
      new File(basePath, procName + ".class").delete();

      return objJson;
      // return prettyJsonString;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 进行动态编译操作
   *
   * @param basePath 基础的路径信息
   * @param procName 处理的类名
   * @param encode 内容 信息
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  private void compileCode(String basePath, String procName, String encode) {

    StandardJavaFileManager javaFileManager = null;

    try {

      File compFile = new File(basePath + procName + ".java");
      FileWriteUtils.writeFileCode(compFile.getPath(), encode);

      // 获取编译器
      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

      // 创建诊断信息监听器, 用于诊断信息
      DiagnosticCollector<JavaFileObject> diagnosticListeners = new DiagnosticCollector<>();

      // 获取FileManager
      javaFileManager = compiler.getStandardFileManager(diagnosticListeners, null, null);
      Iterable it = javaFileManager.getJavaFileObjects(compFile);

      // 生编译任务
      JavaCompiler.CompilationTask task =
          compiler.getTask(
              null, javaFileManager, diagnosticListeners, Arrays.asList("-d", basePath), null, it);
      // 执行编译任务
      task.call();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // 关闭FileManager
      try {
        javaFileManager.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

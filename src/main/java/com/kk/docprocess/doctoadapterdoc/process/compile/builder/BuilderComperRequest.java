package com.kk.docprocess.doctoadapterdoc.process.compile.builder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kk.docprocess.doctoadapterdoc.bean.ParamBase;
import com.kk.docprocess.doctoadapterdoc.process.compile.jsonBeanWrite.JavaBeanCreate;
import com.kk.utils.FileWriteUtils;

/** 采用编译代码的方式生成json字符信息 */
public class BuilderComperRequest {

  /** 实例对象 */
  public static final BuilderComperRequest INSTANCE = new BuilderComperRequest();

  /** 基础的jsonbean操作 */
  private JavaBeanCreate jsonBean = new JavaBeanCreate();

  /** */
  private Gson gson = new Gson();

  @SuppressWarnings({"rawtypes", "unchecked"})
  public String buildRequest(List<ParamBase> columnList, String namekey) {

    StandardJavaFileManager javaFileManager = null;

    try {
      String procName = namekey;

      // 1 ，获得编译的源码文件
      String encode = jsonBean.encodeServiceImpl(columnList, procName);
      // 2,将源码文件写入到文件中
      // 获得当前的基路径
      String basePath = this.getClass().getClassLoader().getResource(".").getPath();

      File compFile = new File(basePath + procName + ".java");
      FileWriteUtils.writeFileCode(compFile.getPath(), encode);

      // 获取编译器
      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

      // 创建诊断信息监听器, 用于手机诊断信息
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

      // 返射实例化对象
      // 动态执行
      Class klass = Class.forName(procName);
      Object instance = klass.newInstance();

      String objJson = gson.toJson(instance);

      // 将文件删除
      new File(basePath, procName + ".java").delete();
      new File(basePath, procName + ".class").delete();

      return objJson;
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

    return null;
  }
}

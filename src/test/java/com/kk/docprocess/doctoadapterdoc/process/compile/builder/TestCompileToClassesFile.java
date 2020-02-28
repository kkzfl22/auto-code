package com.kk.docprocess.doctoadapterdoc.process.compile.builder;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/14
 */
public class TestCompileToClassesFile {

  /**
   * @author ZhangXiang
   * @param args 2011-4-7
   */
  public static void main(String[] args) {

    StringBuilder classStr =
        new StringBuilder(
            "package com.kk.docprocess.doctoadapterdoc.process.compile.builder;public class Foo implements Test{");
    classStr.append("public void test(){");
    classStr.append("System.out.println(\"Foo2\");}}");

    JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
    StandardJavaFileManager fileManager = jc.getStandardFileManager(null, null, null);
    JavaFileManager.Location location = StandardLocation.CLASS_OUTPUT;
    File[] outputs = new File[] {new File("target/test-classes")};
    try {
      fileManager.setLocation(location, Arrays.asList(outputs));
    } catch (IOException e) {
      e.printStackTrace();
    }

    JavaFileObject jfo =
        new JavaSourceFromString(
            "com.kk.docprocess.doctoadapterdoc.process.compile.builder.Foo", classStr.toString());

    new JavaSourceFromString(
        "com.kk.docprocess.doctoadapterdoc.process.compile.builder.Foo", classStr.toString());
    JavaFileObject[] jfos = new JavaFileObject[] {jfo};
    Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(jfos);
    boolean b = jc.getTask(null, fileManager, null, null, null, compilationUnits).call();
    if (b) { // 如果编译成功
      try {
        Test t = (Test) Class.forName("com.kk.docprocess.doctoadapterdoc.process.compile.builder.Foo").newInstance();
        t.test();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }
}

package com.kk.autocode.encode.code.microservice;

import com.kk.autocode.util.IOutils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件操作公共方法
 *
 * @author liujun
 * @version 0.0.1
 */
public class FileUtils {

  public static final void writeFile(String filePath, StringBuilder data) {

    FileWriter fw = null;
    BufferedWriter buffOut = null;
    try {
      fw = new FileWriter(new File(filePath));
      buffOut = new BufferedWriter(fw);
      buffOut.write(data.toString());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOutils.closeStream(buffOut);
      IOutils.closeStream(fw);
    }
  }
}

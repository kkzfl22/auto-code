package com.kk.autocode.encode.code.bean.microservice;

/**
 * @author liujun
 * @version 0.0.1
 */
public class MyBatisKey {

  /** xml文件的定义头 */
  public static final String HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

  public static final String DEFINE =
      "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"  \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">";

  public static final String START = "<";
  public static final String END = ">";
  public static final String OVER = "/>";

  /** 注释 开始 */
  public static final String DOC_START = "<!--";

  /** 注释结束 */
  public static final String DOC_END = "-->";
}

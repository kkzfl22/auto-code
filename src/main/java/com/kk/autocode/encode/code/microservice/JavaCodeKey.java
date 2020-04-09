package com.kk.autocode.encode.code.microservice;

/**
 * 定义java的关键字
 *
 * @author liujun
 * @version 0.0.1
 */
public class JavaCodeKey {

  /** 文件后缀名 */
  public static final String FILE_SUFFIX = ".java";

  public static final String PACKAGE = "package";

  public static final String IMPORT = "import";

  public static final String PUBLIC = "public";

  public static final String THIS = "this";

  public static final String NEW = "new";

  public static final String RETURN = "return";

  public static final String INT_TYPE = "int";

  public static final String INTERFACE = "interface";

  /** 类注释开始 */
  public static final String ANNO_CLASS = "/**";

  /** 类注释第二行 */
  public static final String ANNO_CLASS_MID = " * ";

  /** 注释结束 */
  public static final String ANNO_OVER = " */";

  public static final String DOC_VERSION = " * @version 0.0.1";

  public static final String DOC_AUTH = " * @author liujun";

  /** 类开头 */
  public static final String ClASS_START = "public class ";

  /** private 关键字 */
  public static final String PRIVATE = "private";

  /** 用来生成get和set方法 */
  public static final String BEAN_IMPORT_DATA = "import lombok.Data;";

  /** 用来生成toString方法 */
  public static final String BEAN_IMPORT_TOSTRING = "import lombok.ToString;";

  /** 用来注解data */
  public static final String BEAN_USE_DATA = "@Data";

  /** 用来生成toString */
  public static final String BEAN_USE_TOSTRING = "@ToString";

  public static final String LIST_TYPE = "List<";

  public static final String LIST_TYPE_END = ">";
}

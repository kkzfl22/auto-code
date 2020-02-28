package com.kk.autocode.encode.bean;

/** 生成的参数的bean的信息 */
public class CreateParamBean {

  /** 文件的基本路径 @字段说明 fileBasePath */
  private String fileBasePath;

  /** java的包的命名信息 @字段说明 javaPackage */
  private String javaPackage;

  /** mybatis的namespace信息 @字段说明 tableSpace */
  private String mybatisBaseSpace;

  /** 表空间名称 @字段说明 tableSpace */
  private String tableSpaceName;

  /** 公共的上下文信息 */
  private EncodeContext context;

  public CreateParamBean(
      String fileBasePath, String javaPackage, String mybatisBaseSpace, String tableSpaceName) {
    super();
    this.fileBasePath = fileBasePath;
    this.javaPackage = javaPackage;
    this.mybatisBaseSpace = mybatisBaseSpace;
    this.tableSpaceName = tableSpaceName;
  }

  public String getFileBasePath() {
    return fileBasePath;
  }

  public void setFileBasePath(String fileBasePath) {
    this.fileBasePath = fileBasePath;
  }

  public String getJavaPackage() {
    return javaPackage;
  }

  public void setJavaPackage(String javaPackage) {
    this.javaPackage = javaPackage;
  }

  public String getMybatisBaseSpace() {
    return mybatisBaseSpace;
  }

  public void setMybatisBaseSpace(String mybatisBaseSpace) {
    this.mybatisBaseSpace = mybatisBaseSpace;
  }

  public String getTableSpaceName() {
    return tableSpaceName;
  }

  public void setTableSpaceName(String tableSpaceName) {
    this.tableSpaceName = tableSpaceName;
  }

  public EncodeContext getContext() {
    return context;
  }

  public void setContext(EncodeContext context) {
    this.context = context;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CreateParamBean [fileBasePath=");
    builder.append(fileBasePath);
    builder.append(", javaPackage=");
    builder.append(javaPackage);
    builder.append(", mybatisBaseSpace=");
    builder.append(mybatisBaseSpace);
    builder.append(", tableSpaceName=");
    builder.append(tableSpaceName);
    builder.append("]");
    return builder.toString();
  }
}

package com.kk.autocode.encode.builder.map;

import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.builder.AutoCodeBuilderInf;
import com.kk.autocode.encode.code.AutoCodeInf;
import com.kk.autocode.encode.code.map.*;

import java.util.LinkedList;
import java.util.List;

/**
 * 自动生成代码建造器 源文件名：AutoCodeBuilder.java 文件版本：1.0.0 创建作者：liujun 创建日期：2016年10月8日 修改作者：liujun
 * 修改日期：2016年10月8日 文件描述：TODO 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
 */
public class AutoCodeMapBuilder implements AutoCodeBuilderInf {

  /** 添加集合接入 @字段说明 autoList */
  private List<AutoCodeInf> autoList = new LinkedList<>();

  /**
   * 添加action的代码 方法描述
   *
   * @return @创建日期 2016年10月8日
   */
  public AutoCodeMapBuilder addAction() {
    this.autoList.add(new JavaCodeActionCreate());
    return this;
  }

  /**
   * 添加service的代码 方法描述
   *
   * @return @创建日期 2016年10月8日
   */
  public AutoCodeMapBuilder addService() {
    this.autoList.add(new JavaCodeServiceInfCreate());
    this.autoList.add(new JavaCodeServiceImplCreate());
    return this;
  }

  /**
   * 添加dao的代码 方法描述
   *
   * @return @创建日期 2016年10月8日
   */
  public AutoCodeMapBuilder adddao() {
    this.autoList.add(new JavaCodeDaoInfCreate());
    this.autoList.add(new JavaCodeDaoImplCreate());
    return this;
  }

  /**
   * 添加dao的测试代码 方法描述
   *
   * @return @创建日期 2016年10月8日
   */
  public AutoCodeMapBuilder addDaoTest() {
    // this.autoList.add(new JavaCodeJunitDaoCreate());
    return this;
  }

  /**
   * 添加dao的测试代码 方法描述
   *
   * @return @创建日期 2016年10月8日
   */
  public AutoCodeMapBuilder addServiceTest() {
    //  this.autoList.add(new JavaCodeJunitServiceCreate());
    return this;
  }

  /**
   * 添加mapper的测试代码 方法描述
   *
   * @return @创建日期 2016年10月8日
   */
  public AutoCodeMapBuilder addMapper() {
    // this.autoList.add(new JavaCodeMyBatisMapperCreate());
    return this;
  }

  /**
   * 进行代码的生成操作 方法描述
   *
   * @param param @创建日期 2016年10月8日
   */
  public void createCode(CreateParamBean param) {
    for (AutoCodeInf autoCodeInf : autoList) {
      // 进行代码的自动化生成
      autoCodeInf.autoCode(param);
    }
  }
}

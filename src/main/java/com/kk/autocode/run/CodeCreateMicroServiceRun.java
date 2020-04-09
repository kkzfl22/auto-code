package com.kk.autocode.run;

import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.builder.bean.AutoCodeBeanBuilder;
import com.kk.autocode.encode.constant.CreateCommKey;

import java.util.ArrayList;
import java.util.List;

/**
 * 微服务相当的类的代码生成
 *
 * @since 2018年4月15日 下午9:39:49
 * @version 0.0.1
 * @author liujun
 */
public class CodeCreateMicroServiceRun {

  public static void main(String[] args) throws Exception {

    // javapackage路径
    String prefix = "com.paraview.security.pap.microservice.domain.resource.repository.";

    // mybatis命名空间
    String mybatisNameSpace = "com.paraview.security.pap.microservice.domain.resource.repository.";

    // 表空间
    String tableSpace = "autocode";
    // String tableSpace = "compress";

    // 文件路径
    String filePath = "D:/java/encode/";

    CreateParamBean param = new CreateParamBean(filePath, prefix, mybatisNameSpace, tableSpace);

    AutoCodeBeanBuilder builer = new AutoCodeBeanBuilder();

    // 使用javaBean代码的生成操作
    // 生成bean
    builer.addMicorServiceRepositoryPo();

    // 生成数据库mapper文件信息
    builer.addMicroServiceRepositoryMapper();

    // 生成Dao的接口代码,生成DAO实现的代码
    builer.addMicroServiceRepositoryDAOInf();

    // 生成junitDAO测试代码
    builer.addMicroServiceRepositoryJunitDAOInf();

    // 设置查询信息
    builer.setQueryData(param);
    // 添加单元测试的父类
    addJunitParentImport(param);
    // 生成代码
    builer.createCode(param);

    System.out.println("生成结束");
  }

  private static void addJunitParentImport(CreateParamBean param) {
    // 添加音元测试你父类信息
    List<String> dataList = new ArrayList<>();
    dataList.add("com.paraview.security.pap.microservice.TestParent");
    param.getContext().getDataMap().put(CreateCommKey.JUNIT_IMPORT_KEY.getKey(), dataList);
  }
}

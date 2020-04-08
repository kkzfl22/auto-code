package com.kk.autocode.run;

import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.builder.bean.AutoCodeBeanBuilder;

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
    String prefix = "com.paraview.security.pap.microservice.domain.resource.";

    // mybatis命名空间
    String mybatisNameSpace = "com.paraview.security.pap.microservice.domain.resource.";

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

    // 1,生成数据库mapper文件信息
    builer.addMapper();

    // 2,生成Dao的接口代码,生成DAO实现的代码
    builer.adddao();

    // 6，生成junitDAO测试代码
    builer.addDaoTest();

    // 7,生成junit的Service测试代码
    builer.addServiceTest();

    // 设置查询信息
    builer.setQueryData(param);

    // 生成代码
    builer.createCode(param);

    System.out.println("生成结束");
  }
}

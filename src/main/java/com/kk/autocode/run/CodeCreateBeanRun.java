package com.kk.autocode.run;

import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.builder.bean.AutoCodeBeanBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 进行代码的生成的类的入口
 *
 * @since 2018年4月15日 下午9:39:49
 * @version 0.0.1
 * @author liujun
 */
public class CodeCreateBeanRun {

  public static void main(String[] args) throws Exception {

    // javapackage路径
    String prefix = "com.para.monitor.cluster.data.center.";

    // mybatis命名空间
    String mybatisNameSpace = "com.para.monitor.cluster.data.center.";

    // 表空间
    String tableSpace = "autocode";
    // String tableSpace = "compress";

    // 文件路径
    String filePath = "D:/java/encode/javacode/";

    CreateParamBean param = new CreateParamBean(filePath, prefix, mybatisNameSpace, tableSpace);

    AutoCodeBeanBuilder builer = new AutoCodeBeanBuilder();

    // 使用javaBean代码的生成操作
    // 生成bean
    builer.addBean();

    // 1,生成数据库mapper文件信息
    builer.addMapper();

    // 2,生成Dao的接口代码,生成DAO实现的代码
    builer.adddao();

    // 4，生成Service的代码,生成Service实现的代码
    builer.addService();

    // 6，生成junitDAO测试代码
    builer.addDaoTest();

    // 7,生成junit的Service测试代码
    builer.addServiceTest();

    // 8，生成action的代码p
    // builer.addAction();
    builer.addFormAction();

    // 9，生成json返回的数据，在一般在app应用中返回
    builer.addJsonAction();
    builer.addFormAction();
    builer.addAction();

    // 10,生成json的javabean信息
    builer.addJsonBean();

    // 添加swagger
    builer.addswagger();

    // 设置查询信息
    builer.setQueryData(param);

    // 生成代码
    builer.createCode(param);

    System.out.println("生成结束");
  }
}

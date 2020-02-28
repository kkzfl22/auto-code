package com.kk.autocode.run;

import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.builder.map.AutoCodeMapBuilder;

/**
 * 进行代码的生成的类的入口
 *
 * @version 0.0.1
 * @author liujun
 * @since 2019/05/20
 */
public class CodeCreateMapRun {

  public static void main(String[] args) throws Exception {

    // javapackage路径
    String prefix = "com.a10.resource.phone.";

    // mybatis命名空间
    String mybatisNameSpace = "com.a10.resource.phone.";

    // 表空间
    String tableSpace = "a10";

    // 文件路径
    String filePath = "D:/java/encode/a10/";

    CreateParamBean param = new CreateParamBean(filePath, prefix, mybatisNameSpace, tableSpace);

    AutoCodeMapBuilder builer = new AutoCodeMapBuilder();

    // 进行代码的生成操作
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

    // 8，生成action的代码
    builer.addAction();

    // 生成代码
    builer.createCode(param);
  }
}

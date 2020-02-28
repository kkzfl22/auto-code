package com.kk.autocode.encode.builder.bean;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.kk.autocode.encode.code.bean.*;
import com.kk.element.database.mysql.pojo.TableInfoDTO;
import org.aspectj.util.FileUtil;

import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.bean.EncodeContext;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.autocode.encode.builder.AutoCodeBuilderInf;
import com.kk.autocode.encode.code.AutoCodeInf;

/**
 * 自动生成代码建造器
 *
 * @version 0.0.1
 * @author liujun
 * @date 2019/08/27
 */
public class AutoCodeBeanBuilder implements AutoCodeBuilderInf {

  /** 添加集合接入 @字段说明 autoList */
  private List<AutoCodeInf> autoList = new LinkedList<>();

  /** 添加Bean的代码 方法描述 */
  public AutoCodeBeanBuilder addBean() {
    this.autoList.add(new JavaCodeBeanCreate());
    return this;
  }

  /** 添加Bean的代码 方法描述 */
  public AutoCodeBeanBuilder addJsonBean() {
    this.autoList.add(new JavaCodeBeanCreateJson());
    return this;
  }

  /** 添加action的代码 方法描述 */
  public AutoCodeBeanBuilder addAction() {
    this.autoList.add(new JavaCodeBeanActionCreate());
    return this;
  }

  /** 添加action的代码 方法描述 */
  public AutoCodeBeanBuilder addFormAction() {
    this.autoList.add(new JavaCodeBeanActionFormCreate());
    return this;
  }

  /** 添加action的代码 方法描述 */
  public AutoCodeBeanBuilder addswagger() {
    this.autoList.add(new JavaCodeBeanSwaggerCreate());
    return this;
  }

  /** 添加action的json的代码 方法描述 */
  public AutoCodeBeanBuilder addJsonAction() {
    this.autoList.add(new JavaCodeBeanActionJsonCreate());
    return this;
  }

  /** 添加service的代码 方法描述 */
  public AutoCodeBeanBuilder addService() {
    this.autoList.add(new JavaCodeBeanServiceInfCreate());
    this.autoList.add(new JavaCodeBeanServiceImplCreate());
    return this;
  }

  /** 添加dao的代码 方法描述 */
  public AutoCodeBeanBuilder adddao() {
    this.autoList.add(new JavaCodeBeanDaoInfCreate());
    this.autoList.add(new JavaCodeBeanDaoImplCreate());
    return this;
  }

  /** 添加dao的测试代码 方法描述 */
  public AutoCodeBeanBuilder addDaoTest() {
    this.autoList.add(new JavaCodeBeanJunitDaoCreate());
    return this;
  }

  /** 添加dao的测试代码 方法描述 */
  public AutoCodeBeanBuilder addServiceTest() {
    this.autoList.add(new JavaCodeBeanJunitServiceCreate());
    return this;
  }

  /** 添加mapper的测试代码 方法描述 */
  public AutoCodeBeanBuilder addMapper() {
    this.autoList.add(new JavaCodeBeanMyBatisMapperCreate());
    return this;
  }

  /**
   * 设置查询结果信息
   *
   * @param param
   * @throws Exception
   */
  public void setQueryData(CreateParamBean param) throws Exception {
    JavaCodeBeanCreate serviceImplInstance = new JavaCodeBeanCreate();

    Map<String, List<TableColumnDTO>> map =
        serviceImplInstance.getTableColumnInfoByBean(param.getTableSpaceName());

    Map<String, TableInfoDTO> tableMap =
        serviceImplInstance.getTableInfoByBean(param.getTableSpaceName());

    EncodeContext context = new EncodeContext();

    context.setColumnMap(map);
    context.setTableMap(tableMap);

    param.setContext(context);
  }

  /** 执行清理 */
  private void clear(CreateParamBean param) {
    File filePath = new File(param.getFileBasePath());

    if (filePath.exists()) {
      // 首先删除文件夹，然后再创建
      FileUtil.deleteContents(filePath);
      filePath.mkdirs();
    }
  }

  /**
   * 进行代码的生成操作 方法描述
   *
   * @param param @创建日期 2016年10月8日
   */
  public void createCode(CreateParamBean param) {
    // 首先执行清理，再开始生成
    clear(param);

    for (AutoCodeInf autoCodeInf : autoList) {
      // 进行代码的自动化生成
      autoCodeInf.autoCode(param);
    }
  }
}

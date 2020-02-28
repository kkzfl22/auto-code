package com.kk.element.database.mysql.DAO;

import com.kk.element.database.mysql.pojo.TableInfoDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 测试mysql表信息的获取
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/13
 */
public class TestMysqlTableInfoDaoImpl {

  /** 获取表的相关描述信息 */
  @Test
  public void selectTableMsg() {
    List<TableInfoDTO> list = MySqlTableInfoDaoImpl.INSTANCE.selectTableInfo("autocode");
    Assert.assertNotNull(list);
  }
}

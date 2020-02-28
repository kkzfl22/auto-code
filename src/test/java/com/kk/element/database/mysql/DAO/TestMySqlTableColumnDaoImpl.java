package com.kk.element.database.mysql.DAO;

import com.kk.element.database.mysql.pojo.TableColumnDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 进行mysql的schema消息的查询
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/13
 */
public class TestMySqlTableColumnDaoImpl {

  /** 查询当前库下面的所有表信息 */
  @Test
  public void selectAllTable() {
    List<TableColumnDTO> list = MySqlTableColumnDaoImpl.INSTANCE.selectTableColumn("autocode");
    Assert.assertNotNull(list);
  }
}

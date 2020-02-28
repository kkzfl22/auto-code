package com.kk.element.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 进行数据转换的接口
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/02/14
 */
@FunctionalInterface
public interface DataResultParseInf {

  <T> T parse(ResultSet rs) throws SQLException;
}

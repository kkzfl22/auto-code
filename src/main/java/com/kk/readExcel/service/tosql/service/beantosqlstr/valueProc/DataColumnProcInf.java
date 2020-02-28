package com.kk.readExcel.service.tosql.service.beantosqlstr.valueProc;

import com.kk.readExcel.service.tosql.bean.ColumnMsgInfo;

/**
 * 进行数据处理
 * 
 * @since 2017年3月19日 下午7:16:24
 * @version 0.0.1
 * @author liujun
 */
public interface DataColumnProcInf {

	/**
	 * 列数据处理
	 * @param columnmsg 列信息
	 * @param line 行数据
	 * @return 响应信息
	 */
	public String columnProc(ColumnMsgInfo columnmsg, String line);
}

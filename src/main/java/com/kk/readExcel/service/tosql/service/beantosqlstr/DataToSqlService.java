package com.kk.readExcel.service.tosql.service.beantosqlstr;

import java.util.List;

import com.kk.readExcel.service.tosql.bean.TableInfoBean;
import com.kk.readExcel.service.tosql.service.beantosqlstr.valueProc.DataColumnProcInf;

/**
 * 将数据转换为sql信息
 * 
 * @since 2017年3月19日 下午3:56:14
 * @version 0.0.1
 * @author liujun
 */
public interface DataToSqlService {

	/**
	 * 将数扭转换为sql信息
	 * 
	 * @param toBean
	 * @return
	 */
	public List<List<String>> beanToSqlStr(TableInfoBean toBean, int maxLength);
	
	
	/**
	 * 设置实例对象
	 * @param install
	 */
	public void setDataColumnProc(DataColumnProcInf install);

}

package com.kk.readExcel.service.tosql.service.excel;

import com.kk.readExcel.common.ExcelReadProcess;
import com.kk.readExcel.common.inf.ExcelDataParseInputInf;

/**
 * 进行通用的数据转为java对象信息
 * @since 2017年3月19日 下午3:20:28
 * @version 0.0.1
 * @author liujun
 */
public class DataParseSqlExcelImportExcelProcess extends ExcelReadProcess {

	/**
	 * 
	 * 
	 * @字段说明 phoneExcelReport
	 */
	private ExcelDataParseInputInf imprtReport = new DataParseSqlExcelImport();

	@Override
	protected ExcelDataParseInputInf getParseBean() {
		return imprtReport;
	}

}

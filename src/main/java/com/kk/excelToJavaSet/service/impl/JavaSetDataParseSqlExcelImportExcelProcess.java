package com.kk.excelToJavaSet.service.impl;

import com.kk.excelToJavaSet.service.inf.JavaSetExcelDataParseInputInf;

/**
 * 进行通用的数据转为java对象信息
 * @since 2017年3月19日 下午3:20:28
 * @version 0.0.1
 * @author liujun
 */
public class JavaSetDataParseSqlExcelImportExcelProcess extends JavaSetExcelReadProcess {

	/**
	 * 
	 * 
	 * @字段说明 phoneExcelReport
	 */
	private JavaSetExcelDataParseInputInf imprtReport = new JavaSetDataParseSqlExcelImport();

	@Override
	protected JavaSetExcelDataParseInputInf getParseBean() {
		return imprtReport;
	}

}

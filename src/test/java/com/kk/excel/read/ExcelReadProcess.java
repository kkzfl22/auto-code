package com.kk.excel.read;

import com.kk.readExcel.case2.AbsExcelCommReadProcess;
import com.kk.readExcel.case2.inf.ExcelDataReadInputInf;

public class ExcelReadProcess extends AbsExcelCommReadProcess {

	/**
	 * 转换的实例对象
	 */
	private static final ExcelDataReadInputInf processInStance = null;// new CardProcess();

	@Override
	protected ExcelDataReadInputInf getParseBean() {
		return processInStance;
	}

}

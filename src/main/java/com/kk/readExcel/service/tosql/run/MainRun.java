package com.kk.readExcel.service.tosql.run;

public class MainRun {

	public static void main(String[] args) {
		 ExcelToSqlBuilder excelRun = new ExcelToSqlBuilder();
		
		 excelRun.readelExceltoSql("1市区怡创.xls", 1000000, "1基站资产",0);
		// excelRun.readelExceltoSql("2市区中邮建.xls", 1000000, "2基站资产");
		// excelRun.readelExceltoSql("3市区嘉环.xls", 1000000, "3基站资产");
		// excelRun.readelExceltoSql("5常熟.xls", 1000000, "5基站资产");
		// excelRun.readelExceltoSql("6昆山.xls", 1000000, "6基站资产");
		// excelRun.readelExceltoSql("8张家港资产.xls", 1000000, "8基站资产");
		// excelRun.readelExceltoSql("9吴江.xls", 1000000, "9基站资产");
		
		
		//生成地点码与产权
		CellTowerExcelToSqlBuilder excelAddrCell = new CellTowerExcelToSqlBuilder();

		excelAddrCell.readelExceltoSql("地点码对应的产权信息.xls", 1000000, "地点码对应产权");
		
		
		
	}

}

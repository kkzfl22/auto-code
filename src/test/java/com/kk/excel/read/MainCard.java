package com.kk.excel.read;

import com.kk.readExcel.case2.AbsExcelCommReadProcess;
import com.kk.readExcel.case2.bean.ExcelDataToJavaBean;
import com.kk.readExcel.case2.console.JavaExcelProcException;

import java.util.List;

public class MainCard {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		AbsExcelCommReadProcess read = new ExcelReadProcess();

		try {
			List<ExcelDataToJavaBean> list = read
					.readExcelDataList("D:/java/workspace/omwork/auto-code/src/test/resources/excel/cardRead1.xls", 0);
		
			
			List<CardBean> listCar = (List)list; 
			
			System.out.println(listCar);
		} catch (JavaExcelProcException e) {
			e.printStackTrace();
		}
	}

}

package com.kk.excelToJavaSet.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Row;

import com.kk.excelToJavaSet.bean.JavaSetColumnMsgInfo;
import com.kk.excelToJavaSet.bean.JavaSetDataParseToSQLBean;
import com.kk.excelToJavaSet.service.inf.JavaSetExcelDataParseInputInf;
import com.kk.readExcel.common.ExcelCellStrGet;

/**
 * 数据转换SQL的实现
 * 
 * @since 2017年3月19日 下午3:19:48
 * @version 0.0.1
 * @author liujun
 */
public class JavaSetDataParseSqlExcelImport implements JavaSetExcelDataParseInputInf {

	@Override
	public JavaSetDataParseToSQLBean parseBean(Row excelRow) {

		JavaSetDataParseToSQLBean bean = new JavaSetDataParseToSQLBean();

		HSSFRow row = (HSSFRow) excelRow;

		int rowNum = row.getLastCellNum();

		HSSFCell cell = null;
		for (int i = 0; i < rowNum; i++) {
			cell = (HSSFCell) excelRow.getCell(i);
			bean.put(i, ExcelCellStrGet.getCellValue(cell));
		}

		return bean;
	}

	@Override
	public Map<Integer, JavaSetColumnMsgInfo> getColumnMsg(Row excelRow) {

		Map<Integer, JavaSetColumnMsgInfo> column = new HashMap<>();

		HSSFRow row = (HSSFRow) excelRow;

		int rowNum = row.getLastCellNum();

		HSSFCell cell = null;

		JavaSetColumnMsgInfo columnmsg = null;

		for (int i = 0; i < rowNum; i++) {

			columnmsg = new JavaSetColumnMsgInfo();

			cell = (HSSFCell) excelRow.getCell(i);

			String value = ExcelCellStrGet.getCellValue(cell);

			if (null != value) {

				columnmsg.setColumnMsg(value);

				columnmsg.setIndex(i);

				column.put(i, columnmsg);
			}
		}

		return column;
	}

	@Override
	public String getTableName(Row excelRow) {
		HSSFRow row = (HSSFRow) excelRow;
		return ExcelCellStrGet.getCellValue(row.getCell(1));
	}

	@Override
	public Map<Integer, JavaSetColumnMsgInfo> setColumnMsgType(Row excelRow,
			Map<Integer, JavaSetColumnMsgInfo> columnMap) {

		HSSFRow row = (HSSFRow) excelRow;

		int rowNum = row.getLastCellNum();

		HSSFCell cell = null;

		JavaSetColumnMsgInfo columnmsg = null;

		for (int i = 0; i < rowNum; i++) {

			columnmsg = columnMap.get(i);

			cell = (HSSFCell) excelRow.getCell(i);

			String value = ExcelCellStrGet.getCellValue(cell);

			if (null != value) {
				columnmsg.setType(cell.getCellType());
				columnMap.put(i, columnmsg);
			}
		}

		return columnMap;

	}

	@Override
	public Map<Integer, JavaSetColumnMsgInfo> setColumnMsgName(Row excelRow,
			Map<Integer, JavaSetColumnMsgInfo> columnMap) {
		HSSFRow row = (HSSFRow) excelRow;

		int rowNum = row.getLastCellNum();

		HSSFCell cell = null;

		JavaSetColumnMsgInfo columnmsg = null;

		for (int i = 0; i < rowNum; i++) {

			columnmsg = columnMap.get(i);

			cell = (HSSFCell) excelRow.getCell(i);

			String value = ExcelCellStrGet.getCellValue(cell);

			if (null != value) {
				columnmsg.setColumnName(value);
				columnMap.put(i, columnmsg);
			}
		}

		return columnMap;
	}

}

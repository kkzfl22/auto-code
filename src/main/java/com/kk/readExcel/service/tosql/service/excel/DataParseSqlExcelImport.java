package com.kk.readExcel.service.tosql.service.excel;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Row;

import com.kk.readExcel.common.ExcelCellStrGet;
import com.kk.readExcel.common.inf.ExcelDataParseInputInf;
import com.kk.readExcel.service.tosql.bean.ColumnMsgInfo;
import com.kk.readExcel.service.tosql.bean.DataParseToSQLBean;

/**
 * 数据转换SQL的实现
 * 
 * @since 2017年3月19日 下午3:19:48
 * @version 0.0.1
 * @author liujun
 */
public class DataParseSqlExcelImport implements ExcelDataParseInputInf {

	@Override
	public DataParseToSQLBean parseBean(Row excelRow) {

		DataParseToSQLBean bean = new DataParseToSQLBean();

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
	public Map<Integer, ColumnMsgInfo> getColumnMsg(Row excelRow) {

		Map<Integer, ColumnMsgInfo> column = new HashMap<>();

		HSSFRow row = (HSSFRow) excelRow;

		int rowNum = row.getLastCellNum();

		HSSFCell cell = null;

		ColumnMsgInfo columnmsg = null;

		for (int i = 0; i < rowNum; i++) {

			columnmsg = new ColumnMsgInfo();

			cell = (HSSFCell) excelRow.getCell(i);

			columnmsg.setType(cell.getCellType());

			String value = ExcelCellStrGet.getCellValue(cell);

			if (null != value) {

				columnmsg.setColumnName(value.toUpperCase());

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

}

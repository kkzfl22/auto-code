package com.kk.readExcel.common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.CellType;

/**
 * 进行单元格的数据提取
 * 
 * @author liujun
 * @date 2016年4月8日
 * @verion 0.0.1
 */
public class ExcelCellStrGet {
	/**
	 * 提取excel单元格中的值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(HSSFCell cell) {

		if (null != cell) {
			// 如果当前为字符串
			if (CellType.STRING == cell.getCellTypeEnum()) {
				return cell.getStringCellValue();
			}
			// 如果当前为数字
			else if (CellType.NUMERIC == cell.getCellTypeEnum()) {

				return String.valueOf(cell.getNumericCellValue());
			}
		}

		return null;
	}

	/**
	 * 提取excel单元格中的值
	 * 
	 * @param cell
	 * @return
	 */
	public static int getCellNumerValue(HSSFCell cell) {

		if (null != cell) {
			// 如果当前为数字
			if (CellType.NUMERIC == cell.getCellTypeEnum()) {
				return (int) cell.getNumericCellValue();
			}
		}

		return -1;
	}

	/**
	 * 提取excel单元格中的double值
	 * 
	 * @param cell
	 * @return
	 */
	public static double getCellDoubleValue(HSSFCell cell) {

		if (null != cell) {
			// 如果当前为数字
			if (CellType.NUMERIC == cell.getCellTypeEnum()) {
				return cell.getNumericCellValue();
			}
		}

		return -1;
	}
}

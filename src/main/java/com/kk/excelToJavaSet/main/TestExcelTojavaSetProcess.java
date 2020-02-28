package com.kk.excelToJavaSet.main;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.Test;

import com.kk.excelToJavaSet.bean.JavaSetColumnMsgInfo;
import com.kk.excelToJavaSet.bean.JavaSetTableInfoBean;
import com.kk.excelToJavaSet.service.impl.JavaSetDataParseSqlExcelImportExcelProcess;
import com.kk.excelToJavaSet.service.impl.JavaSetExcelReadProcess;
import com.kk.readExcel.common.ExcelProcException;

public class TestExcelTojavaSetProcess {

	/**
	 * 换行符
	 */
	private static final String LINE = "\r\n";

	/**
	 * excel信息
	 */
	private JavaSetExcelReadProcess excel = new JavaSetDataParseSqlExcelImportExcelProcess();

	@Test
	public void testExcelAssetRead() {
		// String path =
		// this.getClass().getClassLoader().getResource("temp/CELL_TOWER_ASSET.xls").getPath();

		JavaSetTableInfoBean dataBean = null;

		try {
			dataBean = excel.readExcelDataList("CELL_TOWER_ANTENNA.xls", 0);
		} catch (ExcelProcException e) {
			e.printStackTrace();
		}

		Map<Integer, JavaSetColumnMsgInfo> colMap = dataBean.getColumn();

		for (int i = 0; i < colMap.size(); i++) {
			JavaSetColumnMsgInfo msg = colMap.get(i);


			String projavaName = toJavaName(msg.getColumnName());

			StringBuilder out = new StringBuilder();

			out.append("//" + msg.getColumnMsg()).append(LINE);

			if (msg.getType() == 1) {
				// out.append("paramBean.set" + name + "(data.get(" + i +
				// "));");
				out.append(" HSSFCell " + projavaName + " = hrow.createCell(").append(i).append(");").append(LINE);
				out.append(projavaName).append(".setCellType(CellType.STRING);").append(LINE);
				out.append(projavaName).append(".setCellValue(beaninfo.get").append(toSetProJavaName(msg.getColumnName()))
						.append("());").append(LINE);

			} else {
				out.append(" HSSFCell " + projavaName + " = hrow.createCell(").append(i).append(");").append(LINE);
				out.append(projavaName).append(".setCellType(CellType.NUMERIC);").append(LINE);
				out.append(projavaName).append(".setCellValue(beaninfo.get").append(toSetProJavaName(msg.getColumnName()))
						.append("());").append(LINE);
			}

			System.out.println(out.toString());
			out = null;
		}

		for (int i = 0; i < colMap.size(); i++) {
			JavaSetColumnMsgInfo msg = colMap.get(i);

			String projavaName = toJavaName(msg.getColumnName());

			StringBuilder out = new StringBuilder();

			out.append("//" + msg.getColumnMsg()).append(LINE);

			// out.append("paramBean.set" + name + "(data.get(" + i +
			// "));");
			out.append(" HSSFCell " + projavaName + " = hrow.createCell(").append(i).append(");").append(LINE);
			out.append(projavaName).append(".setCellType(CellType.STRING);").append(LINE);
			out.append(projavaName).append(".setCellValue(\"").append(msg.getColumnMsg()).append("\");")
					.append(LINE);

			System.out.println(out.toString());
			out = null;
		}

		// System.out.println(dataBean);
	}

	/**
	 * 转换为java属性get与set命名规则 方法描述
	 * 
	 * @param str
	 * @return
	 * @创建日期 2016年9月28日
	 */
	protected String toSetProJavaName(String str) {
		String[] strs = str.split("_");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			sb.append(strs[i].substring(0, 1).toUpperCase());
			sb.append(strs[i].substring(1).toLowerCase());
		}
		return sb.toString();
	}

	/**
	 * 转换为java普通命令规则
	 * 
	 * @param str
	 * @return
	 * @创建日期 2016年9月28日
	 */
	protected String toJavaName(String str) {
		String[] strs = str.split("_");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			sb.append(strs[i].substring(0, 1).toLowerCase());
			sb.append(strs[i].substring(1).toLowerCase());
		}
		return sb.toString();
	}

}

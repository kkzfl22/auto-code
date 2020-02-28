package com.kk.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;

public class JavaMargeExcel {

	public static void main(String[] args) {

		String outPath = "D:/java/test/excel/marge.xls";

		POIFSFileSystem fs = null;

		// 创建一个工作薄
		HSSFWorkbook wb = null;
		// 创建sheet页
		HSSFSheet sheet = null;

		FileOutputStream output = null;
		try {

			// 得到Excel工作簿对象
			wb = new HSSFWorkbook();
			// 创建一个新的sheet页
			sheet = wb.createSheet();

			createTitle(sheet, wb);

			createColumn(sheet, wb);

			createRowDate(sheet, wb);

			output = new FileOutputStream(outPath);

			wb.write(output);

			output.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(fs);
		}

	}

	/**
	 * 创建标题
	 * 
	 * @param sheet
	 * @param workbook
	 */
	public static void createTitle(HSSFSheet sheet, HSSFWorkbook wb) {
		HSSFRow columnRow = sheet.createRow(0);

		HSSFCell name = columnRow.createCell(0);
		name.setCellValue("2018年3月业务员提成汇总                  单位：元");

		HSSFCellStyle style = wb.createCellStyle();
		// 设置居中
		style.setAlignment(HorizontalAlignment.CENTER);
		name.setCellStyle(style);

		// 设置合并器
		CellRangeAddress callRangeAddress = new CellRangeAddress(0, 0, 0, 11);// 起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);
	}

	/**
	 * 创建标题
	 * 
	 * @param sheet
	 * @param workbook
	 */
	public static void createColumn(HSSFSheet sheet, HSSFWorkbook workbook) {
		HSSFRow columnRow = sheet.createRow(1);

		HSSFCell seq = columnRow.createCell(0);
		seq.setCellValue("序号");

		HSSFCell people = columnRow.createCell(1);
		people.setCellValue("业务员");

		HSSFCell memory = columnRow.createCell(2);
		memory.setCellValue("企业名称");

		HSSFCell memory1 = columnRow.createCell(3);
		memory1.setCellValue("提成项目");

		HSSFCell memory21 = columnRow.createCell(4);
		memory21.setCellValue("收款");

		HSSFCell memory2 = columnRow.createCell(5);
		memory2.setCellValue("付款");

		HSSFCell memory3 = columnRow.createCell(6);
		memory3.setCellValue("提成金额");

		HSSFCell memory4 = columnRow.createCell(7);
		memory4.setCellValue("提成分摊（可提成）");

		HSSFCell memory41 = columnRow.createCell(8);
		memory41.setCellValue("");

		HSSFCell memory5 = columnRow.createCell(9);
		memory5.setCellValue("办证办照参与人员提成");

		HSSFCell memory51 = columnRow.createCell(10);
		memory51.setCellValue("");

		HSSFRow columnRow2 = sheet.createRow(2);

		for (int i = 0; i <= 6; i++) {
			HSSFCell memorytmp = columnRow2.createCell(i);
			memorytmp.setCellValue("");
		}

		HSSFCell memory71 = columnRow2.createCell(7);
		memory71.setCellValue("提成比例");

		HSSFCell memory72 = columnRow2.createCell(8);
		memory72.setCellValue("金额");

		HSSFCell name91 = columnRow2.createCell(9);
		name91.setCellValue("姓名");

		HSSFCell memroy92 = columnRow2.createCell(10);
		memroy92.setCellValue("金额");

		HSSFCellStyle style = workbook.createCellStyle();
		// 设置居中
		style.setAlignment(HorizontalAlignment.CENTER);
		seq.setCellStyle(style);
		
		//垂直居中
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setVerticalAlignment(VerticalAlignment.CENTER);
		seq.setCellStyle(style2);

		// 序列号合并器
		CellRangeAddress callRangeSeq = new CellRangeAddress(1, 2, 0, 0);// 起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeSeq);
		// 业务员合并器
		CellRangeAddress callRangePeople = new CellRangeAddress(1, 2, 1, 1);// 起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangePeople);
		// 企业名称合并器
		CellRangeAddress callRangeName = new CellRangeAddress(1, 2, 2, 2);// 起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeName);
		// 提成项目合并器
		CellRangeAddress callRangeMemory1 = new CellRangeAddress(1, 2, 3, 3);// 起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeMemory1);
		// 收款合并器
		CellRangeAddress callRangeMemory2 = new CellRangeAddress(1, 2, 4, 4);// 起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeMemory2);
		// 付款合并器
		CellRangeAddress callRangeMemory3 = new CellRangeAddress(1, 2, 5, 5);// 起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeMemory3);
		// 提成金额合并器
		CellRangeAddress callRangeMemory4 = new CellRangeAddress(1, 2, 6, 6);// 起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeMemory4);
		// 提成分摊（可提成）合并器
		CellRangeAddress callRangeMemory5 = new CellRangeAddress(1, 1, 7, 8);// 起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeMemory5);
		// 办证办照参与人员提成 合并器
		CellRangeAddress callRangeMemory10 = new CellRangeAddress(1, 1, 9, 10);// 起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeMemory10);

	}

	/**
	 * 创建标题
	 * 
	 * @param sheet
	 * @param workbook
	 */
	public static void createRowDate(HSSFSheet sheet, HSSFWorkbook workbook) {

		for (int i = 3; i < 20; i += 2) {

			HSSFRow columnRow = sheet.createRow(i);

			HSSFCell seq = columnRow.createCell(0);

			if (i == 3) {
				seq.setCellValue("序号");
			}

			HSSFCell people = columnRow.createCell(1);

			if (i == 3) {
				people.setCellValue("业务员");
			}

			HSSFCell memory = columnRow.createCell(2);
			memory.setCellValue("企业名称" + i);

			HSSFCell memory1 = columnRow.createCell(3);
			memory1.setCellValue("提成项目" + i);

			HSSFCell memory21 = columnRow.createCell(4);
			memory21.setCellValue("收款" + i);

			HSSFCell memory2 = columnRow.createCell(5);
			memory2.setCellValue("付款" + i);

			HSSFCell memory3 = columnRow.createCell(6);
			memory3.setCellValue("提成金额" + i);

			HSSFCell memory4 = columnRow.createCell(7);
			memory4.setCellValue("提成分摊（可提成）" + i);

			HSSFCell memory41 = columnRow.createCell(8);
			memory41.setCellValue("");

			HSSFCell memory5 = columnRow.createCell(9);
			memory5.setCellValue("办证办照参与人员提成" + i);

			HSSFCell memory51 = columnRow.createCell(10);
			memory51.setCellValue("");

			HSSFRow columnRow2 = sheet.createRow(i + 1);

			for (int j = 0; j <= 6; j++) {
				HSSFCell memorytmp = columnRow2.createCell(j);
				memorytmp.setCellValue("");
			}

			HSSFCell memory71 = columnRow2.createCell(7);
			memory71.setCellValue("提成比例" + (i + 1));

			HSSFCell memory72 = columnRow2.createCell(8);
			memory72.setCellValue("金额" + (i + 1));

			HSSFCell name91 = columnRow2.createCell(9);
			name91.setCellValue("姓名" + (i + 1));

			HSSFCell memroy92 = columnRow2.createCell(10);
			memroy92.setCellValue("金额" + (i + 1));

			HSSFCellStyle style = workbook.createCellStyle();
			// 设置居中
			style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
			seq.setCellStyle(style);
			
			//垂直居中
			HSSFCellStyle style2 = workbook.createCellStyle();
			style2.setVerticalAlignment(VerticalAlignment.CENTER);
			seq.setCellStyle(style2);

			// // 序列号合并器
			// CellRangeAddress callRangeSeq = new CellRangeAddress(i, i + 1, 0,
			// 0);// 起始行,结束行,起始列,结束列
			// sheet.addMergedRegion(callRangeSeq);
			// // 业务员合并器
			// CellRangeAddress callRangePeople = new CellRangeAddress(i, i + 1,
			// 1, 1);// 起始行,结束行,起始列,结束列
			// sheet.addMergedRegion(callRangePeople);
			// 企业名称合并器
			CellRangeAddress callRangeName = new CellRangeAddress(i, i + 1, 2, 2);// 起始行,结束行,起始列,结束列
			sheet.addMergedRegion(callRangeName);
			// 提成项目合并器
			CellRangeAddress callRangeMemory1 = new CellRangeAddress(i, i + 1, 3, 3);// 起始行,结束行,起始列,结束列
			sheet.addMergedRegion(callRangeMemory1);
			// 收款合并器
			CellRangeAddress callRangeMemory2 = new CellRangeAddress(i, i + 1, 4, 4);// 起始行,结束行,起始列,结束列
			sheet.addMergedRegion(callRangeMemory2);
			// 付款合并器
			CellRangeAddress callRangeMemory3 = new CellRangeAddress(i, i + 1, 5, 5);// 起始行,结束行,起始列,结束列
			sheet.addMergedRegion(callRangeMemory3);
			// 提成金额合并器
			CellRangeAddress callRangeMemory4 = new CellRangeAddress(i, i + 1, 6, 6);// 起始行,结束行,起始列,结束列
			sheet.addMergedRegion(callRangeMemory4);
			// 提成分摊（可提成）合并器
			CellRangeAddress callRangeMemory5 = new CellRangeAddress(i, i, 7, 8);// 起始行,结束行,起始列,结束列
			sheet.addMergedRegion(callRangeMemory5);
			// 办证办照参与人员提成 合并器
			CellRangeAddress callRangeMemory10 = new CellRangeAddress(i, i, 9, 10);// 起始行,结束行,起始列,结束列
			sheet.addMergedRegion(callRangeMemory10);

		}

		// // 业务员合并器
		CellRangeAddress callRangePeople1 = new CellRangeAddress(3, 20, 0, 0);// 起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangePeople1);
		// 企业名称合并器
		CellRangeAddress callRangeName1 = new CellRangeAddress(3, 20, 1, 1);// 起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeName1);

	}

}

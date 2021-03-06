package com.kk.excelToJavaSet.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

import com.kk.autocode.util.IOutils;
import com.kk.excelToJavaSet.bean.JavaSetTableInfoBean;
import com.kk.excelToJavaSet.service.inf.JavaSetExcelDataParseInputInf;
import com.kk.readExcel.common.ExcelProcException;

/**
 * 进行excel的读取操作
 * 
 * @author liujun
 * @date 2016年4月8日
 * @verion 0.0.1
 */
public abstract class JavaSetExcelReadProcess {
	/**
	 * 通过过此抽换方法调用实现
	 * 
	 * @return
	 */
	protected abstract JavaSetExcelDataParseInputInf getParseBean();

	/**
	 * 基础路径信息
	 */
	protected String basePath;

	/**
	 * 读取Excel数据转换成集合数据
	 * 
	 * @param path
	 *            excel路径
	 * @return 结果
	 */
	@SuppressWarnings("resource")
	public JavaSetTableInfoBean readExcelDataList(String file, int sheetIndex) throws ExcelProcException {

		String path = getBasePath() + file;

		JavaSetTableInfoBean result = new JavaSetTableInfoBean();

		POIFSFileSystem fs = null;
		try {
			fs = new POIFSFileSystem(new FileInputStream(path));

			// 得到Excel工作簿对象
			HSSFWorkbook wookbook = new HSSFWorkbook(fs);
			// 得到Excel工作表对象
			HSSFSheet sheet = wookbook.getSheetAt(sheetIndex);

			// 得到总行数
			Iterator<Row> iterRow = sheet.iterator();

			// 得到转换对象
			JavaSetExcelDataParseInputInf parseBean = getParseBean();

			int index = 0;

			while (iterRow.hasNext()) {
				// 得到Excel工作表的行
				Row row = iterRow.next();

				if (index < 8) {
					// 读取表名信息
					if (index == 0) {
						result.setTableName(parseBean.getTableName(row));
					}
					// 读取列字段信息
					else if (index == 3) {
						result.setColumn(parseBean.getColumnMsg(row));
					}
					// 填充列字段信息
					else if (index == 4) {
						result.setColumn(parseBean.setColumnMsgName(row, result.getColumn()));
					}
					// 填充列字段信息
					else if (index == 5) {
						result.setColumn(parseBean.setColumnMsgType(row, result.getColumn()));
					}
					index += 1;
				}
				// 读取数据信息
				else {
					result.addValue(parseBean.parseBean(row));
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOutils.closeStream(fs);
		}

		return result;
	}

	/**
	 * 获取基础路径
	 * 
	 * @return
	 */
	protected String getBasePath() {
		if (basePath == null) {
			basePath = this.getClass().getResource("/tojavaset").getPath();
			basePath = basePath + "/";
			return basePath;
		} else {
			return basePath;
		}
	}

}

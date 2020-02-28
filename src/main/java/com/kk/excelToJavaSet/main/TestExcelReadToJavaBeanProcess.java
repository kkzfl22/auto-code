package com.kk.excelToJavaSet.main;

import java.util.Map;

import org.junit.Test;

import com.kk.excelToJavaSet.bean.JavaSetColumnMsgInfo;
import com.kk.excelToJavaSet.bean.JavaSetTableInfoBean;
import com.kk.excelToJavaSet.service.impl.JavaSetDataParseSqlExcelImportExcelProcess;
import com.kk.excelToJavaSet.service.impl.JavaSetExcelReadProcess;
import com.kk.readExcel.common.ExcelProcException;

public class TestExcelReadToJavaBeanProcess {

	/**
	 * excel信息
	 */
	private JavaSetExcelReadProcess excel = new JavaSetDataParseSqlExcelImportExcelProcess();

	@Test
	public void testExcelAssetRead() {
		// String path =
		// this.getClass().getClassLoader().getResource("temp/CELL_TOWER_ASSET.xls").getPath();
		String path = this.getClass().getClassLoader().getResource("temp/test12.xls").getPath();

		JavaSetTableInfoBean dataBean = null;

		try {
			dataBean = excel.readExcelDataList(path, 0);
		} catch (ExcelProcException e) {
			e.printStackTrace();
		}

		Map<Integer, JavaSetColumnMsgInfo> colMap = dataBean.getColumn();

		for (int i = 0; i < colMap.size(); i++) {
			JavaSetColumnMsgInfo msg = colMap.get(i);

			String name = toProJavaName(msg.getColumnName());

			String out = "//" + msg.getColumnMsg() + "\r\n";
			if (msg.getType() == 1) {
				out += "paramBean.set" + name + "(data.get(" + i + "));";
			} else {
				out += "paramBean.set" + name + "(parseInt(data.get(" + i + ")));";
			}
			System.out.println(out);
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
	protected String toProJavaName(String str) {
		String[] strs = str.split("_");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			sb.append(strs[i].substring(0, 1).toUpperCase());
			sb.append(strs[i].substring(1));
		}
		return sb.toString();
	}

}

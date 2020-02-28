package com.kk.readExcel.service.tosql.service.writefile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.kk.autocode.util.IOutils;

public class WriteSqlFileImpl implements WriteFileInf {

	/**
	 * 文件后缀名
	 */
	private static final String SUFFIX_NAME = ".sql";

	/**
	 * 输出路径
	 */
	private String basePath = getBasePath();

	/**
	 * 换行符信息
	 */
	private static final String LINE = "\r\n";

	@Override
	public boolean wirteSql(List<List<String>> data, boolean spitFlag, String fileName) {

		String output = null;

		for (int i = 0; i < data.size(); i++) {
			// 如果当前是多文件写入
			if (spitFlag) {
				output = basePath + "/" + fileName + "-" + i + SUFFIX_NAME;
			} else {
				output = basePath + "/" + fileName + "-99999" + SUFFIX_NAME;
			}

			// 进行数据写入
			this.fileWirte(output, data.get(i));

		}

		return true;

	}

	/**
	 * 进行文件数据的写入
	 * 
	 * @param output
	 *            文件名称
	 * @param data
	 *            数据信息
	 * @return true写入成功 false写入失败
	 */
	private void fileWirte(String output, List<String> data) {
		FileWriter write = null;
		BufferedWriter buffWrite = null;

		try {
			write = new FileWriter(output, true);
			buffWrite = new BufferedWriter(write);

			for (String lineData : data) {
				buffWrite.write(lineData + LINE);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOutils.closeStream(buffWrite);
			IOutils.closeStream(write);
		}

	}

	/**
	 * 获取基础路径
	 * 
	 * @return
	 */
	protected String getBasePath() {
		if (basePath == null) {
			basePath = this.getClass().getResource("/tosql/sql").getPath();
			return basePath + "/";
		} else {
			return basePath;
		}
	}

}

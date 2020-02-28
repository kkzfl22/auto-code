package com.kk.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.kk.autocode.util.IOutils;

/**
 * 文件写入公共类
 * 
 * @since 2018年5月12日 下午7:12:11
 * @version 0.0.1
 * @author liujun
 */
public class FileWriteUtils {

	/**
	 * 将文件写入到指定的内容中
	 * 
	 * @param path
	 *            路径
	 * @param code
	 *            内容信息
	 */
	public static void writeFileCode(String path, String code) throws IOException {
		FileWriter fileWrite = null;
		BufferedWriter bufferWrite = null;
		try {
			fileWrite = new FileWriter(path);
			bufferWrite = new BufferedWriter(fileWrite);

			bufferWrite.write(code);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			IOutils.closeStream(bufferWrite);
			IOutils.closeStream(fileWrite);
		}

	}

}

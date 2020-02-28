package com.kk.readExcel.service.tosql.service.loadsrc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.kk.autocode.util.IOutils;

/**
 * 定义数据源加载的基本文件信息
 * 
 * @since 2017年3月19日 下午6:14:41
 * @version 0.0.1
 * @author liujun
 */
public abstract class SrcFileLoaderBase<K, V> implements SrcDataLoaderInf<K, V> {

	/**
	 * 输出路径
	 */
	private String basePath = getBasePath();

	/**
	 * 获取行数据处理
	 * 
	 * @return
	 */
	protected abstract SrcDataLineProcInf<K, V> getlineProc();

	@Override
	public Map<K, V> loaderDataSrcProcess(String name) {

		Map<K, V> result = new HashMap<>();

		FileReader fileRead = null;
		BufferedReader bufferFile = null;

		String path = basePath + name ;

		try {
			fileRead = new FileReader(path);
			bufferFile = new BufferedReader(fileRead);

			String line = null;

			SrcDataLineProcInf<K, V> dataLine = getlineProc();

			Entry<K, V> dataParseBean = null;

			while ((line = bufferFile.readLine()) != null) {
				// 进行数据处理
				dataParseBean = dataLine.parseDataValue(line);
				// 放入到map中
				result.put(dataParseBean.getKey(), dataParseBean.getValue());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOutils.closeStream(bufferFile);
			IOutils.closeStream(fileRead);
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
			basePath = this.getClass().getResource("/tosql").getPath();
			return basePath + "/";
		} else {
			return basePath;
		}
	}
	
	public static void main(String[] args) {
		Map<String,Integer> map = new HashMap<>();
		
		map.put("1", 1);
		
		map.entrySet().iterator();
	}

}

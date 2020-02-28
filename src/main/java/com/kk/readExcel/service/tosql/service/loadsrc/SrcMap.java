package com.kk.readExcel.service.tosql.service.loadsrc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kk.readExcel.service.tosql.service.console.FillEnum;
import com.kk.readExcel.service.tosql.service.loadsrc.impl.AssetTypeSrcLoaderImpl;

/**
 * 临时的map信息
 * 
 * @since 2017年3月19日 下午7:06:35
 * @version 0.0.1
 * @author liujun
 */
public class SrcMap {

	private static final Map<Object, Object> MAP = new ConcurrentHashMap<>();

	public static Object get(Object key) {
		return MAP.get(key);
	}

	public static void get(Object key, Object value) {
		MAP.put(key, value);
	}

	static {
		SrcFileLoaderBase<String, Integer> base = new AssetTypeSrcLoaderImpl();
		Map<String, Integer> entryMap = base.loaderDataSrcProcess(FillEnum.ASSERTTYPE.getKey());

		// 加载填充数据信息
		MAP.put(FillEnum.ASSERTTYPE.getKey(), entryMap);
		
		//产权信息表
		SrcFileLoaderBase<String, Integer> cellTowerType = new AssetTypeSrcLoaderImpl();
		Map<String, Integer> cellTowerTypeMap = cellTowerType.loaderDataSrcProcess(FillEnum.CELLTOWERTYPE.getKey());

		// 加载产权数据信息
		MAP.put(FillEnum.CELLTOWERTYPE.getKey(), cellTowerTypeMap);
	}
}

package com.kk.readExcel.service.tosql.service.loadsrc.impl;

import java.util.AbstractMap;
import java.util.Map.Entry;

import com.kk.readExcel.service.tosql.service.loadsrc.SrcDataLineProcInf;
import com.kk.readExcel.service.tosql.service.loadsrc.SrcFileLoaderBase;

/**
 * 进行cell_tower_type数据加载的处理
 * 
 * @since 2017年3月19日 下午6:42:13
 * @version 0.0.1
 * @author liujun
 */
public class CellTowerTypeSrcLoaderImpl extends SrcFileLoaderBase<String, Integer>
		implements SrcDataLineProcInf<String, Integer> {

	@Override
	protected SrcDataLineProcInf<String, Integer> getlineProc() {
		return this;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Entry<String, Integer> parseDataValue(String line) {

		String[] array = line.split("	");

		return new AbstractMap.SimpleEntry(array[1], Integer.parseInt(array[0]));
	}

	public static void main(String[] args) {
		SrcFileLoaderBase<String, Integer> base = new CellTowerTypeSrcLoaderImpl();
		base.loaderDataSrcProcess("asset_type.src");
	}

}

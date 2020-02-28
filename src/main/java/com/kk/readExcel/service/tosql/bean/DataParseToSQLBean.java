package com.kk.readExcel.service.tosql.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * cell_tower_antenna表对应的实体信息 文件版本：1.0.0 创建作者：liujun 创建日期：2017-03-14
 */
public class DataParseToSQLBean {

	private Map<Integer, String> nameValue = new HashMap<>();

	public Map<Integer, String> getNameValue() {
		return nameValue;
	}

	public void put(Integer key, String value) {
		this.nameValue.put(key, value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataParseToSQLBean [nameValue=");
		builder.append(nameValue);
		builder.append("]");
		return builder.toString();
	}

}
package com.kk.readExcel.service.tosql.service.console;

/**
 * 填充数据的枚举信息
 * 
 * @since 2017年3月19日 下午7:45:18
 * @version 0.0.1
 * @author liujun
 */
public enum FillEnum {

	/**
	 * 用来进行类型的填充
	 */
	ASSERTTYPE("asset_type.src", null),

	/**
	 * 数据加载
	 */
	CELLTOWERTYPE("cell_tower_type.src", null);

	/**
	 * 进行key的配制
	 */
	private String key;

	/**
	 * 进行值配制
	 */
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private FillEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

}

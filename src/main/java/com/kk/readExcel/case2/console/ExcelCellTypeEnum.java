package com.kk.readExcel.case2.console;

/**
 * Excel单元格中的信息
 * @since 2018年4月29日 下午1:06:55
 * @version 0.0.1
 * @author liujun
 */
public enum ExcelCellTypeEnum {
	
	
	/**
	 * 数字类型,使用double
	 */
	CELLTYPE_NUMBER("Double"),
	
	
	/**
	 * 字符串类型
	 */
	CELLTYPE_STRING("String"),
	
	
	/**
	 * 布尔类型的信息
	 */
	CELLTYPE_BOOLEAN("Boolean"),
	
	
	/**
	 * 时间类型信息
	 */
	CELLTYPE_DATE("DateTime");
	
	/**
	 * 类型信息
	 */
	private String type;

	private ExcelCellTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

}

package com.kk.readExcel.case2.bean.comm;

/**
 * 将列信息转换为javaBean所需要的信息
 * 
 * @since 2018年4月28日 下午5:18:17
 * @version 0.0.1
 * @author liujun
 */
public class ColumnToJavaBeanMsg {

	/**
	 * 索引号
	 */
	private int index;

	/**
	 * 实体的名称
	 */
	private String name;

	/**
	 * 数据的类型
	 */
	private String type;

	/**
	 * 列描述信息
	 */
	private String describe;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColumnToJavaBeanMsg [index=");
		builder.append(index);
		builder.append(", name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", describe=");
		builder.append(describe);
		builder.append("]");
		return builder.toString();
	}

}

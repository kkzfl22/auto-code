package com.kk.docprocess.doctoadapterdoc.console;

/**
 * 操作名称的定义
 * 
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月11日 下午2:06:54
 */
public enum ProcEnum {

	INSERT("insert", "添加操作"),

	UPDATE("update", "修改操作"),

	DELETE("delete", "删除操作"),

	QUERY("detail", "查询详细操作"),

	QUERYPAGE("queryPage", "分页查询操作"),
	
	PROC_URL_SUFFIX("Api",""),

	PROC_SUFFIX(".action", "请求的后缀名");

	/**
	 * key信息
	 */
	private String key;

	/**
	 * 描述信息
	 */
	private String msg;

	private ProcEnum(String key, String msg) {
		this.key = key;
		this.msg = msg;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

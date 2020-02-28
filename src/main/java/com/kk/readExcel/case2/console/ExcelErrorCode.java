package com.kk.readExcel.case2.console;

/**
 * 错误码信息
 * 
 * @author liujun
 * @date 2016年4月8日
 * @verion 0.0.1
 */
public enum ExcelErrorCode
{
	
	FILE_NOTFOUNT(1000,"输入文件路径指定有误，找不到对应的文件 "),
	
	FILE_FILE_IS_ERROR(1001,"输入的文件存在错误，请检查文件是否按标准的excel格式!"),
	
	EXCEL_PARSE_DATA(1002, "当前数据转换为excel错误 "),
	
	DATA1_INPUT_ERROR(10000, "输入文件有错误"),

	;

	/**
	 * 错误码信息
	 */
	private int code;

	/**
	 * 错误信息
	 */
	private String msg;

	private ExcelErrorCode(int code, String msg)
	{
		this.code = code;
		this.msg = msg;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

}

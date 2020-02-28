package com.kk.readExcel.common;

/**
 * excel处理的异常
 * 
 * @author liujun
 * @date 2016年4月1日
 * @verion 0.0.1
 */
public class ExcelProcException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcelProcException()
	{
		super();
	}

	public ExcelProcException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ExcelProcException(String message)
	{
		super(message);
	}

	public ExcelProcException(Throwable cause)
	{
		super(cause);
	}

}

package com.kk.docprocess.doctoadapterdoc.bean;

import java.util.List;

import com.kk.docprocess.doctoadapterdoc.console.ProcEnum;

/**
 * 接口信息的bean
 * 
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月11日 下午12:11:16
 */
public class AdapterBean {
	
	/**
	 * 当前操作信息的枚举
	 */
	private ProcEnum proc;
	
	/**
	 * 表名
	 */
	private String name;
	
	/**
	 * 接口地址
	 */
	private String url;

	/**
	 * 接口描述
	 */
	private String msg;

	/**
	 * 请求参数信息
	 */
	private List<ParamBase> request;

	/**
	 * 请求响应信息
	 */
	private AdapterResponse response;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<ParamBase> getRequest() {
		return request;
	}

	public void setRequest(List<ParamBase> request) {
		this.request = request;
	}

	public AdapterResponse getResponse() {
		return response;
	}

	public void setResponse(AdapterResponse response) {
		this.response = response;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public ProcEnum getProc() {
		return proc;
	}

	public void setProc(ProcEnum proc) {
		this.proc = proc;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AdapterBean [url=");
		builder.append(url);
		builder.append(", msg=");
		builder.append(msg);
		builder.append(", request=");
		builder.append(request);
		builder.append(", response=");
		builder.append(response);
		builder.append("]");
		return builder.toString();
	}

}

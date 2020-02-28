package com.kk.docprocess.doctoadapterdoc.bean;

import java.util.List;

/**
 * 响应信息
 * 
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月11日 下午12:14:30
 */
public class AdapterResponse {

	/**
	 * 公共响应
	 */
	private List<ParamBase> commRsp;

	/**
	 * 响应结果集
	 */
	private List<ParamBase> rspDataList;

	public List<ParamBase> getCommRsp() {
		return commRsp;
	}

	public void setCommRsp(List<ParamBase> commRsp) {
		this.commRsp = commRsp;
	}

	public List<ParamBase> getRspDataList() {
		return rspDataList;
	}

	public void setRspDataList(List<ParamBase> rspDataList) {
		this.rspDataList = rspDataList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AdapterResponse [commRsp=");
		builder.append(commRsp);
		builder.append(", rspDataList=");
		builder.append(rspDataList);
		builder.append("]");
		return builder.toString();
	}

}

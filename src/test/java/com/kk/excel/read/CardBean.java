package com.kk.excel.read;

import java.util.Date;

import com.kk.readExcel.case2.bean.ExcelDataToJavaBean;

public class CardBean extends ExcelDataToJavaBean {

	/**
	 * 序号
	 */
	private double sequence;

	/**
	 * 企业名称流水编号
	 */
	private String companySeq;

	/**
	 * 首次收款日期
	 */
	private String memoryTime;

	/**
	 * 应收款
	 */
	private double recMemory;

	/**
	 * 已收金额
	 */
	private double alreadyMemory;

	/**
	 * 收款余额
	 */
	private double recBalMemory;

	/**
	 * 刻章
	 */
	private double stampCarving;

	/**
	 * CA证书
	 */
	private double caCard;

	/**
	 * 海关登记证
	 */
	private String chinaCustomsCard;

	/**
	 * 代理记账费用
	 */
	private double proxyMemory;

	/**
	 * 加急
	 */
	private double urgent;

	/**
	 * 其他
	 */
	private String other;

	/**
	 * 终止退客户款
	 */
	private double useRefundMemory;

	/**
	 * 结转金额
	 */
	private double carryDownMemory;

	/**
	 * 转出时间
	 */
	private String rollOutTime;

	/**
	 * 业务员
	 */
	private String salesman;

	/**
	 * 备注
	 */
	private String remark;
	
	
	/**
	 * boolean类型的测试
	 */
	private boolean flag;
	
	/**
	 * 获取时间
	 */
	private Date time;

	public double getSequence() {
		return sequence;
	}

	public void setSequence(double sequence) {
		this.sequence = sequence;
	}

	public String getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(String companySeq) {
		this.companySeq = companySeq;
	}

	public String getMemoryTime() {
		return memoryTime;
	}

	public void setMemoryTime(String memoryTime) {
		this.memoryTime = memoryTime;
	}

	public double getRecMemory() {
		return recMemory;
	}

	public void setRecMemory(double recMemory) {
		this.recMemory = recMemory;
	}

	public double getAlreadyMemory() {
		return alreadyMemory;
	}

	public void setAlreadyMemory(double alreadyMemory) {
		this.alreadyMemory = alreadyMemory;
	}

	public double getRecBalMemory() {
		return recBalMemory;
	}

	public void setRecBalMemory(double recBalMemory) {
		this.recBalMemory = recBalMemory;
	}

	public double getStampCarving() {
		return stampCarving;
	}

	public void setStampCarving(double stampCarving) {
		this.stampCarving = stampCarving;
	}

	public double getCaCard() {
		return caCard;
	}

	public void setCaCard(double caCard) {
		this.caCard = caCard;
	}

	public String getChinaCustomsCard() {
		return chinaCustomsCard;
	}

	public void setChinaCustomsCard(String chinaCustomsCard) {
		this.chinaCustomsCard = chinaCustomsCard;
	}

	public double getProxyMemory() {
		return proxyMemory;
	}

	public void setProxyMemory(double proxyMemory) {
		this.proxyMemory = proxyMemory;
	}

	public double getUrgent() {
		return urgent;
	}

	public void setUrgent(double urgent) {
		this.urgent = urgent;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public double getUseRefundMemory() {
		return useRefundMemory;
	}

	public void setUseRefundMemory(double useRefundMemory) {
		this.useRefundMemory = useRefundMemory;
	}

	public double getCarryDownMemory() {
		return carryDownMemory;
	}

	public void setCarryDownMemory(double carryDownMemory) {
		this.carryDownMemory = carryDownMemory;
	}

	public String getRollOutTime() {
		return rollOutTime;
	}

	public void setRollOutTime(String rollOutTime) {
		this.rollOutTime = rollOutTime;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CardBean [sequence=");
		builder.append(sequence);
		builder.append(", companySeq=");
		builder.append(companySeq);
		builder.append(", memoryTime=");
		builder.append(memoryTime);
		builder.append(", recMemory=");
		builder.append(recMemory);
		builder.append(", alreadyMemory=");
		builder.append(alreadyMemory);
		builder.append(", recBalMemory=");
		builder.append(recBalMemory);
		builder.append(", stampCarving=");
		builder.append(stampCarving);
		builder.append(", caCard=");
		builder.append(caCard);
		builder.append(", chinaCustomsCard=");
		builder.append(chinaCustomsCard);
		builder.append(", proxyMemory=");
		builder.append(proxyMemory);
		builder.append(", urgent=");
		builder.append(urgent);
		builder.append(", other=");
		builder.append(other);
		builder.append(", useRefundMemory=");
		builder.append(useRefundMemory);
		builder.append(", carryDownMemory=");
		builder.append(carryDownMemory);
		builder.append(", rollOutTime=");
		builder.append(rollOutTime);
		builder.append(", salesman=");
		builder.append(salesman);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", flag=");
		builder.append(flag);
		builder.append(", time=");
		builder.append(time);
		builder.append("]");
		return builder.toString();
	}
	
	

	
}

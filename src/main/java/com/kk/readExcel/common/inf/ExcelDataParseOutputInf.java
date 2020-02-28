package com.kk.readExcel.common.inf;

import org.apache.poi.ss.usermodel.Row;

/**
 * 进行excel的数据转换接口
 * 
 * 将java对象转化为excel中的行记录
 * 
 * @author liujun
 * @date 2016年4月1日
 * @verion 0.0.1
 */
public interface ExcelDataParseOutputInf<T> {

    /**
     * 将实现转换为excel的行记录信息
     * 
     * @param beaninfo
     *            实体信息
     * @return excel行记录
     */
    public void parseExcelRow(T beaninfo, Row row);

    /**
     * 实现为excel设置列头信息
     * 
     * @param column
     *            列的map信息
     * @param row
     *            excel行信息
     */
    public void setExcelColumn(Row row);

}

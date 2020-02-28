package com.kk.autocode.db;

import java.util.List;
import java.util.Map;

import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;

/**
 * 数据库操作的接口
* 源文件名：DbProcessInf.java
* 文件版本：1.0.0
* 创建作者：liujun
* 创建日期：2016年10月9日
* 修改作者：liujun
* 修改日期：2016年10月9日
* 文件描述：TODO
* 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
*/
public interface DbProcessInf {
	
	

	/**
	 * 获得表信息
	 * 方法描述
	 * @param dataSource
	 * @return
	 * @throws Exception 
	 * @创建日期 2016年9月12日
	 */
	public Map<String, TableInfoDTO> getTableInfo(String tableSpace) throws Exception;
   
	
	/**
     * 获得表列的信息
    * 方法描述
    * @param dataSource
    * @return
     * @throws Exception 
    * @创建日期 2016年9月12日
    */
    public Map<String, List<TableColumnDTO>> getTableColumnByBean(String tableSpace) throws Exception;

    /**
     * 获得表列mapp的相关信息
    * 方法描述
    * @param tableSpace
    * @return
    * @throws Exception
    * @创建日期 2016年10月9日
    */
    public Map<String, List<TableColumnDTO>> getTableColumnInfoByMap(String tableSpace) throws Exception;

    /**
     * 进行数据的填充
    * 方法描述
    * @param type 类型信息
    * @return
    * @创建日期 2016年10月9日
    */
    public String createValue(TableColumnDTO bean);

    /**
     * 通过数据库的类型转换为java的类型
    * 方法描述
    * @param dbType
    * @return
    * @创建日期 2016年10月9日
    */
    public String parseJavaType(TableColumnDTO bean);

    /**
     * 通过数据库的类型转换为java的类型
    * 方法描述
    * @param dbType
    * @return
    * @创建日期 2016年10月9日
    */
    public String getJavaDefValue(TableColumnDTO bean);

}

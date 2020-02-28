package com.kk.readExcel.service.tosql.service.beantosqlstr.valueProc;

import java.util.Map;

import com.kk.readExcel.service.tosql.bean.ColumnMsgInfo;
import com.kk.readExcel.service.tosql.service.console.FillEnum;
import com.kk.readExcel.service.tosql.service.loadsrc.SrcMap;

/**
 * 进行数据列的填充
 * 
 * @since 2017年3月19日 下午7:22:51
 * @version 0.0.1
 * @author liujun
 */
public class CellTowerTypeColumnProcImpl implements DataColumnProcInf {

	/**
	 * 资产类型，外键
	 */
	private static final String COLUMN_NAME = "ct_type_id";

	@SuppressWarnings("unchecked")
	@Override
	public String columnProc(ColumnMsgInfo columnmsg, String line) {

		String result = line;

		// 列名称
		if (COLUMN_NAME.toUpperCase().equals(columnmsg.getColumnName())) {

			if (null != line && !line.isEmpty()) {

				String value = line.substring(line.lastIndexOf(".") + 1);
				Map<String, Integer> entryMap = (Map<String, Integer>) SrcMap.get(FillEnum.CELLTOWERTYPE.getKey());

				Integer typeId = entryMap.get(value);

				if (null != typeId) {
					result = String.valueOf(typeId);
				}
				else
				{
					result = "6";
				}
			} else {
				// 返回其他类型信息
				result = "6";
			}
		}

		// 删除标志，0未删除，1删除
		else if ("delete_flag".toUpperCase().equals(columnmsg.getColumnName())) {
			result = "0";
		}

		if (columnmsg.getType() == 1) {
			if (null != result && !result.isEmpty() && !"null".equals(result)) {
				result = "'" + result + "'";
			}
		}

		if (null == result || result.isEmpty() || "null".equals(result)) {
			result = null;
		}

		return result;
	}

}

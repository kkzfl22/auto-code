package com.kk.readExcel.service.tosql.service.beantosqlstr.valueProc;

import java.util.Calendar;
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
public class AssertTypeColumnProcImpl implements DataColumnProcInf {

	/**
	 * 资产类型，外键
	 */
	private static final String COLUMN_NAME = "asset_type_id";

	@SuppressWarnings("unchecked")
	@Override
	public String columnProc(ColumnMsgInfo columnmsg, String line) {

		String result = line;

		if (null != line && !line.isEmpty()) {
			// 列名称
			if (COLUMN_NAME.toUpperCase().equals(columnmsg.getColumnName())) {
				String value = line.substring(line.lastIndexOf(".") + 1);
				Map<String, Integer> entryMap = (Map<String, Integer>) SrcMap.get(FillEnum.ASSERTTYPE.getKey());

				Integer typeId = entryMap.get(value);

				if (null != typeId) {
					result = String.valueOf(typeId);
				} else {
					// 返回其他类型信息
					result = "81";
				}
			}

			// 时间转换
			else if ("create_time".toUpperCase().equals(columnmsg.getColumnName())) {
				return timeProcess(line);
			}
			// 启用日期
			else if ("date_used".toUpperCase().equals(columnmsg.getColumnName())) {
				return timeProcess(line);
			}

			// 是否使用值的填充
			else if ("is_using".toUpperCase().equals(columnmsg.getColumnName())) {
				if ("在用".equals(line)) {
					result = "1";
				} else {
					result = "2";
				}
			}
			// 删除标志，0未删除，1删除
			else if ("delete_flag".toUpperCase().equals(columnmsg.getColumnName())) {
				result = "0";
			}
			// 传输线路资源
			else if ("transfer_quantity".toUpperCase().equals(columnmsg.getColumnName())) {
				if (null == line || line.isEmpty()) {
					result = "0";
				} else {
					result = line;
				}
			}

			// 租金
			else if ("rental".toUpperCase().equals(columnmsg.getColumnName())) {
				if (null == line || line.isEmpty()) {
					result = "0";
				} else {
					result = line;
				}
			}

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

	/**
	 * 时间处理
	 * 
	 * @param line
	 * @return
	 */
	private String timeProcess(String line) {
		if (null != line && line.indexOf("-") != -1) {
			Calendar time = Calendar.getInstance();
			String year = line.substring(0, line.indexOf("-"));

			line = line.substring(line.indexOf("-") + 1);
			String month = line.substring(0, line.indexOf("-"));

			line = line.substring(line.indexOf("-") + 1);

			String day = line;

			time.set(Calendar.YEAR, Integer.parseInt(year));
			time.set(Calendar.MONTH, Integer.parseInt(month));
			time.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));

			String result = String.valueOf(time.getTime().getTime());
			return result;
		}

		return line;

	}

}

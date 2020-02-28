package com.kk.readExcel.service.tosql.service.beantosqlstr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kk.readExcel.service.tosql.bean.ColumnMsgInfo;
import com.kk.readExcel.service.tosql.bean.DataParseToSQLBean;
import com.kk.readExcel.service.tosql.bean.TableInfoBean;
import com.kk.readExcel.service.tosql.service.beantosqlstr.valueProc.DataColumnProcInf;

/**
 * 将数据转换为 sql的字符串信息
 * 
 * @since 2017年3月19日 下午3:58:50
 * @version 0.0.1
 * @author liujun
 */
public class DataToSqlServiceImpl implements DataToSqlService {

	/**
	 * 进行列处理
	 */
	private DataColumnProcInf columnProcess;

	@Override
	public List<List<String>> beanToSqlStr(TableInfoBean toBean, int maxLength) {
		String tableName = toBean.getTableName();

		Map<Integer, ColumnMsgInfo> column = toBean.getColumn();

		List<DataParseToSQLBean> dataValue = toBean.getValue();

		List<List<String>> list = new ArrayList<>();

		List<String> dataSqlList = new ArrayList<>();

		DataParseToSQLBean dataBean = null;

		for (int i = 0; i < dataValue.size(); i++) {

			dataBean = dataValue.get(i);

			// 做分段集合
			if (i % maxLength == 0 && i != 0) {
				dataSqlList.add(this.parseSql(tableName, column, dataBean));
				list.add(dataSqlList);
				dataSqlList = new ArrayList<>();
			} else {
				dataSqlList.add(this.parseSql(tableName, column, dataBean));
			}
		}

		list.add(dataSqlList);

		return list;
	}

	/**
	 * 将数据转换为sql信息
	 * 
	 * @param table
	 *            表名
	 * @param column
	 *            列名
	 * @param data
	 *            数据
	 * @return sql信息
	 */
	private String parseSql(String table, Map<Integer, ColumnMsgInfo> column, DataParseToSQLBean data) {
		StringBuilder sql = new StringBuilder();

		sql.append("insert into ").append(table);
		sql.append("(");
		// 添加列信息
		column.entrySet().forEach((Entry<Integer, ColumnMsgInfo> en) -> {
			sql.append(en.getValue().getColumnName()).append(",");
		});
		// 去掉多余的符号
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		sql.append("values(");

		// 进行值的输出
		column.entrySet().forEach((Entry<Integer, ColumnMsgInfo> en) -> {
			sql.append(columnProcess.columnProc(en.getValue(), data.getNameValue().get(en.getKey()))).append(",");
		});
		sql.deleteCharAt(sql.length() - 1);
		sql.append(");");

		return sql.toString();
	}

	@Override
	public void setDataColumnProc(DataColumnProcInf install) {
		this.columnProcess = install;

	}

}

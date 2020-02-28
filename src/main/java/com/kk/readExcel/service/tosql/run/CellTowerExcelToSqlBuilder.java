package com.kk.readExcel.service.tosql.run;

import java.util.List;

import com.kk.readExcel.common.ExcelProcException;
import com.kk.readExcel.common.ExcelReadProcess;
import com.kk.readExcel.service.tosql.bean.TableInfoBean;
import com.kk.readExcel.service.tosql.service.beantosqlstr.DataToSqlService;
import com.kk.readExcel.service.tosql.service.beantosqlstr.DataToSqlServiceImpl;
import com.kk.readExcel.service.tosql.service.beantosqlstr.valueProc.CellTowerTypeColumnProcImpl;
import com.kk.readExcel.service.tosql.service.beantosqlstr.valueProc.DataColumnProcInf;
import com.kk.readExcel.service.tosql.service.excel.DataParseSqlExcelImportExcelProcess;
import com.kk.readExcel.service.tosql.service.writefile.WriteFileInf;
import com.kk.readExcel.service.tosql.service.writefile.WriteSqlFileImpl;

/**
 * 进行读取exlce数据到sql的一套流程
 * 
 * @since 2017年3月19日 下午5:02:19
 * @version 0.0.1
 * @author liujun
 */
public class CellTowerExcelToSqlBuilder {

	/**
	 * 1,加载excel文件
	 */
	private ExcelReadProcess readExcel = new DataParseSqlExcelImportExcelProcess();

	/**
	 * 进行数据生成sql处理
	 */
	private DataToSqlService toSqlProc = new DataToSqlServiceImpl();

	/**
	 * 进行文件的写入
	 */
	private WriteFileInf writeSql = new WriteSqlFileImpl();

	/**
	 * 进行数据列处理
	 */
	private DataColumnProcInf columnProc = new CellTowerTypeColumnProcImpl();

	/**
	 * 获取数据信息
	 * 
	 * @return
	 */
	private TableInfoBean getTableData(String name,int sheetIndex) {
		TableInfoBean tableBean = null;
		try {
			tableBean = readExcel.readExcelDataList(name,sheetIndex);
		} catch (ExcelProcException e) {
			e.printStackTrace();
		}

		return tableBean;
	}

	/**
	 * 转换为sql操作
	 * 
	 * @param bean
	 *            文件bean
	 * @param maxLength
	 *            最大单集合大小
	 * @return 集合信息
	 */
	private List<List<String>> tosqlProc(TableInfoBean bean, int maxLength) {

		// 设置处理实例信息
		toSqlProc.setDataColumnProc(columnProc);

		return toSqlProc.beanToSqlStr(bean, maxLength);
	}

	/**
	 * 进行多文件配合写入数据
	 * 
	 * @param list
	 *            数据集合信息
	 * @param spitFlag
	 *            集合标识
	 * @param fileName
	 *            名称信息
	 */
	private void writeFile(List<List<String>> list, boolean spitFlag, String fileName) {
		writeSql.wirteSql(list, spitFlag, fileName);
	}

	/**
	 * 进行excel到sql的处理过程
	 * 
	 * @param inputFileName
	 *            输入的文件名称
	 * @param maxLength
	 *            最大长度
	 * @param outputFileName
	 *            小文件
	 */
	public void readelExceltoSql(String inputFileName, int maxLength, String outputFileName) {
		// 1，读取excel信息,得到数据源信息
		TableInfoBean excelSrc = getTableData(inputFileName,0);

		// 2,进行中间转换
		List<List<String>> list = this.tosqlProc(excelSrc, maxLength);

		// 进行文件写入
		if (null != list && !list.isEmpty()) {
			// 大于一个集合说明是多文件,否则即为单文件
			if (list.size() > 1) {
				writeFile(list, true, outputFileName);
			} else {
				writeFile(list, false, outputFileName);
			}
		}
	}

}

package com.kk.readExcel.case2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

import com.kk.autocode.util.IOutils;
import com.kk.excel.read.CardBean;
import com.kk.readExcel.case2.bean.ExcelDataToJavaBean;
import com.kk.readExcel.case2.bean.comm.ColumnToJavaBeanMsg;
import com.kk.readExcel.case2.bean.comm.ExcelReadBean;
import com.kk.readExcel.case2.console.ExcelCellTypeEnum;
import com.kk.readExcel.case2.console.ExcelErrorCode;
import com.kk.readExcel.case2.console.JavaExcelProcException;
import com.kk.readExcel.case2.inf.ExcelDataReadInputInf;

/**
 * 进行excel的读取操作
 *
 * @author liujun
 * @date 2016年4月8日
 * @verion 0.0.1
 */
public abstract class AbsExcelCommReadProcess {

	/**
	 * 通过过此抽换方法调用实现
	 *
	 * @return
	 */
	protected abstract ExcelDataReadInputInf getParseBean();

	/**
	 * 读取Excel数据转换成集合数据
	 *
	 * @param path
	 *            excel路径
	 * @return 结果
	 */
	@SuppressWarnings({ "resource" })
	public List<ExcelDataToJavaBean> readExcelDataList(String file, int sheetIndex) throws JavaExcelProcException {

		List<ExcelDataToJavaBean> list = new ArrayList<>();

		POIFSFileSystem fs = null;
		try {
			fs = new POIFSFileSystem(new FileInputStream(file));

			// 得到Excel工作簿对象
			HSSFWorkbook wookbook = new HSSFWorkbook(fs);
			// 得到Excel工作表对象
			HSSFSheet sheet = wookbook.getSheetAt(sheetIndex);

			// 得到总行数
			Iterator<Row> iterRow = sheet.iterator();

			// 得到转换对象
			ExcelDataReadInputInf readProc = getParseBean();

			// 1,跳过无用行
			readProc.jumpRownum(iterRow);

			// 2,获取java与excel对应的列的相关信息,列名，类型，描述
			ExcelReadBean readBean = readProc.getReadBean(iterRow);

			// 进行数据内容的读取操作
			while (iterRow.hasNext()) {
				// 得到Excel工作表的行
				Row row = iterRow.next();

				// 将excel的数据转换为javaBean对象信息
				list.add(this.rowToBean(row, readBean));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new JavaExcelProcException(ExcelErrorCode.FILE_NOTFOUNT.getMsg());
		} catch (IOException e) {
			e.printStackTrace();
			throw new JavaExcelProcException(ExcelErrorCode.FILE_FILE_IS_ERROR.getMsg());
		} finally {
			IOutils.closeStream(fs);
		}

		return list;
	}

	/**
	 * 将excel中的数据转换为javabean对象信息
	 *
	 * @param excelRow
	 *            行对象信息
	 * @param columnMap
	 *            列对象信息
	 * @param beanClass
	 *            java对应的bean的类型信息
	 * @return 加载数据信息
	 * @throws Exception
	 *             异常信息
	 */
	@SuppressWarnings("rawtypes")
	private ExcelDataToJavaBean rowToBean(Row excelRow, ExcelReadBean readbean) throws JavaExcelProcException {

		CardBean resut = null;

		try {
			// 获取列信息
			Map<Integer, ColumnToJavaBeanMsg> columnMap = readbean.getColumnMap();

			// 获取所对应的java信息
			Class beanClass = readbean.getJavaClassType();

			Object instObj = beanClass.newInstance();

			// 获取java中所有的方法
			Method[] methos = beanClass.getMethods();

			Iterator<Entry<Integer, ColumnToJavaBeanMsg>> columnIter = columnMap.entrySet().iterator();
			Entry<Integer, ColumnToJavaBeanMsg> colItem = null;

			while (columnIter.hasNext()) {
				colItem = columnIter.next();
				// 打到对应的方法
				String setName = humpName(colItem.getValue().getName());
				for (int i = 0; i < methos.length; i++) {
					if (setName.equals(methos[i].getName())) {

						// 进行字符串类型信息的设置
						if (ExcelCellTypeEnum.CELLTYPE_STRING.getType().equals(colItem.getValue().getType())) {
							// 进行方法的调用
							methos[i].invoke(instObj,
									excelRow.getCell(colItem.getValue().getIndex()).getStringCellValue());
						}
						// 进行数字类型的设置
						else if (ExcelCellTypeEnum.CELLTYPE_NUMBER.getType().equals(colItem.getValue().getType())) {
							// 进行方法的调用
							methos[i].invoke(instObj,
									excelRow.getCell(colItem.getValue().getIndex()).getNumericCellValue());
						}
						// 进行boolean类型的设置
						else if (ExcelCellTypeEnum.CELLTYPE_BOOLEAN.getType().equals(colItem.getValue().getType())) {
							// 进行方法的调用
							methos[i].invoke(instObj,
									excelRow.getCell(colItem.getValue().getIndex()).getBooleanCellValue());
						}
						// 进行时间类型的设置
						else if (ExcelCellTypeEnum.CELLTYPE_DATE.getType().equals(colItem.getValue().getType())) {
							// 进行方法的调用
							methos[i].invoke(instObj,
									excelRow.getCell(colItem.getValue().getIndex()).getDateCellValue());
						}
					}
				}
			}

			resut = (CardBean) instObj;

		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new JavaExcelProcException(ExcelErrorCode.EXCEL_PARSE_DATA.getMsg());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new JavaExcelProcException(ExcelErrorCode.EXCEL_PARSE_DATA.getMsg());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new JavaExcelProcException(ExcelErrorCode.EXCEL_PARSE_DATA.getMsg());
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new JavaExcelProcException(ExcelErrorCode.EXCEL_PARSE_DATA.getMsg());
		}

		return resut;
	}

	/**
	 * 进行属性的驼峰命名法
	 *
	 * @param name
	 *            名称
	 * @return
	 */
	private String humpName(String name) {
		String nameStart = name.substring(0, 1).toUpperCase();
		String nemeSuffix = name.substring(1);
		return "set" + nameStart + nemeSuffix;
	}

	/**
	 * 提供默认的列读取器,按列数读取出列信息
	 *
	 * @param row
	 * @return
	 */
	protected Map<Integer, ColumnToJavaBeanMsg> defaultGetColumn(Iterator<Row> iterRow) {

		HSSFRow row = (HSSFRow) iterRow.next();

		Map<Integer, ColumnToJavaBeanMsg> resultMap = new HashMap<>();

		// 获得excel的列数信息
		short cellNum = row.getLastCellNum();

		ColumnToJavaBeanMsg bean = null;
		// 获取excel的列的名称信息
		for (int i = 0; i < cellNum; i++) {
			bean = new ColumnToJavaBeanMsg();
			bean.setIndex(i);
			bean.setName(row.getCell(i).getStringCellValue().trim());
			resultMap.put(i, bean);
		}

		// 设置类型信息
		HSSFRow rowType = (HSSFRow) iterRow.next();
		// 设置excel列的类型信息
		for (int i = 0; i < cellNum; i++) {
			bean = resultMap.get(i);
			bean.setType(rowType.getCell(i).getStringCellValue().trim());
		}

		HSSFRow describeRow = (HSSFRow) iterRow.next();
		// 从excel中获取名称并设置到实体中
		for (int i = 0; i < cellNum; i++) {
			bean = resultMap.get(i);
			bean.setDescribe(describeRow.getCell(i).getStringCellValue().trim());
		}

		return resultMap;
	}

}

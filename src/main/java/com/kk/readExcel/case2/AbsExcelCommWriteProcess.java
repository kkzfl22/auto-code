package com.kk.readExcel.case2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.IOUtils;

import com.kk.readExcel.case2.console.ExcelErrorCode;
import com.kk.readExcel.case2.console.JavaExcelProcException;
import com.kk.readExcel.case2.inf.ExcelDataWriteOutputInf;

/**
 * 直接进行数据导出功能操作
 * 
 * @author liujun
 * @date 2016年4月8日
 * @verion 0.0.1
 */
public abstract class AbsExcelCommWriteProcess<T> {
	
	/**
	 * 通过过此抽换方法调用实现
	 * 
	 * @return
	 */
	protected abstract ExcelDataWriteOutputInf<T> getToExcelInstall();

	/**
	 * 写入数据,从集合中
	 * 
	 * @param outPath
	 *            定入路径
	 * @param sheetName
	 *            sheet标签页名称
	 * @param cloumnMap
	 *            参数
	 * @param writeList
	 *            写入的集体
	 * @throws JavaExcelProcException
	 *             异常
	 */
	public void writeExcelByList(String outPath, List<T> writeList) throws JavaExcelProcException {
		POIFSFileSystem fs = null;

		// 创建一个工作薄
		HSSFWorkbook wb = null;
		// 创建sheet页
		HSSFSheet sheet = null;

		FileOutputStream output = null;
		try {

			// 得到Excel工作簿对象
			wb = new HSSFWorkbook();
			// 创建一个新的sheet页
			sheet = wb.createSheet();

			// 进行输出文件内容
			if (null != writeList && !writeList.isEmpty()) {
				ExcelDataWriteOutputInf<T> toExcelBean = getToExcelInstall();

				// 进行首行列信息设置
				toExcelBean.excelColumnWrite(sheet);

				// 进行excel记录的写入操作
				for (int i = 0; i < writeList.size(); i++) {
					toExcelBean.excelRowWrite(writeList.get(i), sheet);
				}
			}

			output = new FileOutputStream(outPath);
			wb.write(output);

			output.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new JavaExcelProcException(ExcelErrorCode.FILE_NOTFOUNT.getMsg());
		} catch (IOException e) {
			e.printStackTrace();
			throw new JavaExcelProcException(ExcelErrorCode.FILE_FILE_IS_ERROR.getMsg());
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(fs);
		}
	}

}

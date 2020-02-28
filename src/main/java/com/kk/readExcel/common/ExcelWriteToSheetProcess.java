package com.kk.readExcel.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.IOUtils;

import com.kk.readExcel.common.inf.ExcelDataParseOutputInf;


/**
 * 进行excel的写入到新的sheet页的数据操作
 * 
 * @author liujun
 * @date 2016年4月8日
 * @verion 0.0.1
 */
public abstract class ExcelWriteToSheetProcess<T> {
    /**
     * 通过过此抽换方法调用实现
     * 
     * @return
     */
    protected abstract ExcelDataParseOutputInf<T> getToExcelInstall();

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
     * @throws ExcelProcException
     *             异常
     */
    public void writeExcelByList(String outPath, List<T> writeList) throws ExcelProcException {
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

            HSSFRow columnRow = sheet.createRow(0);

            // 进行输出文件内容
            if (null != writeList && !writeList.isEmpty()) {
                ExcelDataParseOutputInf<T> toExcelBean = getToExcelInstall();

                // 进行首行列信息设置
                toExcelBean.setExcelColumn(columnRow);

                for (int i = 0; i < writeList.size(); i++) {
                    HSSFRow row = sheet.createRow(i + 1);

                    toExcelBean.parseExcelRow(writeList.get(i), row);
                }
            }

            output = new FileOutputStream(outPath);

            wb.write(output);

            output.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ExcelProcException(ErrorCode.FILE_NOTFOUNT.getMsg());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExcelProcException(ErrorCode.FILE_FILE_IS_ERROR.getMsg());
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(wb);
            IOUtils.closeQuietly(fs);
        }
    }

}

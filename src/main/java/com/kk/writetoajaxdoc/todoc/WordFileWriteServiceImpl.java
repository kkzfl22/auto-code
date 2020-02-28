package com.kk.writetoajaxdoc.todoc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * 读取模板的doc文件，写入新的doc文件
 *
 * @author liujun
 */
public class WordFileWriteServiceImpl {

  public void readDocToList(String input, String output) throws Exception {

    // 读取word源文件
    FileInputStream fileInputStream = new FileInputStream(input);
    // POIFSFileSystem pfs = new POIFSFileSystem(fileInputStream);
    XWPFDocument document = new XWPFDocument(fileInputStream);

    // 获取所有表格
    List<XWPFTable> tables = document.getTables();

    // 这里简单取第一个表格
    XWPFTable table = tables.get(0);

    int numRow = table.getNumberOfRows();

    System.out.println("行数:" + numRow);

    // 表格的插入行有两种方式，这里使用addNewRowBetween，因为这样会保留表格的样式，
    // 就像我们在word文档的表格中插入行一样。注意这里不要使用insertNewTableRow方法插入新行，
    // 这样插入的新行没有样式，很难看
    // 获取到刚刚插入的行
    XWPFTableRow rows = table.createRow();
    // table.addRow(table.createRow());
    // table.addRow(table.createRow());
    // table.addRow(table.createRow());
    // table.addRow(table.createRow());

    numRow = table.getNumberOfRows();
    System.out.println("行数:" + numRow);

    rows.getCell(1).setText("txtsdsf");

    fileInputStream.close();
    // 写到目标文件
    OutputStream outputStr = new FileOutputStream(output);
    document.write(outputStr);
    outputStr.close();
  }

  public static void main(String[] args) {
    WordFileWriteServiceImpl write = new WordFileWriteServiceImpl();

    String path =
        WordFileWriteServiceImpl.class.getClassLoader().getResource("toajaxdoc").getPath();

    System.out.println("路径 :" + path);
    try {
      write.readDocToList(path + "/接口文档信息.docx", path + "/output.docx");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

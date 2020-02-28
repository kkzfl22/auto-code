package com.kk.docprocess.doctoadapterdoc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

public class XwpfTest {
	
	 /** 
     * word整体样式 
     */  
    private static CTStyles wordStyles = null;

	/**
	 * Word整体样式
	 */
	static {
		XWPFDocument template;
		try {
			// 读取模板文档
			template = new XWPFDocument(new FileInputStream("D:/java/test/doc/style.docx"));
			// 获得模板文档的整体样式
			wordStyles = template.getStyle();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		XwpfTest test = new XwpfTest();
		String path = "D:/java/test/doc/";
		test.testSimpleWrite(path + "simp.docx");
		test.testWriteTable(path + "simptable.docx");

	}
	
   

	/**
	 * 基本的写操作
	 * 
	 * @throws Exception
	 */
	public void testSimpleWrite(String path) throws Exception {
		// 新建一个文档
		XWPFDocument doc = new XWPFDocument();
		// // 创建一个段落
		// XWPFParagraph para = doc.createParagraph();
		//
		// // 一个XWPFRun代表具有相同属性的一个区域。
		// XWPFRun run = para.createRun();
		// run.setBold(true); // 加粗
		// run.setText("加粗的内容");
		// run = para.createRun();
		// run.setColor("FF0000");
		// run.setText("红色的字。");

        // 获取新建文档对象的样式  
        XWPFStyles newStyles = doc.createStyles();  
        // 关键行// 修改设置文档样式为静态块中读取到的样式  
        newStyles.setStyles(wordStyles);  
  
        // 开始内容输入  
        // 标题1，1级大纲  
        XWPFParagraph para1 = doc.createParagraph();  
        // 关键行// 1级大纲  
        para1.setStyle("2");  
        XWPFRun run1 = para1.createRun();  
        // 标题内容  
        run1.setText("接口文档信息");  
  
        // 标题2  
        XWPFParagraph para2 = doc.createParagraph();  
        // 关键行// 2级大纲  
        para2.setStyle("3");  
        XWPFRun run2 = para2.createRun();  
        // 标题内容  
        run2.setText("添加用户接口");  
        
        // 标题2  
        XWPFParagraph para3 = doc.createParagraph();  
        // 关键行// 2级大纲  
        para3.setStyle("4");  
        XWPFRun run3 = para3.createRun();  
        // 标题内容  
        run3.setText("接口地址");  
  
        // 正文  
        XWPFParagraph paraX = doc.createParagraph();  
        XWPFRun runX = paraX.createRun();  
        // 正文内容  
        runX.setText("正文");  
  
        // word写入到文件  
        FileOutputStream fos = new FileOutputStream("D:/myDoc.docx");  
        doc.write(fos);  
        fos.close();  

		OutputStream os = new FileOutputStream(path);
		// 把doc输出到输出流
		doc.write(os);
		this.close(os);
	}

	/***
	 * 写一个表格
	 * 
	 * @throws Exception
	 */
	public void testWriteTable(String path) throws Exception {
		XWPFDocument doc = new XWPFDocument();
		// 创建一个5行5列的表格
		XWPFTable table = doc.createTable(5, 5);
		// 这里增加的列原本初始化创建的那5行在通过getTableCells()方法获取时获取不到，但通过row新增的就可以。
		// table.addNewCol(); //给表格增加一列，变成6列
		table.createRow(); // 给表格新增一行，变成6行
		List<XWPFTableRow> rows = table.getRows();
		// 表格属性
		CTTblPr tablePr = table.getCTTbl().addNewTblPr();
		// 表格宽度
		CTTblWidth width = tablePr.addNewTblW();
		width.setW(BigInteger.valueOf(8000));
		XWPFTableRow row;
		List<XWPFTableCell> cells;
		XWPFTableCell cell;
		int rowSize = rows.size();
		int cellSize;
		for (int i = 0; i < rowSize; i++) {
			row = rows.get(i);
			// 新增单元格
			row.addNewTableCell();
			// 设置行的高度
			row.setHeight(500);
			// 行属性
			// CTTrPr rowPr = row.getCtRow().addNewTrPr();
			// 这种方式是可以获取到新增的cell的。
			// List<CTTc> list = row.getCtRow().getTcList();
			cells = row.getTableCells();
			cellSize = cells.size();
			for (int j = 0; j < cellSize; j++) {
				cell = cells.get(j);
				if ((i + j) % 2 == 0) {
					// 设置单元格的颜色
					cell.setColor("ff0000"); // 红色
				} else {
					cell.setColor("0000ff"); // 蓝色
				}
				// 单元格属性
				CTTcPr cellPr = cell.getCTTc().addNewTcPr();
				cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
				if (j == 3) {
					// 设置宽度
					cellPr.addNewTcW().setW(BigInteger.valueOf(3000));
				}
				cell.setText(i + ", " + j);
			}
		}
		// 文件不存在时会自动创建
		OutputStream os = new FileOutputStream(path);
		// 写入文件
		doc.write(os);
		this.close(os);
	}

	/**
	 * 关闭输出流
	 * 
	 * @param os
	 */
	private void close(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
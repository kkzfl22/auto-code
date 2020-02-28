package com.kk.element.doc.itext;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/13
 */
public class TestItext {

  public void createDocContext(String file) throws DocumentException, IOException {

    // 设置纸张大小
    Document document = new Document(PageSize.A4);

    // 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中
    RtfWriter2.getInstance(document, new FileOutputStream(file));

    document.open();

    // 设置中文字体
    BaseFont bfChinese = BaseFont.createFont();

    // 标题字体风格
    Font titleFont = new Font(bfChinese, 12, Font.BOLD);
    // 正文字体风格
    Font contextFont = new Font(bfChinese, 10, Font.NORMAL);

    Paragraph title = new Paragraph("标题");
    // 设置标题格式对齐方式
    title.setAlignment(Element.ALIGN_CENTER);

    title.setFont(titleFont);

    document.add(title);

    String contextString =
        "iText是一个能够快速产生PDF文件的java类库。"
            + " \n" // 换行                + "iText的java类对于那些要产生包含文本，"
            + "表格，图形的只读文档是很有用的。它的类库尤其与java Servlet有很好的给合。"
            + "使用iText与PDF能够使你正确的控制Servlet的输出。";

    Paragraph context = new Paragraph(contextString);

    // 正文格式左对齐
    context.setAlignment(Element.ALIGN_LEFT);

    context.setFont(contextFont);

    // 离上一段落（标题）空的行数
    context.setSpacingBefore(5);

    // 设置第一行空的列数
    context.setFirstLineIndent(20);

    document.add(context);

    // 利用类FontFactory结合Font和Color可以设置各种各样字体样式

    Paragraph underline =
        new Paragraph(
            "下划线的实现",
            FontFactory.getFont(
                FontFactory.HELVETICA_BOLDOBLIQUE, 18, Font.UNDERLINE, new Color(0, 0, 255)));

    document.add(underline);

    // 添加图片 Image.getInstance即可以放路径又可以放二进制字节流

    Image img = Image.getInstance("D:\\java\\test\\meda\\v-21.png");

    img.setAbsolutePosition(0, 0);

    img.setAlignment(Image.RIGHT); // 设置图片显示位置

    // img.scaleAbsolute(60, 60);// 直接设定显示尺寸

    // img.scalePercent(50);//表示显示的大小为原尺寸的50%

    // img.scalePercent(25, 12);//图像高宽的显示比例

    // img.setRotation(30);//图像旋转一定角度

    // 设置 Table 表格

    Table aTable = new Table(3);

    int width[] = {25, 25, 50};

    aTable.setWidths(width); // 设置每列所占比例

    aTable.setWidth(90); // 占页面宽度 90%

    aTable.setAlignment(Element.ALIGN_CENTER); // 居中显示

    aTable.setAlignment(Element.ALIGN_MIDDLE); // 纵向居中显示

    aTable.setAutoFillEmptyCells(true); // 自动填满

    aTable.setBorderWidth(1); // 边框宽度

    aTable.setBorderColor(new Color(0, 125, 255)); // 边框颜色

    aTable.setPadding(2); // 衬距，看效果就知道什么意思了

    aTable.setSpacing(3); // 即单元格之间的间距

    aTable.setBorder(2); // 边框        // 设置表头

    Cell haderCell = new Cell("表格表头");

    haderCell.setHeader(true);

    haderCell.setColspan(3);

    aTable.addCell(haderCell);

    aTable.endHeaders();

    Font fontChinese = new Font(bfChinese, 12, Font.NORMAL, Color.GREEN);

    Cell cell = new Cell(new Phrase("这是一个测试的 3*3 Table 数据", fontChinese));

    cell.setVerticalAlignment(Element.ALIGN_TOP);

    cell.setBorderColor(new Color(255, 0, 0));

    aTable.addCell(cell);

    aTable.addCell(new Cell(img));

    aTable.addCell(new Cell(img));

    aTable.addCell(new Cell(img));
    aTable.addCell(new Cell(img));
    aTable.addCell(new Cell(img));

    Cell cell3 = new Cell(new Phrase("一行三列数据"));

    cell3.setColspan(3);

    cell3.setVerticalAlignment(Element.ALIGN_CENTER);

    aTable.addCell(cell3);

    document.add(aTable);

    document.add(new Paragraph("\n"));

    // 添加图片 Image.getInstance即可以放路径又可以放二进制字节流
    Image img1 = Image.getInstance("D:\\java\\test\\meda\\v-21.png");

    img.setAbsolutePosition(0, 0);

    img.setAlignment(Image.RIGHT); // 设置图片显示位置

    // img.scaleAbsolute(60, 60);// 直接设定显示尺寸

    // img.scalePercent(50);//表示显示的大小为原尺寸的50%

    // img.scalePercent(25, 12);//图像高宽的显示比例

    // img.setRotation(30);//图像旋转一定角度

    document.add(img);

    document.close();
  }

  public static void main(String[] args) {

    TestItext word = new TestItext();

    String path = TestItext.class.getClassLoader().getResource("./").getPath();
    System.out.println("输出地址:" + path);

    String file = path + File.separator + "outfile.doc";

    try {
      word.createDocContext(file);
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (IOException e) {

      e.printStackTrace();
    }
  }
}

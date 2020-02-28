package com.kk.docprocess.doctosql;

import java.io.InputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class WordDemoReadTest {

    public static void main(String[] args) {

        WordDemoReadTest doc = new WordDemoReadTest();
        doc.testWord("com/kk/docprocess/doctojava/service/01.doc");
        // doc.testWord1();
    }

    public void testWord(String path) {
        try {
            InputStream in = WordDemoReadTest.class.getClassLoader().getResourceAsStream(path);// 载入文档
            POIFSFileSystem pfs = new POIFSFileSystem(in);
            HWPFDocument hwpf = new HWPFDocument(pfs);
            Range range = hwpf.getRange();// 得到文档的读取范围
            TableIterator it = new TableIterator(range);
            // 迭代文档中的表格
            while (it.hasNext()) {
                Table tb = (Table) it.next();
                // 迭代行，默认从0开始
                for (int i = 0; i < tb.numRows(); i++) {
                    TableRow tr = tb.getRow(i);
                    // 迭代列，默认从0开始
                    for (int j = 0; j < tr.numCells(); j++) {
                        TableCell td = tr.getCell(j);// 取得单元格
                        // 取得单元格的内容
                        for (int k = 0; k < td.numParagraphs(); k++) {
                            Paragraph para = td.getParagraph(k);
                            String s = para.text();
                            System.out.println(s.substring(0, s.length() - 1));
                        } // end for
                    } // end for
                } // end for

                System.out.println("当前结束==========================");
            } // end while
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// end method

    public void testWord1() {
        try {
            String file = WordDemoReadTest.class.getClassLoader()
                    .getResource("com/kk/docprocess/doctojava/service/01.doc").getPath();
            InputStream in = WordDemoReadTest.class.getClassLoader()
                    .getResourceAsStream("com/kk/docprocess/doctojava/service/01.doc");
            // word 2003： 图片不会被读取
            WordExtractor ex = new WordExtractor(in);
            String text2003 = ex.getText();
            System.out.println(text2003);
            // word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后
            OPCPackage opcPackage = POIXMLDocument.openPackage(file);
            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
            String text2007 = extractor.getText();
            System.out.println(text2007);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

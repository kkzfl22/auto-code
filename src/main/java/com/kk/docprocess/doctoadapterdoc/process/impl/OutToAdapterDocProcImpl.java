package com.kk.docprocess.doctoadapterdoc.process.impl;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kk.docprocess.doctoadapterdoc.process.compile.builder.BuilderJsonRequest;
import com.kk.docprocess.doctoadapterdoc.process.compile.builder.BuilderJsonResponse;
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

import com.kk.autocode.util.IOutils;
import com.kk.docprocess.doctoadapterdoc.bean.AdapterBean;
import com.kk.docprocess.doctoadapterdoc.bean.ParamBase;
import com.kk.docprocess.doctoadapterdoc.process.OutToAdapterDocProc;
import com.kk.docprocess.doctoadapterdoc.process.compile.builder.BuilderComperRequest;
import com.kk.docprocess.doctoadapterdoc.process.compile.builder.BuilderComperResponse;
import com.kk.docprocess.doctosql.run.MysqlCreateSqlRun;

public class OutToAdapterDocProcImpl implements OutToAdapterDocProc {

  /** word整体样式 */
  private static CTStyles wordStyles = null;

  /** Word整体样式 */
  static {
    XWPFDocument template;
    try {
      String path = "doctoadapter/style.docx";
      InputStream instyple = MysqlCreateSqlRun.class.getClassLoader().getResourceAsStream(path);
      // 读取模板文档
      template = new XWPFDocument(instyple);
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

  @Override
  public void outputAdapterDoc(Map<String, List<AdapterBean>> apaterMap, String outPath) {

    OutputStream os = null;
    BufferedOutputStream bufferOutput = null;
    // 新建一个文档
    XWPFDocument doc = new XWPFDocument();

    try {

      // 获取新建文档对象的样式
      XWPFStyles newStyles = doc.createStyles();
      // 关键行// 修改设置文档样式为静态块中读取到的样式
      newStyles.setStyles(wordStyles);

      int index = 1;
      addStyleDoc(doc, "2", "接口文档信息");

      List<AdapterBean> list = null;

      for (Entry<String, List<AdapterBean>> entryIter : apaterMap.entrySet()) {
        addStyleDoc(doc, "3", index + "." + entryIter.getKey());
        list = entryIter.getValue();

        for (AdapterBean adapterBean : list) {
          addStyleDoc(doc, "4", adapterBean.getMsg());
          addStyleDoc(doc, "5", "接口地址");
          addDoc(doc, adapterBean.getUrl());
          addStyleDoc(doc, "5", "请求参数");
          addDocTable(doc, adapterBean.getRequest());
          addStyleDoc(doc, "5", "响应");

          addDocTable(doc, adapterBean.getResponse().getCommRsp());

          if (null != adapterBean.getResponse().getRspDataList()) {
            addStyleDoc(
                doc,
                "5",
                adapterBean
                    .getResponse()
                    .getCommRsp()
                    .get(adapterBean.getResponse().getCommRsp().size() - 1)
                    .getParamType());
            addDocTable(doc, adapterBean.getResponse().getRspDataList());
          }

          // 添加json格式的输入与输出
          addStyleDoc(doc, "5", "示例");
          addStyleDoc(doc, "6", "请求");

          // 进行请求生成json格式的数据
          String jsonReq = BuilderJsonRequest.INSTANCE.buildRequest(adapterBean.getRequest());
          // 将json写入到word文件中
          addDoc(doc, jsonReq);

          // 将响应生成json格式的数据
          addStyleDoc(doc, "6", "响应");
          String jsonRsp = BuilderJsonResponse.INSTANCE.buildResponse(adapterBean.getResponse());
          // 将json写入到word文件中
          addDoc(doc, jsonRsp);
        }

        index++;
      }

      // 文件不存在时会自动创建
      os = new FileOutputStream(outPath);
      bufferOutput = new BufferedOutputStream(os, 1024 * 64);
      // 写入文件
      doc.write(bufferOutput);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        doc.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      IOutils.closeStream(bufferOutput);
      IOutils.closeStream(os);
    }
  }

  /**
   * 将数据进行表格化写入
   *
   * @param doc word的对象信息
   * @param valueParam 需要做输出的信息
   */
  private void addDocTable(XWPFDocument doc, List<ParamBase> valueParam) {

    List<ParamBase> value = new ArrayList<>();
    // 添加列头信息
    ParamBase column = new ParamBase("序号", "参数名", "类型", "为空Y/N", "默认值", "描述");
    value.add(column);
    // 将数据放入到集合中
    value.addAll(valueParam);

    // 创建一个表格
    XWPFTable table = doc.createTable(value.size(), 6);
    // 这里增加的列原本初始化创建的那5行在通过getTableCells()方法获取时获取不到，但通过row新增的就可以。
    // table.addNewCol(); //给表格增加一列，变成6列
    // table.createRow(); // 给表格新增一行，变成6行
    List<XWPFTableRow> rows = table.getRows();
    // 表格属性
    CTTblPr tablePr = table.getCTTbl().addNewTblPr();
    // 表格宽度
    CTTblWidth width = tablePr.addNewTblW();
    width.setW(BigInteger.valueOf(8200));
    XWPFTableRow row;
    List<XWPFTableCell> cells;
    int rowSize = rows.size();

    ParamBase parBean = null;

    for (int i = 0; i < rowSize; i++) {
      row = rows.get(i);
      // 新增单元格
      // row.addNewTableCell();
      // 设置行的高度
      row.setHeight(500);
      // 行属性
      // CTTrPr rowPr = row.getCtRow().addNewTrPr();
      // 这种方式是可以获取到新增的cell的。
      // List<CTTc> list = row.getCtRow().getTcList();
      cells = row.getTableCells();

      if (i < value.size()) {

        parBean = value.get(i);

        // 设置列序号
        setDocTableCellValue(cells, 0, 700, parBean.getParamSeq());
        // 设置参数名
        setDocTableCellValue(cells, 1, 1000, parBean.getParamName());
        // 类型
        setDocTableCellValue(cells, 2, 500, parBean.getParamType());
        // 是否为空
        setDocTableCellValue(cells, 3, 1200, parBean.isNullFlag());
        // 默认值
        setDocTableCellValue(cells, 4, 900, parBean.getDefValue());
        // 描述
        setDocTableCellValue(cells, 5, 3000, parBean.getMsg());
      }
    }
  }

  /**
   * 进行单元格数据的写入
   *
   * @param cells 单元格对象信息
   * @param index 索引编号
   * @param width 宽度
   * @param value 值信息
   */
  private void setDocTableCellValue(List<XWPFTableCell> cells, int index, int width, String value) {
    XWPFTableCell cell = cells.get(index);

    // 单元格属性
    CTTcPr cellPr = cell.getCTTc().addNewTcPr();
    // 设置对方方式
    cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
    // 设置宽度
    cellPr.addNewTcW().setW(BigInteger.valueOf(width));
    cell.setText(value);
  }

  /**
   * 添加正文内容
   *
   * @param doc
   * @param value
   */
  private void addDoc(XWPFDocument doc, String value) {
    // 正文
    XWPFParagraph paraX = doc.createParagraph();
    XWPFRun runX = paraX.createRun();
    // 正文内容
    runX.setText(value);
  }

  /**
   * 添加带格式的文字信息
   *
   * @param doc
   * @param style
   * @param value
   */
  private void addStyleDoc(XWPFDocument doc, String style, String value) {
    // 标题1，1级大纲
    XWPFParagraph titleParagraph = doc.createParagraph();
    // 关键行// 1级大纲
    titleParagraph.setStyle(style);
    XWPFRun run1 = titleParagraph.createRun();
    // 标题内容
    run1.setText(value);
  }
}

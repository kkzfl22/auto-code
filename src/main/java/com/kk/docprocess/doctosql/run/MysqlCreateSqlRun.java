package com.kk.docprocess.doctosql.run;

import java.io.InputStream;
import java.util.List;

import com.kk.docprocess.docCommon.bean.TableBean;
import com.kk.docprocess.docCommon.read.TableReadService;
import com.kk.docprocess.docCommon.read.WordFileReadService;
import com.kk.docprocess.docCommon.read.impl.WordFileReadServiceImpl;
import com.kk.docprocess.docCommon.read.impl.WordTableReadServiceImpl;
import com.kk.docprocess.doctosql.service.tosql.CreateSqlService;
import com.kk.docprocess.doctosql.service.tosql.FileWriteService;
import com.kk.docprocess.doctosql.service.tosql.impl.FileWriteServiceImpl;
import com.kk.docprocess.doctosql.service.tosql.impl.MysqlCreateSqlServiceImpl;
import com.kk.docprocess.doctosql.service.tosql.impl.SqlLiteCreateSqlServiceImpl;

public class MysqlCreateSqlRun {

  public static void main(String[] args) {

    System.out.println(System.currentTimeMillis());

    String path = "autosql/database-15.doc";
    // String pathFile =
    // MysqlCreateSqlRun.class.getClassLoader().getResource(path).getPath();
    // System.out.println("完整的文件路径为:" + pathFile);
    InputStream in = MysqlCreateSqlRun.class.getClassLoader().getResourceAsStream(path);

    // 指定读取格式类,针对hbase做出解析
    // TableReadService tableReadService = new
    // WordHbaseTableReadServiceImpl();
    // 指定读取格式类普通的
    TableReadService tableReadService = new WordTableReadServiceImpl();

    WordFileReadService wordRead = new WordFileReadServiceImpl(tableReadService);

    // 生成sql的类信息
    CreateSqlService createSql = new MysqlCreateSqlServiceImpl(true);
    // CreateSqlService createSql = new SqlLiteCreateSqlServiceImpl(true);

    // 写入文件
    FileWriteService fileWrite = new FileWriteServiceImpl();

    try {
      List<TableBean> list = wordRead.readDocToList(in);

      String sql = createSql.toSqlStr(list);

      String outPath = "D:/java/test/sqllite-a11.sql";

      // 进行文件写入
      fileWrite.wirteFile(outPath, sql);

      System.out.println(sql);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

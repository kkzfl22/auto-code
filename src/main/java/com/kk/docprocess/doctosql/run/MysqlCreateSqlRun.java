package com.kk.docprocess.doctosql.run;

import java.io.File;
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

  public static void main(String[] args) throws Exception {

    System.out.println(System.currentTimeMillis());

    String path = "autosql/database-15.doc";
    String outPath = "D:/java/encode/database/";
    String outMysqlPath = "database-mysql.sql";
    String outSqllitePath = "database-sqllite.sql";

    MysqlCreateSqlRun instance = new MysqlCreateSqlRun();

    // 清理输出文件夹
    instance.cleanPath(outPath);
    // 输出mysql
    instance.outMysql(path, outPath + outMysqlPath);
    // 输出sqllite
    instance.outSqllite(path, outPath + outSqllitePath);

    System.out.println();
  }

  private void cleanPath(String filePath) {
    File file = new File(filePath);

    if (file.exists()) {
      File[] listFile = file.listFiles();
      for (File filteItem : listFile) {
        filteItem.delete();
      }
    }
  }

  /**
   * 进行mysql的相关生成输出
   *
   * @param filePath 文件信息
   * @param outPath 输出路径
   * @throws Exception
   */
  private void outMysql(String filePath, String outPath) throws Exception {
    // String pathFile =
    // MysqlCreateSqlRun.class.getClassLoader().getResource(path).getPath();
    // System.out.println("完整的文件路径为:" + pathFile);
    InputStream in = MysqlCreateSqlRun.class.getClassLoader().getResourceAsStream(filePath);

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

    List<TableBean> list = wordRead.readDocToList(in);

    String sql = createSql.toSqlStr(list);

    // 进行文件输出
    outFile(outPath, sql);
  }

  private void outSqllite(String filePath, String outPath) throws Exception {
    // String pathFile =
    // MysqlCreateSqlRun.class.getClassLoader().getResource(path).getPath();
    InputStream in = MysqlCreateSqlRun.class.getClassLoader().getResourceAsStream(filePath);

    // 指定读取格式类,针对hbase做出解析
    // TableReadService tableReadService = new
    // WordHbaseTableReadServiceImpl();
    // 指定读取格式类普通的
    TableReadService tableReadService = new WordTableReadServiceImpl();

    WordFileReadService wordRead = new WordFileReadServiceImpl(tableReadService);

    // 生成sql的类信息
    CreateSqlService createSql = new SqlLiteCreateSqlServiceImpl(true);

    List<TableBean> list = wordRead.readDocToList(in);

    String sql = createSql.toSqlStr(list);

    // 进行文件输出
    outFile(outPath, sql);
  }

  private void outFile(String outPath, String sql) {
    // 写入文件
    FileWriteService fileWrite = new FileWriteServiceImpl();

    try {

      // 进行文件写入
      fileWrite.wirteFile(outPath, sql);

      System.out.println("文件路径 :" + outPath);
      // System.out.println(sql);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

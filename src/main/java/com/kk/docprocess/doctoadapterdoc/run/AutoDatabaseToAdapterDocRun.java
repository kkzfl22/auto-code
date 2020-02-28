package com.kk.docprocess.doctoadapterdoc.run;

import com.kk.docprocess.docCommon.bean.TableBean;
import com.kk.docprocess.docCommon.database.impl.MysqlTableServiceImpl;
import com.kk.docprocess.docCommon.read.TableReadService;
import com.kk.docprocess.docCommon.read.WordFileReadService;
import com.kk.docprocess.docCommon.read.impl.WordFileReadServiceImpl;
import com.kk.docprocess.docCommon.read.impl.WordTableReadServiceImpl;
import com.kk.docprocess.doctoadapterdoc.bean.AdapterBean;
import com.kk.docprocess.doctoadapterdoc.process.AdapterDocProcess;
import com.kk.docprocess.doctoadapterdoc.process.OutToAdapterDocProc;
import com.kk.docprocess.doctoadapterdoc.process.impl.AdapterDocProcessImpl;
import com.kk.docprocess.doctoadapterdoc.process.impl.OutToAdapterDocProcImpl;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class AutoDatabaseToAdapterDocRun {

  public static void main(String[] args) throws Exception {
    // 1,加载doc文件中的内容

    List<TableBean> list = MysqlTableServiceImpl.INSTANCE.getTableInfo("autocode");

    // 2,进行doc文件的生成
    AdapterDocProcess adapterDocProc = new AdapterDocProcessImpl();

    Map<String, List<AdapterBean>> apapterMap = adapterDocProc.adapterProcess(list);

    // 4,加载doc文件样式信息，进行doc信息的写入
    OutToAdapterDocProc outDoc = new OutToAdapterDocProcImpl();

    String url =
        AutoDatabaseToAdapterDocRun.class.getClassLoader().getResource("doctoadapter").getPath();

    url = url + File.separator + "out-adapter.docx";


    outDoc.outputAdapterDoc(apapterMap, url);
    System.out.println("文件输出地址:" + url);

    System.out.println("运行结束");
  }
}

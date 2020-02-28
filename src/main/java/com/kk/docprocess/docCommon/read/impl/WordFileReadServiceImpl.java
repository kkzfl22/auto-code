package com.kk.docprocess.docCommon.read.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.kk.autocode.util.IOutils;
import com.kk.docprocess.docCommon.bean.TableBean;
import com.kk.docprocess.docCommon.read.TableReadService;
import com.kk.docprocess.docCommon.read.WordFileReadService;

/**
 * word文档中的表格读取程序 
* 源文件名：DocReadServiceImpl.java
* 文件版本：1.0.0
* 创建作者：liujun
* 创建日期：2016年10月26日
* 修改作者：liujun
* 修改日期：2016年10月26日
* 文件描述：TODO
* 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
*/
public class WordFileReadServiceImpl implements WordFileReadService {

    /**
     * 表读取服务 
    * @字段说明 tableReadService
    */
    private TableReadService tableReadService;

    public WordFileReadServiceImpl(TableReadService tableReadService) {
        this.tableReadService = tableReadService;
    }

    @Override
    public List<TableBean> readDocToList(InputStream input) throws Exception {

        List<TableBean> result = new ArrayList<>();

        POIFSFileSystem pfs = null;
        HWPFDocument hwpf = null;
        try {
            pfs = new POIFSFileSystem(input);
            hwpf = new HWPFDocument(pfs);
            // 得到文档的读取范围
            Range range = hwpf.getRange();
            TableIterator it = new TableIterator(range);
            // 迭代文档中的表格
            while (it.hasNext()) {
                Table tb = (Table) it.next();
                if (null != tb) {
                    // 添加数据信息
                    result.add(tableReadService.getTableInfo(tb));
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            IOutils.closeStream(hwpf);
            IOutils.closeStream(pfs);
            IOutils.closeStream(input);
        }

        return result;
    }

}

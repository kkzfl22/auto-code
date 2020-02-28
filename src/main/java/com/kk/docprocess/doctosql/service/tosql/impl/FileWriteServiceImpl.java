package com.kk.docprocess.doctosql.service.tosql.impl;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.kk.autocode.util.IOutils;
import com.kk.docprocess.doctosql.service.tosql.FileWriteService;

/**
 * 文件写入操作
* 源文件名：FileWriteServiceImpl.java
* 文件版本：1.0.0
* 创建作者：liujun
* 创建日期：2016年10月26日
* 修改作者：liujun
* 修改日期：2016年10月26日
* 文件描述：TODO
* 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
*/
public class FileWriteServiceImpl implements FileWriteService {

    @Override
    public void wirteFile(String path, String value) {

        FileOutputStream output = null;
        ByteArrayInputStream readByteStream = null;

        try {
            byte[] writeBuf = new byte[1024];
            output = new FileOutputStream(path);

            readByteStream = new ByteArrayInputStream(value.getBytes());

            int index = -1;

            while ((index = readByteStream.read(writeBuf)) != -1) {
                output.write(writeBuf, 0, index);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOutils.closeStream(readByteStream);
            IOutils.closeStream(output);
        }

    }

}

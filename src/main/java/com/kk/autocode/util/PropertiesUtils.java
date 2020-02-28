package com.kk.autocode.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.kk.autocode.encode.base.TableProcessBase;

/**
 * 属性文件数据获取
* 源文件名：PropertiesUtils.java
* 文件版本：1.0.0
* 创建作者：liujun
* 创建日期：2016年10月9日
* 修改作者：liujun
* 修改日期：2016年10月9日
* 文件描述：TODO
* 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
*/
public class PropertiesUtils {

    /**
     * 属性配制文件信息
    * @字段说明 protoFile
    */
    private static final String protoFile = "/autocode.properties";

    /**
     * 属性文件信息
    * @字段说明 prop
    */
    private Properties prop = new Properties();

    /**
     * 实例对象
    * @字段说明 PROINSTANCE
    */
    private static final PropertiesUtils PROINSTANCE = new PropertiesUtils();

    public PropertiesUtils() {
        // 进行数据加载
        loadProperties();
    }

    /**
     * 获取实例对象
    * 方法描述
    * @return
    * @创建日期 2016年10月9日
    */
    public static PropertiesUtils getInstance() {
        return PROINSTANCE;
    }

    /**
     * 得到测试的属性文件信息
    * 方法描述
    * @return
    * @创建日期 2016年9月28日
    */
    public void loadProperties() {
        // 仅加载一次
        if (prop.isEmpty()) {
            InputStream in = null;
            String config = protoFile;
            try {
                in = TableProcessBase.class.getResourceAsStream(config);
                if (in == null) {
                    in = TableProcessBase.class.getClassLoader().getResourceAsStream(config);
                }
                if (in == null) {
                    in = Thread.currentThread().getContextClassLoader().getResourceAsStream(config);
                }
                prop.load(in);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOutils.closeStream(in);
            }
        }
    }

    /**
     * 获得属性文件信息
    * 方法描述
    * @return
    * @创建日期 2016年10月9日
    */
    public Properties getProperties() {
        return prop;
    }

    /**
     * 通过key获取Value
    * 方法描述
    * @param key
    * @return
    * @创建日期 2016年10月9日
    */
    public String getValue(String key) {
        return prop.getProperty(key);
    }

}

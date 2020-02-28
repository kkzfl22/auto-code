package com.kk.autocode.console;

/**
 * 系统配制枚举类
* 源文件名：ConfigEnum.java
* 文件版本：1.0.0
* 创建作者：liujun
* 创建日期：2016年10月8日
* 修改作者：liujun
* 修改日期：2016年10月8日
* 文件描述：TODO
* 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
*/
public enum ConfigEnum {


    /**
     * oracle数据库
    * @字段说明 DB_TYPE_ORACLE
    */
    DB_TYPE_ORACLE("1", "oracle数据库"),

    /**
     * 支持mysql与oracle的代码的生成
    * @字段说明 DB_TYPE_MYSQL
    */
    DB_TYPE_MYSQL("2", "mysql数据库"),

    ;

    /**
     * 数据库的类型
    * @字段说明 dbtype
    */
    private String key;

    /**
     * 值信息
    * @字段说明 value
    */
    private String value;

    private ConfigEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

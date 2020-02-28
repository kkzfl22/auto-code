package com.kk.autocode.util;

public class DbUtils {

    /**
     * 进行关闭操作
    * 方法描述
    * @param close
    * @创建日期 2016年9月28日
    */
    public static void close(AutoCloseable close) {
        if (null != close) {
            try {
                close.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

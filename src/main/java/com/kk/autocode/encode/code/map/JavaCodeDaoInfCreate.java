package com.kk.autocode.encode.code.map;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kk.autocode.encode.base.TableProcessBase;
import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.autocode.encode.code.AutoCodeInf;
import com.kk.autocode.util.DateUtils;

/**
 * 生成dao的java代码
* 源文件名：JavaCodeMian.java
* 文件版本：1.0.0
* 创建作者：liujun
* 创建日期：2016年9月12日
* 修改作者：liujun
* 修改日期：2016年9月12日
* 文件描述：TODO
* 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
*/
public class JavaCodeDaoInfCreate extends TableProcessBase implements AutoCodeInf {

    public static void main(String[] args) throws Exception {
        JavaCodeDaoInfCreate daoInf = new JavaCodeDaoInfCreate();

        args = new String[3];

        args[0] = "D:/java/encode/a10/javaMapDAO/";
        args[1] = "com.a10.resource.phone.";
        args[2] = "a10";

        daoInf.encodeDaoInf(args[0], args[1], args[2]);
    }

    /**
     * 进行dao的接口代码的生成
    * 方法描述
    * @param path
    * @param packageStr
    * @param tableSpaceName
    * @throws Exception
    * @创建日期 2016年10月8日
    */
    private void encodeDaoInf(String path, String basePackageStr, String tableSpaceName) throws Exception {

        File dirFile = new File(path);
        // 如果文件夹存在，则执行删除
        boolean exists = dirFile.exists();
        if (exists) {
            dirFile.delete();
        }

        dirFile.mkdirs();

        Map<String, List<TableColumnDTO>> map = this.getTableColumnInfoByBean(tableSpaceName);

        // Set<String> tableNames = map.keySet();
        Iterator<Entry<String, List<TableColumnDTO>>> tableNameEntry = map.entrySet().iterator();

        while (tableNameEntry.hasNext()) {
            Entry<String, List<TableColumnDTO>> tableName = tableNameEntry.next();

            StringBuffer sb = new StringBuffer();

            String tableClassName = toJavaClassName(tableName.getKey());

            String serviceName = tableClassName;
            String className = serviceName + "DAO";

            sb.append("package ").append(basePackageStr).append("dao").append(";").append(NEXT_LINE);

            sb.append(NEXT_LINE);
            sb.append("import java.util.Map;").append(NEXT_LINE);
            sb.append("import java.util.List;").append(NEXT_LINE);

            sb.append(NEXT_LINE);
            sb.append("import ").append(COMM_SPIT_BEAN).append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 添加类注释信息
            sb.append("/**").append(NEXT_LINE);
            sb.append("*").append(tableName.getKey()).append("表的数据库操作").append(NEXT_LINE);
            sb.append("* 文件版本：1.0.0").append(NEXT_LINE);
            sb.append("* 创建作者：liujun").append(NEXT_LINE);
            sb.append("* 创建日期：").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append("*/").append(NEXT_LINE);
            sb.append("public interface " + className + " {" + NEXT_LINE);
            sb.append(NEXT_LINE);

            // 添加insert方法
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("表的添加操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param param 参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @exception 异常信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("public int insert(Map<String,Object> param)throws Exception ;");

            sb.append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 修改
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("表的修改操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param param 参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @exception 异常信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("public int update(Map<String,Object> param)throws Exception ;");

            sb.append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 删除
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("表的删除操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param param 参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @exception 异常信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("public int delete(Map<String,Object> param)throws Exception ;");

            sb.append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 普通查询查询
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("表的查询操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param param 参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @exception 异常信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1))
                    .append("public List<Map<String,Object>> query(Map<String,Object> param)throws Exception ;");
            sb.append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 分页查询统计
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("表的分页查询操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param param 参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @exception 异常信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("public Pagination<Map<String,Object>> queryPage");
            sb.append("(Map<String,Object> param,int pageNum, int pageSize)throws Exception ;");
            sb.append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 结束
            sb.append("}");
            FileWriter fw = new FileWriter(new File(path + className + ".java"));
            fw.write(sb.toString());
            fw.close();
        }

    }

    @Override
    public void autoCode(CreateParamBean param) {
        // 进行自动代码生成
        try {
            String daoInfPath = param.getFileBasePath() + "javaMapDAO/";
            String javaPackage = param.getJavaPackage();
            this.encodeDaoInf(daoInfPath, javaPackage, param.getTableSpaceName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

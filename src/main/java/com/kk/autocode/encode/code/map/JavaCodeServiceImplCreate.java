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
public class JavaCodeServiceImplCreate extends TableProcessBase implements AutoCodeInf {

    public static void main(String[] args) throws Exception {
        JavaCodeServiceImplCreate serviceImplInstance = new JavaCodeServiceImplCreate();

        args = new String[3];

        args[0] = "D:/java/encode/a10/javaMapServiceImpl/";
        args[1] = "com.a10.resource.phone.";
        args[2] = "a10";

        serviceImplInstance.encodeServiceImpl(args[0], args[1], args[2]);

    }

    private void encodeServiceImpl(String path, String basePackageStr, String tableSpace) throws Exception {

        File dirFile = new File(path);
        // 如果文件夹存在，则执行删除
        boolean exists = dirFile.exists();
        if (exists) {
            dirFile.delete();
        }

        dirFile.mkdirs();

        Map<String, List<TableColumnDTO>> map = this.getTableColumnInfoByBean(tableSpace);

        // Set<String> tableNames = map.keySet();
        Iterator<Entry<String, List<TableColumnDTO>>> tableNameEntry = map.entrySet().iterator();

        while (tableNameEntry.hasNext()) {
            Entry<String, List<TableColumnDTO>> tableName = tableNameEntry.next();

            StringBuffer sb = new StringBuffer();

            String tableClassName = toJavaClassName(tableName.getKey());

            String serviceName = tableClassName;
            String className = serviceName + "ServiceImpl";

            String importServiceBean = basePackageStr + "service." + serviceName + "Service";
            String importDaoBean = basePackageStr + "dao." + serviceName + "DAO";

            String daoInf = serviceName + "DAO";

            sb.append("package ").append(basePackageStr).append("service.impl").append(";").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            sb.append("import java.util.Map;").append(NEXT_LINE);
            sb.append("import java.util.List;").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            sb.append("import org.springframework.stereotype.Service;").append(NEXT_LINE);
            sb.append("import org.springframework.beans.factory.annotation.Autowired;").append(NEXT_LINE);
            sb.append("import org.apache.log4j.Logger;").append(NEXT_LINE);

            sb.append(NEXT_LINE);

            sb.append("import ").append(importDaoBean).append(";").append(NEXT_LINE);
            sb.append("import ").append(importServiceBean).append(";").append(NEXT_LINE);
            sb.append("import ").append(COMM_SPIT_BEAN).append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 添加类注释信息
            sb.append("/**").append(NEXT_LINE);
            sb.append("*").append(tableName.getKey()).append("表的业务操作实现").append(NEXT_LINE);
            sb.append("* 文件版本：1.0.0").append(NEXT_LINE);
            sb.append("* 创建作者：liujun").append(NEXT_LINE);
            sb.append("* 创建日期：").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append("*/").append(NEXT_LINE);
            sb.append("@Service").append(NEXT_LINE);
            sb.append("public class " + className + "  implements " + serviceName + "Service {");
            sb.append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 数据库的引用
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" *引用数据库操作dao").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@Autowired").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("private ").append(daoInf).append(" daoInf;").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 添加日志引入
            sb.append(formatMsg(1)).append("private Logger log = Logger.getLogger(").append(className)
                    .append(".class);").append(NEXT_LINE);
            sb.append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 添加insert方法
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("业务的添加操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param param 参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @return boolean true:添加成功,false：添加失败").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("public boolean insert(Map<String,Object> param) {").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className).append(" insert param:\"+param); ")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("int result = -1;").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("try{").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("result = daoInf.insert(param);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("} catch(Exception e){").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("log.info(\"" + className + " insert Exception:\", e);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className);
            sb.append(" insert param:\"+param+\",result true\");").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("if(result != -1){").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("return true;").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("return false;").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 修改
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("表的修改操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param param 参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @return boolean true:添加成功,false：添加失败").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("public boolean update(Map<String,Object> param) {").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className).append(" update param:\"+param); ")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("int result = -1;").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("try{").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("result = daoInf.update(param);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("} catch(Exception e){").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("log.info(\"" + className + " update Exception:\", e);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className);
            sb.append(" update param:\"+param+\",result \"+result+\" \");").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("if(result != -1){").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("return true;").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("return false;").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 删除
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("表的删除操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param param 参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @return boolean true:添加成功,false：添加失败").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("public boolean delete(Map<String,Object> param) {").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className).append(" delete param:\"+param); ")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("int result = -1;").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("try{").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("result = daoInf.delete(param);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("} catch(Exception e){").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("log.info(\"" + className + " delete Exception:\", e);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className);
            sb.append(" delete param:\"+param+\",result \"+result+\" \");").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("if(result != -1){").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("return true;").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("return false;").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 查询
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("表的查询操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param param 参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @return List<Map<String,Object>> 查询的结果信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("public List<Map<String,Object>> query(Map<String,Object> param) {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className).append(" query param:\"+param); ")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("List<Map<String,Object>> list = null;").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("try{").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("list = daoInf.query(param);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("} catch (Exception e) {").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("log.error(\"" + className + " query Exception:\", e);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className)
                    .append(" query param:\"+param+\",result true\");").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("return list;").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 进行分页查询操作
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("表的分页查询操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param param 页面信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param pageNum 页码").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param pageSize 页面大小").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("public Pagination<Map<String,Object>> queryPage");
            sb.append("(Map<String,Object> param,int pageNum, int pageSize) {");
            sb.append(NEXT_LINE);

            sb.append(formatMsg(2)).append("log.info(\"").append(className).append(" queryPage param:\"+param); ")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("Pagination<Map<String,Object>> list = null;").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("try{").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("list = daoInf.queryPage(param,pageNum,pageSize);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("} catch (Exception e) {").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("log.error(\"" + className + " queryPage Exception:\", e);")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className)
                    .append(" queryPage param:\"+param+\",result true\");").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("return list;").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
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
            String daoInfPath = param.getFileBasePath() + "javaMapServiceImpl/";
            String javaPackage = param.getJavaPackage();
            this.encodeServiceImpl(daoInfPath, javaPackage, param.getTableSpaceName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

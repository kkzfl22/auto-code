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
 * 生成action的java代码
* 源文件名：JavaCodeMian.java
* 文件版本：1.0.0
* 创建作者：liujun
* 创建日期：2016年9月12日
* 修改作者：liujun
* 修改日期：2016年9月12日
* 文件描述：TODO
* 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
*/
public class JavaCodeActionCreate extends TableProcessBase implements AutoCodeInf {

    public static void main(String[] args) throws Exception {
        JavaCodeActionCreate serviceImplInstance = new JavaCodeActionCreate();

        args = new String[3];

        args[0] = "D:/java/encode/a10/javaMapAction/";
        args[1] = "com.a10.resource.phone.";
        args[2] = "a10";

        serviceImplInstance.encodeActionImpl(args[0], args[1], args[2]);

    }

    private void encodeActionImpl(String path, String basePackageStr, String tableSpace) throws Exception {

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
            // 获取列描述信息
            List<TableColumnDTO> columnList = tableName.getValue();

            StringBuffer sb = new StringBuffer();

            String tableClassName = toJavaClassName(tableName.getKey());

            String serviceName = tableClassName;
            String className = serviceName + "Controller";

            String serviceInf = serviceName + "Service";

            String importServiceBean = basePackageStr + "service." + serviceName + "Service";

            sb.append("package ").append(basePackageStr).append("action").append(";").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            sb.append("import java.util.Map;").append(NEXT_LINE);
            sb.append("import java.util.HashMap;").append(NEXT_LINE);
            sb.append("import java.util.List;").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            sb.append("import javax.servlet.http.HttpServletRequest;").append(NEXT_LINE);
            sb.append("import javax.servlet.http.HttpServletResponse;").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            sb.append("import org.apache.log4j.Logger;").append(NEXT_LINE);
            sb.append("import org.springframework.beans.factory.annotation.Autowired;").append(NEXT_LINE);
            sb.append("import org.springframework.context.annotation.Scope;").append(NEXT_LINE);
            sb.append("import org.springframework.stereotype.Controller;").append(NEXT_LINE);
            sb.append("import org.springframework.web.bind.annotation.RequestMapping;").append(NEXT_LINE);
            sb.append(NEXT_LINE);
            sb.append("import ").append(COMM_SPIT_BEAN).append(NEXT_LINE);
            sb.append("import ").append(importServiceBean).append(";").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 添加类注释信息
            sb.append("/**").append(NEXT_LINE);
            sb.append("*").append(tableName.getKey()).append("表的controller").append(NEXT_LINE);
            sb.append("* 文件版本：1.0.0").append(NEXT_LINE);
            sb.append("* 创建作者：liujun").append(NEXT_LINE);
            sb.append("* 创建日期：").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append("*/").append(NEXT_LINE);
            sb.append("@Controller").append(NEXT_LINE);
            sb.append("@RequestMapping(value = \"/").append(toActionStr(tableClassName)).append("Action");
            sb.append("\")").append(NEXT_LINE);
            sb.append("@Scope(\"prototype\")").append(NEXT_LINE);
            sb.append("public class " + className + "  {");
            sb.append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 数据库的引用
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" *引用服务").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@Autowired").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("private ").append(serviceInf).append(" serviceInf;").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 添加日志引入
            sb.append(formatMsg(1)).append("private Logger log = Logger.getLogger(").append(className)
                    .append(".class);").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 添加insert方法
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("业务的添加操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param request 请求信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param response 响应").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("insert").append(".action\")")
                    .append(NEXT_LINE);
            sb.append(formatMsg(1))
                    .append("public void insert(HttpServletRequest request, HttpServletResponse response) {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("Map<String, Object> paramMap = getParamMap(request);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("if (!paramMap.isEmpty()) {").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("//调用添加操作").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("boolean result = serviceInf.insert(paramMap);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("//进行结果的处理").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 修改
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("业务的修改操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param request 请求信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param response 响应").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("update").append(".action\")")
                    .append(NEXT_LINE);
            sb.append(formatMsg(1))
                    .append("public void update(HttpServletRequest request, HttpServletResponse response) {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("Map<String, Object> paramMap = getParamMap(request);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("if (!paramMap.isEmpty()) {").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("//调用修改操作").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("boolean result = serviceInf.update(paramMap);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("//进行结果的处理").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 删除
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("业务的删除操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param request 请求信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param response 响应").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("delete").append(".action\")")
                    .append(NEXT_LINE);
            sb.append(formatMsg(1))
                    .append("public void delete(HttpServletRequest request, HttpServletResponse response) {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("Map<String, Object> paramMap = getParamMap(request);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("if (!paramMap.isEmpty()) {").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("//调用删除操作").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("boolean result = serviceInf.delete(paramMap);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("//进行结果的处理").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 查询
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("业务的查询操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param request 请求信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param response 响应").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("query").append(".action\")")
                    .append(NEXT_LINE);
            sb.append(formatMsg(1))
                    .append("public void query(HttpServletRequest request, HttpServletResponse response) {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("Map<String, Object> paramMap = getParamMap(request);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("if (!paramMap.isEmpty()) {").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("//调用查询操作").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("List<Map<String, Object>> result = serviceInf.query(paramMap);")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("//进行结果的处理").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 进行分页查询操作
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*").append(tableName.getKey()).append("业务的分页查询操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param request 请求信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @param response 响应").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("* @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("queryPage").append(".action\")")
                    .append(NEXT_LINE);
            sb.append(formatMsg(1))
                    .append("public void queryPage(HttpServletRequest request, HttpServletResponse response) {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("Map<String, Object> paramMap = getParamMap(request);").append(NEXT_LINE);

            sb.append(formatMsg(2)).append("if (!paramMap.isEmpty()) {").append(NEXT_LINE);
            // 获取当前页
            sb.append(formatMsg(3)).append("int pageNum = 1;").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("if (null != request.getParameter(\"pageNum\")) {").append(NEXT_LINE);
            sb.append(formatMsg(4)).append("pageNum = Integer.parseInt(request.getParameter(\"pageNum\")); ")
                    .append(NEXT_LINE);
            sb.append(formatMsg(3)).append("}").append(NEXT_LINE);

            // 每页显示的条数
            sb.append(formatMsg(3)).append("int pageSize = 1;").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("if (null != request.getParameter(\"pageSize\")) {").append(NEXT_LINE);
            sb.append(formatMsg(4)).append("pageSize = Integer.parseInt(request.getParameter(\"pageSize\")); ")
                    .append(NEXT_LINE);
            sb.append(formatMsg(3)).append("}").append(NEXT_LINE);

            sb.append(formatMsg(3)).append("//调用查询操作").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("Pagination<Map<String, Object>> result = ");
            sb.append("serviceInf.queryPage(paramMap,pageNum,pageSize);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("//进行结果的处理").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE).append(NEXT_LINE);
            sb.append(NEXT_LINE);

            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * 从request中获取参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @param request 请求信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @return map参数").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" *  @创建日期:").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(formatMsg(1)).append("*/").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("private Map<String, Object> getParamMap(HttpServletRequest request) {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("Map<String, Object> param = new HashMap<String, Object>();")
                    .append(NEXT_LINE);

            for (int i = 0; i < columnList.size(); i++) {
                TableColumnDTO tableBean = columnList.get(i);
                String javaName = toJava(tableBean.getColumnName());
                // 添加当前的属性值的注释
                sb.append(formatMsg(2)).append("//").append(tableBean.getColumnMsg()).append(NEXT_LINE);
                // 生成代码
                sb.append(formatMsg(2)).append("if (null != request.getParameter(\"").append(javaName).append("\")) {")
                        .append(NEXT_LINE);
                sb.append(formatMsg(3)).append(" param.put(\"").append(javaName).append("\",");
                sb.append("request.getParameter(\"").append(javaName).append("\"));").append(NEXT_LINE);
                sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
                sb.append(NEXT_LINE);
            }

            sb.append(formatMsg(2)).append("return param;").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);

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
            String daoInfPath = param.getFileBasePath() + "javaMapAction/";
            String javaPackage = param.getJavaPackage();
            this.encodeActionImpl(daoInfPath, javaPackage, param.getTableSpaceName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

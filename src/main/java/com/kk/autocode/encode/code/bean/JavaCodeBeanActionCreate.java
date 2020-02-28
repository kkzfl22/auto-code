package com.kk.autocode.encode.code.bean;

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
 * @since 2018年4月15日 下午3:45:25
 * @version 0.0.1
 * @author liujun
 */
public class JavaCodeBeanActionCreate extends TableProcessBase implements AutoCodeInf {

    public static void main(String[] args) throws Exception {
        JavaCodeBeanActionCreate serviceImplInstance = new JavaCodeBeanActionCreate();

        args = new String[3];

        args[0] = "D:/java/encode/a10/javaBeanAction/";
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

            StringBuilder sb = new StringBuilder();

            String tableClassName = toJavaClassName(tableName.getKey());

            String serviceName = tableClassName;
            String className = serviceName + "Controller";

            String serviceInf = serviceName + "Service";

            String importBean = basePackageStr + "pojo." + tableClassName + "DTO";

            String beanParam = tableClassName + "DTO";

            String packageStr = basePackageStr + "action";

            sb.append("package ").append(packageStr).append(";").append(NEXT_LINE);
            sb.append(NEXT_LINE);

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
            sb.append("import ").append(importBean).append(";").append(NEXT_LINE);
            sb.append("import ").append(COMM_SPIT_BEAN).append(NEXT_LINE);
            // 引入service接口
            sb.append("import ").append(basePackageStr).append("service.").append(tableClassName).append("Service;");
            sb.append(NEXT_LINE);
            sb.append(NEXT_LINE);
            // 添加类注释信息
            sb.append("/**").append(NEXT_LINE);
            sb.append(" * ").append(tableName.getKey()).append("表的controller").append(NEXT_LINE);
            sb.append(" * @version 1.0.0").append(NEXT_LINE);
			sb.append(" * @author liujun").append(NEXT_LINE);
			sb.append(" * @date ").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(" */").append(NEXT_LINE);
            sb.append("@Controller").append(NEXT_LINE);
            sb.append("@RequestMapping(value = \"/").append(toActionStr(tableClassName)).append("ActionWeb");
            sb.append("\")").append(NEXT_LINE);
            sb.append("@Scope(\"prototype\")").append(NEXT_LINE);
            sb.append("public class " + className + "  {");
            sb.append(NEXT_LINE);
            sb.append(" " + NEXT_LINE);
            sb.append(" " + NEXT_LINE);

            // 数据库的引用
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * 引用服务").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@Autowired").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("private ").append(serviceInf).append(" serviceInf;").append(NEXT_LINE);
            sb.append(" " + NEXT_LINE);

            // 添加日志引入
            sb.append(formatMsg(1)).append("private Logger log = Logger.getLogger(").append(className)
                    .append(".class);").append(NEXT_LINE);
            sb.append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 添加insert方法
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * ").append(tableName.getKey()).append("业务的添加操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @param request 请求信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @param response 响应").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("insert").append(".action\")")
                    .append(NEXT_LINE);
            sb.append(formatMsg(1))
                    .append("public void insert(HttpServletRequest request, HttpServletResponse response) {")
                    .append(NEXT_LINE);

            sb.append(formatMsg(2)).append("" + beanParam + " reqBean = getParamBean(request);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("if (reqBean != null ) {").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("//调用添加操作").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("boolean result = serviceInf.insert(reqBean);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("//进行结果的处理").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 修改
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * ").append(tableName.getKey()).append("业务的修改操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @param request 请求信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @param response 响应").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("update").append(".action\")")
                    .append(NEXT_LINE);
            sb.append(formatMsg(1))
                    .append("public void update(HttpServletRequest request, HttpServletResponse response) {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("" + beanParam + " reqBean = getParamBean(request);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("if (null != reqBean) {").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("//调用修改操作").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("boolean result = serviceInf.update(reqBean);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("//进行结果的处理").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 删除
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * ").append(tableName.getKey()).append("业务的删除操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @param request 请求信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @param response 响应").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("delete").append(".action\")")
                    .append(NEXT_LINE);
            sb.append(formatMsg(1))
                    .append("public void delete(HttpServletRequest request, HttpServletResponse response) {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("" + beanParam + " reqBean = getParamBean(request);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("if (null != reqBean) {").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("//调用删除操作").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("boolean result = serviceInf.delete(reqBean);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("//进行结果的处理").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 查询
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * ").append(tableName.getKey()).append("业务的查询操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @param request 请求信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @param response 响应").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("query").append(".action\")")
                    .append(NEXT_LINE);
            sb.append(formatMsg(1))
                    .append("public void query(HttpServletRequest request, HttpServletResponse response) {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("" + beanParam + " reqBean = getParamBean(request);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("if (null != reqBean) {").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("//调用查询操作").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("List<" + beanParam + "> result = serviceInf.query(reqBean);")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("//进行结果的处理").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(" " + NEXT_LINE);

            // 进行分页查询操作
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * ").append(tableName.getKey()).append("业务的删除操作").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @param request 请求信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @param response 响应").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("queryPage").append(".action\")")
                    .append(NEXT_LINE);
            sb.append(formatMsg(1))
                    .append("public void queryPage(HttpServletRequest request, HttpServletResponse response) {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("" + beanParam + " reqBean = getParamBean(request);").append(NEXT_LINE);

            sb.append(formatMsg(2)).append("int pageNum = 1;").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("if (null != request.getParameter(\"pageNum\")) {").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("pageNum = Integer.parseInt(request.getParameter(\"pageNum\")); ")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);

            // 每页显示的条数
            sb.append(formatMsg(2)).append("int pageSize = 1;").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("if (null != request.getParameter(\"pageSize\")) {").append(NEXT_LINE);
            sb.append(formatMsg(3)).append("pageSize = Integer.parseInt(request.getParameter(\"pageSize\")); ")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("}").append(NEXT_LINE);

            sb.append(formatMsg(2)).append("//调用查询操作").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("Pagination<" + beanParam + "> result = ")
                    .append("serviceInf.queryPage(reqBean,pageNum,pageSize);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("//进行结果的处理").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE).append(NEXT_LINE);
            sb.append(NEXT_LINE);

            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * 从request中获取参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @param request 请求信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @return map参数").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("private " + beanParam + " getParamBean(HttpServletRequest request) {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append(beanParam).append(" paramBean=new ").append(beanParam).append("();")
                    .append(NEXT_LINE);

            for (int i = 0; i < columnList.size(); i++) {
                TableColumnDTO tableBean = columnList.get(i);
                String javaName = toJava(tableBean.getColumnName());
                // 添加当前的属性值的注释
                sb.append(formatMsg(2)).append("//").append(tableBean.getColumnMsg()).append(NEXT_LINE);
                // 生成代码
                sb.append(formatMsg(2)).append("if (null != request.getParameter(\"").append(javaName).append("\")) {")
                        .append(NEXT_LINE);
                sb.append(formatMsg(3)).append(" paramBean.set").append(this.toProJavaName(tableBean.getColumnName()))
                        .append("(");
                // 进行参数转化
                this.parseParamValue(tableBean, javaName, sb);
                sb.append(");").append(NEXT_LINE);
                sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
                sb.append(formatMsg(2)).append(NEXT_LINE);
            }

            sb.append(formatMsg(2)).append("return paramBean;").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);

            // 结束
            sb.append("}");
            FileWriter fw = new FileWriter(new File(path + className + ".java"));
            fw.write(sb.toString());
            fw.close();
        }

    }

    /**
     * 转换参数信息
    * 方法描述
    * @param tableBean
    * @param javaName
    * @param sb
    * @创建日期 2016年10月12日
    */
    private void parseParamValue(TableColumnDTO tableBean, String javaName, StringBuilder sb) {

        // 得到数据库对应的java类型
        String javaType = this.getJavaType(tableBean);

        switch (javaType) {
        case "int":
            sb.append("Integer.parseInt(request.getParameter(\"").append(javaName).append("\"))");
            break;
        case "long":
            sb.append("Long.parseLong(request.getParameter(\"").append(javaName).append("\"))");
            break;
        case "float" :
        	 sb.append("Float.parseFloat(request.getParameter(\"").append(javaName).append("\"))");
        	break;
        case "double" :
        	sb.append("Double.parseDouble(request.getParameter(\"").append(javaName).append("\"))");
        	break;
        default:
            sb.append("request.getParameter(\"").append(javaName).append("\")");
            break;
        }

    }

    @Override
    public void autoCode(CreateParamBean param) {

        // 进行自动代码生成
        try {
            String daoInfPath = param.getFileBasePath() + "javaBeanAction/";
            String javaPackage = param.getJavaPackage();
            this.encodeActionImpl(daoInfPath, javaPackage, param.getTableSpaceName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

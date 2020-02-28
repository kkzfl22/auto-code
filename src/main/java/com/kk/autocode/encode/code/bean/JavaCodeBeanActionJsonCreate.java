package com.kk.autocode.encode.code.bean;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kk.autocode.encode.base.TableProcessBase;
import com.kk.autocode.encode.bean.CreateParamBean;
import com.kk.autocode.encode.bean.EncodeContext;
import com.kk.element.database.mysql.pojo.TableColumnDTO;
import com.kk.element.database.mysql.pojo.TableInfoDTO;
import com.kk.autocode.encode.code.AutoCodeInf;
import com.kk.autocode.util.DateUtils;

/**
 * 生成json格式的action
 * 
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月30日 上午11:22:24
 */
public class JavaCodeBeanActionJsonCreate extends TableProcessBase implements AutoCodeInf {

	public static void main(String[] args) throws Exception {

		args = new String[3];

		args[0] = "D:/java/encode/a10/javaBeanAction/";
		args[1] = "com.a10.resource.phone.";
		args[2] = "a10";

		JavaCodeBeanActionJsonCreate serviceImplInstance = new JavaCodeBeanActionJsonCreate();

		Map<String, List<TableColumnDTO>> map = serviceImplInstance.getTableColumnInfoByBean(args[2]);

		Map<String, TableInfoDTO> tableMap = serviceImplInstance.getTableInfoByBean(args[2]);

		EncodeContext context = new EncodeContext();

		context.setColumnMap(map);
		context.setTableMap(tableMap);

		serviceImplInstance.encodeActionImpl(args[0], args[1], args[2], context);

	}

	private void encodeActionImpl(String path, String basePackageStr, String tableSpace, EncodeContext context)
			throws Exception {

		File dirFile = new File(path);
		// 如果文件夹存在，则执行删除
		boolean exists = dirFile.exists();
		if (exists) {
			dirFile.delete();
		}

		dirFile.mkdirs();

		Map<String, List<TableColumnDTO>> map = context.getColumnMap();

		// Set<String> tableNames = map.keySet();
		Iterator<Entry<String, List<TableColumnDTO>>> tableNameEntry = map.entrySet().iterator();

		while (tableNameEntry.hasNext()) {
			Entry<String, List<TableColumnDTO>> tableName = tableNameEntry.next();
			// 获取列描述信息
			// List<TableColumnDTO> columnList = tableName.getValue();

			Map<String, TableInfoDTO> tableMap = context.getTableMap();

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

			sb.append("import org.springframework.beans.factory.annotation.Autowired;").append(NEXT_LINE);
			sb.append("import org.springframework.context.annotation.Scope;").append(NEXT_LINE);
			sb.append("import org.springframework.stereotype.Controller;").append(NEXT_LINE);
			sb.append("import org.springframework.web.bind.annotation.RequestBody;").append(NEXT_LINE);
			sb.append("import org.springframework.web.bind.annotation.RequestMapping;").append(NEXT_LINE);

			sb.append(NEXT_LINE);
			sb.append("import ").append(importBean).append(";").append(NEXT_LINE);
			sb.append("import ").append(COMM_SPIT_BEAN).append(NEXT_LINE);
			// 引入service接口
			sb.append("import ").append(basePackageStr).append("service.").append(tableClassName).append("Service;");
			sb.append(NEXT_LINE);
			sb.append(NEXT_LINE);

			TableInfoDTO tableMsgBean = tableMap.get(tableName.getKey());

			String tableMsg = tableMsgBean.getTableComment() + "(" + tableMsgBean.getTableName() + ")";

			// 添加类注释信息
			sb.append("/**").append(NEXT_LINE);
			sb.append(" * ").append(tableMsg).append("控制层").append(NEXT_LINE);
			sb.append(" * @version 1.0.0").append(NEXT_LINE);
			sb.append(" * @author liujun").append(NEXT_LINE);
			sb.append(" * @date ").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
			sb.append(" */").append(NEXT_LINE);
			sb.append("@Controller").append(NEXT_LINE);
			sb.append("@RequestMapping(value = \"/").append(toActionStr(tableClassName)).append("ActionWeb");
			sb.append("\")").append(NEXT_LINE);
			sb.append("@Scope(\"prototype\")").append(NEXT_LINE);
			sb.append("public class " + className + "  extends BaseMethods {");
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

			sb.append(NEXT_LINE);

			// 添加insert方法
			sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * ").append(tableMsg).append("添加操作").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * @param request 请求信息").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * @param response 响应").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
			sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("insert").append(".action\")")
					.append(NEXT_LINE);
			sb.append(formatMsg(1)).append("public void insert(@RequestBody ").append(beanParam).append(" reqBean,")
					.append("HttpServletRequest request, HttpServletResponse response) {").append(NEXT_LINE);

			sb.append(formatMsg(2)).append("boolean result = false;").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("if (reqBean != null ) {").append(NEXT_LINE);
			sb.append(formatMsg(3)).append("//调用添加操作").append(NEXT_LINE);
			sb.append(formatMsg(3)).append("result = serviceInf.insert(reqBean);").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("//进行结果的处理").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("String rspJsonMsg = setResultForString(result);").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("this.writeResponse(response, rspJsonMsg, null);").append(NEXT_LINE);
			sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
			sb.append(NEXT_LINE);

			// 修改
			sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * ").append(tableMsg).append("修改操作").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * @param request 请求信息").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * @param response 响应").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
			sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("update").append(".action\")")
					.append(NEXT_LINE);
			sb.append(formatMsg(1)).append("public void update(@RequestBody ").append(beanParam).append(" reqBean,")
					.append("HttpServletRequest request, HttpServletResponse response) {").append(NEXT_LINE);

			sb.append(formatMsg(2)).append("boolean result = false;").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("if (null != reqBean) {").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("//调用修改操作").append(NEXT_LINE);
			sb.append(formatMsg(3)).append("result = serviceInf.update(reqBean);").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("//进行结果的处理").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("String rspJsonMsg = setResultForString(result);").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("this.writeResponse(response, rspJsonMsg, null);").append(NEXT_LINE);
			sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
			sb.append(NEXT_LINE);

			// 删除
			sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * ").append(tableMsg).append("删除操作").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * @param request 请求信息").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * @param response 响应").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
			sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("delete").append(".action\")")
					.append(NEXT_LINE);
			sb.append(formatMsg(1)).append("public void delete(@RequestBody ").append(beanParam).append(" reqBean,")
					.append("HttpServletRequest request, HttpServletResponse response) {").append(NEXT_LINE);

			sb.append(formatMsg(2)).append("boolean result = false;").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("if (null != reqBean) {").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("//调用删除操作").append(NEXT_LINE);
			sb.append(formatMsg(3)).append("result = serviceInf.delete(reqBean);").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("//进行结果的处理").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("String rspJsonMsg = setResultForString(result);").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("this.writeResponse(response, rspJsonMsg, null);").append(NEXT_LINE);
			sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
			sb.append(NEXT_LINE);

			// 查询
			sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * ").append(tableMsg).append("查询操作").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * @param request 请求信息").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * @param response 响应").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
			sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("query").append(".action\")")
					.append(NEXT_LINE);
			sb.append(formatMsg(1)).append("public void query(@RequestBody ").append(beanParam).append(" reqBean,")
					.append("HttpServletRequest request, HttpServletResponse response) {").append(NEXT_LINE);

			sb.append(formatMsg(2)).append("List<" + beanParam + "> result = null;").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("if (null != reqBean) {").append(NEXT_LINE);
			sb.append(formatMsg(3)).append("//调用查询操作").append(NEXT_LINE);
			sb.append(formatMsg(3)).append("result = serviceInf.query(reqBean);").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("}").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("//进行结果的处理").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("String rspJsonMsg = setSuccessResultForString(result);").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("this.writeResponse(response, rspJsonMsg, null);").append(NEXT_LINE);
			sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
			sb.append(" " + NEXT_LINE);

			// 进行分页查询操作
			sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * ").append(tableMsg).append("查询分页").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * @param request 请求信息").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" * @param response 响应").append(NEXT_LINE);
			sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
			sb.append(formatMsg(1)).append("@RequestMapping(value = \"/").append("queryPage").append(".action\")")
					.append(NEXT_LINE);
			sb.append(formatMsg(1)).append("public void queryPage(@RequestBody ").append(beanParam).append(" reqBean,")
					.append("HttpServletRequest request, HttpServletResponse response) {").append(NEXT_LINE);

			sb.append(formatMsg(2)).append("List<" + beanParam + "> list = null;").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("long total = 0;").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("//调用查询操作").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("if( null != reqBean){").append(NEXT_LINE);

			sb.append(formatMsg(3)).append("Pagination<" + beanParam + "> result = ")
					.append("serviceInf.queryPage(reqBean);").append(NEXT_LINE);
			sb.append(formatMsg(3)).append("//进行结果的处理").append(NEXT_LINE);
			sb.append(formatMsg(3)).append("list = result.getList();").append(NEXT_LINE);
			sb.append(formatMsg(3)).append("total = result.getTotal();").append(NEXT_LINE);
			sb.append(formatMsg(2)).append("}").append(NEXT_LINE);

			sb.append(formatMsg(2)).append("String rspJsonMsg = setSuccessResultForString(list, total);")
					.append(NEXT_LINE);
			sb.append(formatMsg(2)).append("this.writeResponse(response, rspJsonMsg, null);").append(NEXT_LINE);

			sb.append(formatMsg(1)).append("}").append(NEXT_LINE).append(NEXT_LINE);
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
			String daoInfPath = param.getFileBasePath() + "javaBeanActionJson/";
			String javaPackage = param.getJavaPackage();
			this.encodeActionImpl(daoInfPath, javaPackage, param.getTableSpaceName(), param.getContext());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

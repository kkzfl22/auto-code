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
 * 生成dao的java代码
 * @since 2018年4月15日 下午2:57:50
 * @version 0.0.1
 * @author liujun
 */
public class JavaCodeBeanDaoImplCreate extends TableProcessBase implements AutoCodeInf {

    public static void main(String[] args) throws Exception {

        args = new String[4];

        args[0] = "D:/java/encode/a10/javabeanDaoImpl/";
        args[1] = "com.a10.resource.phone.";
        args[2] = "com.a10.resource.phone.";
        args[3] = "a10";

        JavaCodeBeanDaoImplCreate daoImplInstance = new JavaCodeBeanDaoImplCreate();
        
        Map<String, List<TableColumnDTO>> map = daoImplInstance.getTableColumnInfoByBean(args[3]);

		Map<String, TableInfoDTO> tableMap = daoImplInstance.getTableInfoByBean(args[3]);

		EncodeContext context = new EncodeContext();

		context.setColumnMap(map);
		context.setTableMap(tableMap);
        
        
        daoImplInstance.encodeDaoImpl(args[0], args[1], args[2], args[3],context);

    }

    /**
     * 进行Dao代码的自动生成
    * 方法描述
    * @param path
    * @param packageStr
    * @param mapperNamespace
    * @param tableSpaceName
    * @throws Exception
    * @创建日期 2016年10月8日
    */
    private void encodeDaoImpl(String path, String basePackage, String mapperNamespace, String tableSpaceName,EncodeContext context)
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

            StringBuffer sb = new StringBuffer();
            
            TableInfoDTO tableInfo = context.getTableMap().get(tableName.getKey());
            

            String tableClassName = toJavaClassName(tableName.getKey());

            String serviceName = tableClassName;
            String className = serviceName + "DAOImpl";

            // 引入javabean的路径
            String importBean = basePackage + "pojo." + tableClassName + "DTO";
            String beanParam = tableClassName + "DTO";

            String packageStr = basePackage + "dao.impl";
            String daoPackageImportStr = basePackage + "dao." + serviceName + "DAO";

            sb.append("package ").append(packageStr).append(";").append(NEXT_LINE);
            sb.append(NEXT_LINE);
            sb.append("import java.util.List;").append(NEXT_LINE);
            sb.append(NEXT_LINE);
            sb.append("import org.springframework.stereotype.Repository;").append(NEXT_LINE);
            sb.append("import org.apache.log4j.Logger;").append(NEXT_LINE);
            sb.append(NEXT_LINE);
            sb.append("import ").append(importBean).append(";").append(NEXT_LINE);
            sb.append("import ").append(BASE_DAO_PATH).append(NEXT_LINE);
            sb.append("import ").append(COMM_SPIT_BEAN).append(NEXT_LINE);
            sb.append("import ").append(daoPackageImportStr).append(";").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 添加类注释信息
            sb.append("/**").append(NEXT_LINE);
            sb.append(" * ").append(tableInfo.getTableComment()).append("(").append(tableName.getKey()).append(")数据库操作实现")
			.append(NEXT_LINE);
            sb.append(" * @version 1.0.0").append(NEXT_LINE);
			sb.append(" * @author liujun").append(NEXT_LINE);
			sb.append(" * @date ").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
            sb.append(" */").append(NEXT_LINE);
            sb.append("@Repository").append(NEXT_LINE);
            sb.append("public class " + className + " extends BaseDAOImpl implements " + serviceName + "DAO {");
            sb.append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 添加命名空间
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * mybatis的命名空间").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("private static final String NAMESPACE =").append("\"")
                    .append(mapperNamespace).append(toJavaClassName(tableName.getKey())).append("Mapper.\";");
            sb.append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 添加日志引入
            sb.append(formatMsg(1)).append("private Logger log = Logger.getLogger(").append(className)
                    .append(".class);").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 添加insert方法
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            
            sb.append(formatMsg(1)).append(" * ").append(tableInfo.getTableComment()).append("(");
			sb.append(tableName.getKey()).append(")添加操作").append(NEXT_LINE);
            
            sb.append(formatMsg(1)).append(" * @param param 参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @throws Exception 异常信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @return 数据库影响的行数").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@Override").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("public int insert(" + beanParam + " param)throws Exception{")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className).append(" insert param:\"+param); ")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("int result = this.insert(NAMESPACE+\"insert\",param);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className)
                    .append(" insert param:\"+param+\",result \"").append("+result").append(");").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("return result;").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 修改
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            
            sb.append(formatMsg(1)).append(" * ").append(tableInfo.getTableComment()).append("(");
			sb.append(tableName.getKey()).append(")修改操作").append(NEXT_LINE);
            
            sb.append(formatMsg(1)).append(" * @param param 参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @throws Exception 异常信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @return 数据库影响的行数").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@Override").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("public int update(" + beanParam + " param)throws Exception {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className).append(" update param:\"+param); ")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("int result = this.update(NAMESPACE+\"update\",param);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className)
                    .append(" update param:\"+param+\",result \"").append("+result").append(");").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("return result;").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 删除
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            
            sb.append(formatMsg(1)).append(" * ").append(tableInfo.getTableComment()).append("(");
			sb.append(tableName.getKey()).append(")删除操作").append(NEXT_LINE);
            
            sb.append(formatMsg(1)).append(" * @param param 参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @throws Exception 异常信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @return 数据库影响的行数").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@Override").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("public int delete(" + beanParam + " param)throws Exception {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className).append(" delete param:\"+param); ")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("int result = this.delete(NAMESPACE+\"delete\",param);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className)
                    .append(" delete param:\"+param+\",result \"").append("+result").append(");").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("return result;").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 查询
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            
            sb.append(formatMsg(1)).append("* ").append(tableInfo.getTableComment()).append("(");
			sb.append(tableName.getKey()).append(")查询操作").append(NEXT_LINE);
            
            sb.append(formatMsg(1)).append(" * @param param 参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @throws Exception 异常信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @return 数据库查询结果集").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@Override").append(NEXT_LINE);
            sb.append(formatMsg(1))
                    .append("public List<" + beanParam + "> query(" + beanParam + " param)throws Exception {")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className).append(" query param:\"+param); ")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("List<" + beanParam + "> list = this.selectList(NAMESPACE+\"query\",param);")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className)
                    .append(" query param:\"+param+\",result true\");").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("return list;").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("}").append(NEXT_LINE);
            sb.append(NEXT_LINE);

            // 进行分页查询操作
            sb.append(formatMsg(1)).append("/**").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * ").append(tableInfo.getTableComment()).append("(");
			sb.append(tableName.getKey()).append(")分页查询操作").append(NEXT_LINE);
            
            sb.append(formatMsg(1)).append(" * @param param 参数信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @throws Exception 异常信息").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" * @return 数据库查询结果集").append(NEXT_LINE);
            sb.append(formatMsg(1)).append(" */").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("@Override").append(NEXT_LINE);
            sb.append(formatMsg(1)).append("public Pagination<" + beanParam + "> queryPage");
            sb.append("(" + beanParam + " param)throws Exception {");
            sb.append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className).append(" queryPage param:\"+param); ")
                    .append(NEXT_LINE);
            sb.append(formatMsg(2)).append("Pagination<" + beanParam + "> result = ");
            sb.append("this.selectPhysicPagination(NAMESPACE+\"page\", param);").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("log.info(\"").append(className)
                    .append(" queryPage param:\"+param+\",result true, pageMsg \"+result+\"\");").append(NEXT_LINE);
            sb.append(formatMsg(2)).append("return result;").append(NEXT_LINE);
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
        String daoImplPath = param.getFileBasePath() + "javaBeanDAOImpl/";
        String javaPackage = param.getJavaPackage();

        try {
            this.encodeDaoImpl(daoImplPath, javaPackage, param.getMybatisBaseSpace(), param.getTableSpaceName(),param.getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

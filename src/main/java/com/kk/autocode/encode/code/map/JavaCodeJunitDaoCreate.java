// package com.kk.autocode.encode.code.map;
//
// import java.io.File;
// import java.io.FileWriter;
// import java.util.Iterator;
// import java.util.List;
// import java.util.Map;
// import java.util.Map.Entry;
//
// import com.kk.autocode.encode.base.TableProcessBase;
// import com.kk.autocode.encode.bean.CreateParamBean;
// import com.kk.element.database.mysql.pojo.TableColumnDTO;
// import com.kk.autocode.encode.code.AutoCodeInf;
// import com.kk.autocode.util.DateUtils;
//
/// **
// * 生成junit测试代码
// * 源文件名：JavaCodeMian.java
// * 文件版本：1.0.0
// * 创建作者：liujun
// * 创建日期：2016年9月12日
// * 修改作者：liujun
// * 修改日期：2016年9月12日
// * 文件描述：TODO
// * 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
// */
// public class JavaCodeJunitDaoCreate extends TableProcessBase implements AutoCodeInf {
//
//    public static void main(String[] args) throws Exception {
//        JavaCodeJunitDaoCreate instance = new JavaCodeJunitDaoCreate();
//
//        args = new String[3];
//
//        args[0] = "D:/java/encode/a10/javaMapJunitDao/";
//        args[1] = "com.a10.resource.phone.";
//        args[2] = "a10";
//
//        instance.enTestDaoCode(args[0], args[1], args[2]);
//    }
//
//    private void enTestDaoCode(String path, String basePackageStr, String tableSpace) throws
// Exception {
//
//        File dirFile = new File(path);
//        // 如果文件夹存在，则执行删除
//        boolean exists = dirFile.exists();
//        if (exists) {
//            dirFile.delete();
//        }
//
//        dirFile.mkdirs();
//
//        Map<String, List<TableColumnDTO>> map = this.getTableColumnInfoByBean(tableSpace);
//
//        // Set<String> tableNames = map.keySet();
//        Iterator<Entry<String, List<TableColumnDTO>>> tableNameEntry = map.entrySet().iterator();
//
//        while (tableNameEntry.hasNext()) {
//            Entry<String, List<TableColumnDTO>> tableName = tableNameEntry.next();
//
//            List<TableColumnDTO> columnList = tableName.getValue();
//            StringBuffer sb = new StringBuffer();
//
//            String tableClassName = toJavaClassName(tableName.getKey());
//
//            String serviceName = tableClassName + "DAO";
//            String className = serviceName + "Test";
//            TableColumnDTO primaryKey = this.getPrimaryKey(columnList);
//
//            TableColumnDTO idTableBean = null;
//
//            if (null != primaryKey) {
//                idTableBean = primaryKey;
//            } else {
//                idTableBean = columnList.get(0);
//            }
//
//            String packageStr = basePackageStr + "dao";
//
//            String importDAOBean = basePackageStr + "dao." + serviceName;
//
//            sb.append("package ").append(packageStr).append(";").append(NEXT_LINE);
//            sb.append(NEXT_LINE);
//
//            sb.append("import java.util.List;").append(NEXT_LINE);
//            sb.append("import java.util.Map;").append(NEXT_LINE);
//            sb.append("import java.util.HashMap;").append(NEXT_LINE);
//            sb.append(NEXT_LINE);
//
//            sb.append("import org.junit.Test;").append(NEXT_LINE);
//            sb.append("import org.junit.Before;").append(NEXT_LINE);
//            sb.append(NEXT_LINE);
//            sb.append("import ").append(COMM_SPIT_BEAN).append(NEXT_LINE);
//            sb.append("import ").append(importDAOBean).append(";").append(NEXT_LINE);
//            sb.append("import
// ").append(basePackageStr).append("TestParent").append(";").append(NEXT_LINE);
//            sb.append(NEXT_LINE);
//
//            // 添加类注释信息
//            sb.append("/**").append(NEXT_LINE);
//            sb.append("*").append(tableName.getKey()).append("表的数据库操作单元测试").append(NEXT_LINE);
//            sb.append("* 文件版本：1.0.0").append(NEXT_LINE);
//            sb.append("* 创建作者：liujun").append(NEXT_LINE);
//            sb.append("* 创建日期：").append(DateUtils.getStrCurrtDate()).append(NEXT_LINE);
//            sb.append("*/").append(NEXT_LINE);
//            sb.append(NEXT_LINE);
//            sb.append("public class " + className + " extends TestParent {" + NEXT_LINE);
//
//            sb.append(NEXT_LINE);
//
//            // 添加前置方法
//            sb.append(formatMsg(1)).append("private " + tableClassName + "DAO instDao;" +
// NEXT_LINE);
//
//            sb.append(NEXT_LINE);
//
//            sb.append(formatMsg(1)).append(" @Before" + NEXT_LINE);
//            sb.append(formatMsg(1)).append("public void setBean() { " + NEXT_LINE);
//            sb.append(formatMsg(2)).append("instDao = (" + tableClassName + "DAO)");
//            sb.append(" this.getBean(\"" + getSpringInstanceName(tableClassName) + "DAOImpl\"); "
// + NEXT_LINE);
//            sb.append(formatMsg(1)).append("}" + NEXT_LINE);
//            sb.append(NEXT_LINE);
//
//            // 添加insert方法
//            sb.append(formatMsg(1)).append("@Test" + NEXT_LINE);
//            sb.append(formatMsg(1)).append("public void testInsert() throws Exception
// {").append(NEXT_LINE);
//            sb.append(formatMsg(2)).append("Map<String,Object> map = new
// HashMap<String,Object>();" + NEXT_LINE);
//            for (int i = 1; i < columnList.size(); i++) {
//                String cloumnName = columnList.get(i).getColumnName();
//                String javaName = toJava(cloumnName);
//                // 添加当前的属性值的注释
//                sb.append(formatMsg(2)).append("//");
//                sb.append(columnList.get(i).getColumnMsg());
//                sb.append(NEXT_LINE);
//                // 添加生成代码的方法
//                sb.append(formatMsg(2)).append("map.put(\"");
//                sb.append(javaName);
//                sb.append("\",\"");
//                sb.append(createValue(columnList.get(i)));
//                sb.append("\");" + NEXT_LINE);
//            }
//            // 执行添加代码操作
//            sb.append(formatMsg(2)).append("int rs = instDao.insert(map);" + NEXT_LINE);
//            sb.append(formatMsg(2)).append("System.out.println(rs);" + NEXT_LINE);
//            sb.append(formatMsg(1)).append("}");
//
//            sb.append(NEXT_LINE);
//            sb.append(NEXT_LINE);
//            // 修改
//            sb.append(formatMsg(1)).append("@Test" + NEXT_LINE);
//            sb.append(formatMsg(1)).append("public void testUpdate() throws Exception {");
//            sb.append(NEXT_LINE);
//            sb.append(formatMsg(2)).append("Map<String,Object> updatemap = new
// HashMap<String,Object>();" + NEXT_LINE);
//            for (int i = 0; i < columnList.size(); i++) {
//                String cloumnName = columnList.get(i).getColumnName();
//                String javaName = toJava(cloumnName);
//                // 添加当前的属性值的注释
//                sb.append(formatMsg(2)).append("//");
//                sb.append(columnList.get(i).getColumnMsg());
//                sb.append(NEXT_LINE);
//                sb.append(formatMsg(2)).append("updatemap.put(\"");
//                sb.append(javaName);
//                sb.append("\",\"");
//                sb.append(createValue(columnList.get(i)));
//                sb.append("\");" + NEXT_LINE);
//            }
//            sb.append(formatMsg(2)).append("int rs = instDao.update(updatemap);" + NEXT_LINE);
//            sb.append(formatMsg(2)).append("System.out.println(rs);" + NEXT_LINE);
//            sb.append(formatMsg(1)).append("}");
//
//            sb.append(NEXT_LINE);
//            sb.append(NEXT_LINE);
//
//            // 删除
//            sb.append(formatMsg(1)).append("@Test" + NEXT_LINE);
//            sb.append(formatMsg(1)).append("public void testDelete() throws Exception {");
//            sb.append(NEXT_LINE);
//            sb.append(formatMsg(2)).append("Map<String,Object> deleteMap = new
// HashMap<String,Object>();" + NEXT_LINE);
//            // 添加注释
//            sb.append(formatMsg(2)).append("//");
//            sb.append(idTableBean.getColumnMsg());
//            sb.append(NEXT_LINE);
//            // 生成代码
//
// sb.append(formatMsg(2)).append("deleteMap.put(\"").append(idTableBean.getColumnName()).append("\",\"");
//            sb.append(createValue(idTableBean));
//            sb.append("\");");
//            sb.append(NEXT_LINE);
//            sb.append(formatMsg(2)).append("int rs = instDao.delete(deleteMap);" + NEXT_LINE);
//            sb.append(formatMsg(2)).append("System.out.println(rs);" + NEXT_LINE);
//            sb.append(formatMsg(1)).append("}");
//            sb.append(NEXT_LINE);
//            sb.append(NEXT_LINE);
//
//            // 查询
//            sb.append(formatMsg(1)).append("@Test" + NEXT_LINE);
//            sb.append(formatMsg(1)).append("public void testQuery() throws Exception {");
//            sb.append(NEXT_LINE);
//            sb.append(formatMsg(2)).append("Map<String,Object> queryMap = new
// HashMap<String,Object>();" + NEXT_LINE);
//            for (int i = 0; i < columnList.size(); i++) {
//                String cloumnName = columnList.get(i).getColumnName();
//                String javaName = toJava(cloumnName);
//                // 添加当前的属性值的注释
//                sb.append(formatMsg(2)).append("//");
//                sb.append(columnList.get(i).getColumnMsg());
//                sb.append(NEXT_LINE);
//                sb.append(formatMsg(2)).append("queryMap.put(\"");
//                sb.append(javaName);
//                sb.append("\",\"");
//                sb.append(createValue(columnList.get(i)));
//                sb.append("\");" + NEXT_LINE);
//            }
//            sb.append(formatMsg(2)).append("List<Map<String, Object> rs =
// instDao.query(queryMap);" + NEXT_LINE);
//            sb.append(formatMsg(2)).append("System.out.println(rs);" + NEXT_LINE);
//            sb.append(formatMsg(1)).append("}");
//
//            sb.append(NEXT_LINE);
//            sb.append(NEXT_LINE);
//
//            // 分页查询
//            sb.append(formatMsg(1)).append("@Test" + NEXT_LINE);
//            sb.append(formatMsg(1)).append("public void testPageQuery() throws Exception {");
//            sb.append(NEXT_LINE);
//            sb.append(formatMsg(2)).append("Map<String,Object> queryMap = new
// HashMap<String,Object>();" + NEXT_LINE);
//            for (int i = 0; i < columnList.size(); i++) {
//                String cloumnName = columnList.get(i).getColumnName();
//                String javaName = toJava(cloumnName);
//                // 添加当前的属性值的注释
//                sb.append(formatMsg(2)).append("//");
//                sb.append(columnList.get(i).getColumnMsg());
//                sb.append(NEXT_LINE);
//                sb.append(formatMsg(2)).append("queryMap.put(\"");
//                sb.append(javaName);
//                sb.append("\",\"");
//                sb.append(createValue(columnList.get(i)));
//                sb.append("\");" + NEXT_LINE);
//            }
//            sb.append(formatMsg(2))
//                    .append("Pagination<Map<String,Object> rs = instDao.queryPage(queryMap,1,1);"
// + NEXT_LINE);
//            sb.append(formatMsg(2)).append("System.out.println(rs);" + NEXT_LINE);
//            sb.append(formatMsg(1)).append("}");
//
//            sb.append(NEXT_LINE);
//
//            // 结束
//            sb.append("}");
//            FileWriter fw = new FileWriter(new File(path + className + ".java"));
//            fw.write(sb.toString());
//            fw.close();
//        }
//
//    }
//
//    @Override
//    public void autoCode(CreateParamBean param) {
//
//        // 进行自动代码生成
//        try {
//            String daoInfPath = param.getFileBasePath() + "javaMapJunitDAO/";
//            String javaPackage = param.getJavaPackage();
//            this.enTestDaoCode(daoInfPath, javaPackage, param.getTableSpaceName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
// }

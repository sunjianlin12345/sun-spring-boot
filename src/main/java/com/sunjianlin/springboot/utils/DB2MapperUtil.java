package com.sunjianlin.springboot.utils;


import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by sunjianlin
 * 2018年04月16日 15:16:23
 */
public class DB2MapperUtil {

    // 定义数据库常用类型
    private static Map<String, String> types = new HashMap<String, String>();

    // 获取指定表的所有字段详情
    private static final String showFields = "show full fields from ";

    private static final String exitTable = "show tables like ?";

    private static List<Column> columns = new ArrayList<Column>();

    //项目所在目录
    private static String relativelyPath = System.getProperty("user.dir");

    private static Properties prop = new Properties();

    private static String driver;

    private static String url;

    private static String username;

    private static String password;

    private static Connection conn = null;

    private static PreparedStatement pstate = null;

    private static ResultSet rs = null;

    private static String author;
    //数据库表名
    private static String[] tableNames;

    private static String currentTableName;

    private static String targetJava;

    private static String targetResources;

    private static String targetRoot;

    private static String targetGroup;

    private static String moduleName;

    private static String entityTargetPackage;

    private static String daoTargetPackage;

    private static String serviceTargetPackage;

    private static String mapperTargetPackage;

    private static String entityName;

    private static String daoName;

    private static String serviceName;

    private static String serviceImplName;

    //数据库表字段
    private static class Column{
        //名称
        private String filed;
        //类型
        private String type;
        //字段注释
        private String comment;
        //字段首字母小写aaBbCc
        private String firstLowerName = "";
        //字段首字母大写AcBbCc
        private String firstUpperName = "";
        //java类型
        private String javaType;
        //jdbcType类型
        private String jdbcType;

        public Column(String filed, String type, String comment) throws Exception {
            this.filed = filed;
            this.type = type;
            this.comment = comment;
            this.firstLowerName = CodeUtil.convertLowerOrUpper(filed, "_", true);
            this.firstUpperName = CodeUtil.convertLowerOrUpper(filed, "_", false);
            String converJdbcType = type.replaceAll("[^A-Za-z]", "");
            this.jdbcType = CodeUtil.convertString(converJdbcType, true);
            this.javaType = jdbc2javaType(jdbcType);
        }

        private String jdbc2javaType(String jdbcType) throws Exception {
            String javaType = types.get(jdbcType);
            if(StringUtils.isBlank(javaType)) {
                System.err.println("字段" + filed+ "的jdbc类型：" + type + ",匹配java类型出现错误");
                throw new Exception("字段" + filed+ "的jdbc类型：" + type + ",匹配java类型出现错误");
            } else if(javaType.contains(".")) {
                javaType = javaType.split("\\.")[2];
            }
            return  javaType;
        }

        public String getFiled() {
            return filed;
        }

        public String getType() {
            return type;
        }

        public String getComment() {
            return comment;
        }

        public String getFirstLowerName() {
            return firstLowerName;
        }

        public String getFirstUpperName() {
            return firstUpperName;
        }

        public String getJavaType() {
            return javaType;
        }

        public String getJdbcType() {
            return jdbcType;
        }

    }

    /**
     * 读取配置
     */
    private static void readProperties() {
        try {
            prop.load(new BufferedInputStream( new FileInputStream(relativelyPath+"\\src\\main\\resources\\DB2Mapper")));
//            for (Object key : prop.keySet()) {
//                System.out.println(key + "=" + prop.get(key));
//            }
            driver = (String) prop.get("jdbc.driver");
            url = (String) prop.get("jdbc.url");
            username = (String) prop.get("jdbc.username");
            password = (String) prop.get("jdbc.password");
            author = (String) prop.get("author");
            tableNames = ((String) prop.get("tableName")).split(",");
            targetRoot = (String) prop.get("targetRoot");
            targetGroup = (String) prop.get("targetGroup");
            targetJava = targetRoot + targetGroup;
            targetResources = (String) prop.get("targetResources");
            moduleName = (String) prop.get("moduleName");
            entityTargetPackage = (String) prop.get("entityTargetPackage");
            daoTargetPackage = (String) prop.get("daoTargetPackage");
            serviceTargetPackage = (String) prop.get("serviceTargetPackage");
            mapperTargetPackage = (String) prop.get("mapperTargetPackage");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(tableNames.length == 0 ) {
            System.err.println("tableName不能为空");
        } else {
            Arrays.asList(tableNames).stream().forEach((String x) -> {
                if(StringUtils.isBlank(x)) {
                    System.err.println("不能有空白表名");
                }
            });
        }
    }

    /**
     * 初始化mysql数据类型
     */
    private static void initMysqlTypes() {
        types.put("CHAR", "String");
        types.put("VARCHAR", "String");
        types.put("LONGVARCHAR", "String");
        types.put("NUMERIC", "java.math.BigDecimal");
        types.put("DECIMAL", "java.math.BigDecimal");
        types.put("BIT", "boolean");
        types.put("BOOLEAN", "boolean");
        types.put("TINYINT", "byte");
        types.put("SMALLINT", "short");
        types.put("INTEGER", "int");
        types.put("BIGINT", "long");
        types.put("REAL", "float");
        types.put("FLOAT", "double");
        types.put("DOUBLE", "double");
        types.put("BINARY", "byte[]");
        types.put("VARBINARY", "byte[]");
        types.put("LONGVARBINARY", "byte[]");
        types.put("DATE", "java.sql.Date");
        types.put("DATETIME", "java.sql.Date");
        types.put("TIME", "java.sql.Time");
        types.put("TIMESTAMP", "java.sql.Timestamp");
        types.put("CLOB", "Clob");
        types.put("BLOB", "Blob");
        types.put("ARRAY", "Array");
    }

    /**
     * 数据库连接
     */
    private static void getConnection() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username,
                    password);
        } catch (Exception ex) {
            System.out.println("数据库获取连接失败:" + ex.getMessage());
        }
    }

    /**
     * 数据库查询
     */
    private static void executeQuery() throws Exception {
        columns.clear();
        pstate = conn.prepareStatement(exitTable);
        pstate.setString(1, currentTableName);
        rs = pstate.executeQuery();
        if(rs.next()==false) {
            System.err.println("表"+currentTableName+"不存在");
            throw new Exception("表"+currentTableName+"不存在");
        }
        pstate = conn.prepareStatement(showFields + currentTableName);
        ResultSet results = pstate.executeQuery();
        int error = 0;
        while (results.next()) {
            Column column = new Column(results.getString("FIELD"),
                    results.getString("TYPE"), results.getString("COMMENT"));
            columns.add(column);
            if(column.getFiled().equals("id") || column.getFiled().equals("create_date")
                    || column.getFiled().equals("update_date")) {
                error++;
            }
        }
        if(error < 3) {
            System.err.println("表" + currentTableName + "缺失字段id、createDate、updateDate");
            throw new Exception("表" + currentTableName + "缺失字段id、createDate、updateDate");
        }
    }

    /**
     * 创建文件
     */
    private static File createFile(String directoryName, String fileName) throws Exception {
        File directory = new File(directoryName);
        if(!directory.exists()) {
            //目录不存在则新建目录
            directory.mkdirs();
        }
        File file = new File(directoryName + "\\" + fileName);
        if(file.exists()) {
            System.err.println("文件 "+fileName+" 已存在");
            file.delete();
        }
        file.createNewFile();
        return file;
    }

    /**
     * 数据写入文件
     * @param file
     */
    private static void writeFile(File file, StringBuffer inputData) {
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            fos.write(inputData.toString().getBytes("UTF-8"));
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 类文件头部
     * @param inputData
     * @param packageName 包名 例如：com.sunjianlin.sunspringboot.dao
     * @param classType 类类型 例如：interface class
     * @param annotations 类注解 例如：@Mapper
     * @param className 类名或者接口名 例如：BaseMapper
     * @param extendAndImplements 继承或者实现的语句 例如：extends BaseEntity implements Serializable
     */
    private static void appendClassHead(StringBuffer inputData, String packageName, String[] annotations,
                                        String classType, String className, String extendAndImplements,
                                        String importPackage) {
        inputData.append("package " + packageName + ";");
        inputData.append("\r\n");
        inputData.append(importPackage);
        inputData.append("\r\n");
        inputData.append("/**");
        inputData.append("\r\n");
        inputData.append(" * Created by " + author);
        inputData.append("\r\n");
        inputData.append(" * " + new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
        inputData.append("\r\n");
        inputData.append(" */");
        inputData.append("\r\n");
        if(annotations.length > 0) {
            Arrays.asList(annotations).stream().forEach(x -> {
                inputData.append(x);
                inputData.append("\r\n");
            });
        } else {
            extendAndImplements= "";
        }
        inputData.append("public " + classType + " " + className + " " + extendAndImplements + " {");
    }

    /**
     * 类文件尾部
     */
    private static void appendClassEnd(StringBuffer inputData) throws IOException {
        inputData.append("\r\n\r\n");
        inputData.append("}");
        inputData.append("\r\n");
    }


    /**
     * 构建实体类
     */
    private static void generaneEntity() throws Exception {
        String directoryName = relativelyPath + targetJava + "\\" + entityTargetPackage;
        String fileName = entityName + ".java";
        File file = createFile(directoryName, fileName);
        StringBuffer inputData = new StringBuffer();
        String importPackage = "import java.io.Serializable;\n" +
                "import lombok.Data;\n" +
                "import lombok.EqualsAndHashCode;\n";
        appendClassHead(inputData, moduleName + "." + entityTargetPackage,new String[]{
                "@Data", "@EqualsAndHashCode(callSuper=true)"
                }, "class",
                entityName,
                "extends BaseEntity implements Serializable",
                importPackage);

        inputData.append("\r\n\r\n");
        inputData.append("    private static final long serialVersionUID = 1L;");
//        StringBuffer setGetData = new StringBuffer();
        for(Column column : columns) {
            if(column.getFiled().equals("id") || column.getFiled().equals("create_date")
                    || column.getFiled().equals("update_date")) {
                continue;
            }
            inputData.append("\r\n\r\n");
            inputData.append("    /**");
            inputData.append("\r\n");
            inputData.append("     * " + column.getComment());
            inputData.append("\r\n");
            inputData.append("     */");
            inputData.append("\r\n");
            inputData.append("    private " + column.getJavaType() + " " + column.getFirstLowerName() + ";");
            //set get方法
//            setGetData.append("\r\n\r\n");
//            setGetData.append("    public void set" + column.getFirstUpperName() + "(" + column.getJavaType() + " " + column.getFirstLowerName() + ") {");
//            setGetData.append("\r\n");
//            setGetData.append("        this." + column.getFirstLowerName() + " = " + column.getFirstLowerName() + ";");
//            setGetData.append("\r\n");
//            setGetData.append("    }");
//            setGetData.append("\r\n\r\n");
//            setGetData.append("    public " + column.getJavaType() + " get" + column.getFirstUpperName() + "() {");
//            setGetData.append("\r\n");
//            setGetData.append("        return " + column.getFirstLowerName() + ";");
//            setGetData.append("\r\n");
//            setGetData.append("    }");
        }
//        inputData.append(setGetData);

        appendClassEnd(inputData);

        writeFile(file, inputData);
        System.out.print("新增\r\n    " + directoryName + "\\" + fileName);
    }

    /**
     * 构建Dao
     */
    private static void generaneDao() throws Exception {
        String directoryName = relativelyPath + targetJava + "\\" + daoTargetPackage;
        String fileName = daoName + ".java";
        File file = createFile(directoryName, fileName);
        StringBuffer inputData = new StringBuffer();
        String importPackage = "import "+moduleName+"." +entityTargetPackage+"." +entityName + ";\n"
                +"import org.apache.ibatis.annotations.Mapper;\n";
        appendClassHead(inputData, moduleName + "." + daoTargetPackage, new String[]{"@Mapper"}, "interface",
                CodeUtil.convertLowerOrUpper(currentTableName, "_", false) + "Mapper",
                "extends BaseMapper<"+entityName+">",
                importPackage);
        inputData.append("\r\n");
        appendClassEnd(inputData);
        writeFile(file, inputData);
        System.out.print("新增\r\n    " + directoryName + "\\" + fileName);
    }

    /**
     * 构建Service接口
     */
    private static void generaneService() throws Exception {
        String directoryName = relativelyPath + targetJava + "\\" + serviceTargetPackage;
        String fileName = serviceName + ".java";
        File file = createFile(directoryName, fileName);
        StringBuffer inputData = new StringBuffer();
        String importPackage = "import "+moduleName+"." +entityTargetPackage+"." +entityName + ";\n";
        appendClassHead(inputData, moduleName + "." + serviceTargetPackage, new String[]{}, "interface",
                serviceName,
                null,
                importPackage);
        inputData.append("\r\n");
        appendClassEnd(inputData);
        writeFile(file, inputData);
        System.out.print("新增\r\n    " + directoryName + "\\" + fileName);
    }

    /**
     * 构建ServiceImpl实现类
     */
    private static void generaneServiceImpl() throws Exception {
        String directoryName = relativelyPath + targetJava + "\\" + serviceTargetPackage + "\\impl";
        String fileName = serviceImplName + ".java";
        File file = createFile(directoryName, fileName);
        StringBuffer inputData = new StringBuffer();
        String importPackage = "import "+moduleName+"." +daoTargetPackage+"." +daoName+ ";\n"
                +"import "+moduleName+"." +entityTargetPackage+"." +entityName+ ";\n"
                +"import "+moduleName+"." +serviceTargetPackage+"." +serviceName+ ";\n"
                +"import org.springframework.beans.factory.annotation.Autowired;\n"
                +"import org.springframework.stereotype.Service;\n"
                ;
        appendClassHead(inputData, moduleName+"."+serviceTargetPackage+".impl", new String[]{"@Service(\""+CodeUtil.convertLowerOrUpper(currentTableName, "_", true) + "Service\")"},
                "class", serviceImplName,
                "implements "+serviceName,
                importPackage);
        inputData.append("\r\n");
        inputData.append("    @Autowired\n");
        inputData.append("    private "+daoName+" "+CodeUtil.convertLowerOrUpper(currentTableName, "_", true) + "Mapper"+";\n");
        inputData.append("\r\n");
        appendClassEnd(inputData);
        writeFile(file, inputData);
        System.out.print("新增\r\n    " + directoryName + "\\" + fileName);
    }

    /**
     * 构建映射文件
     */
    private static void generaneMapper() throws Exception {
        String directoryName = relativelyPath + targetResources + "\\" + mapperTargetPackage;
        String fileName = daoName + ".xml";
        File file = createFile(directoryName, fileName);
        StringBuffer inputData = new StringBuffer();
        String entityClass = moduleName+"."+entityTargetPackage+"."+entityName;
        String daoClass = moduleName+"."+daoTargetPackage+"."+daoName;
        inputData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        inputData.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n");
        inputData.append("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
        inputData.append("\r\n");
        inputData.append("<mapper namespace=\""+daoClass+"\">\n");
        inputData.append("\r\n");
        StringBuffer selectSqlData = new StringBuffer();
        StringBuffer insertFiledData = new StringBuffer();
        StringBuffer insertValuedData = new StringBuffer();
        StringBuffer batchInsertFiledData = new StringBuffer();
        StringBuffer batchInsertValuedData = new StringBuffer();
        StringBuffer updateData = new StringBuffer();
        StringBuffer batchUpdateData = new StringBuffer();
        //resultMap
        inputData.append("    <resultMap id=\"BaseResultMap\"\n");
        inputData.append("               type=\""+entityClass+"\">\n");
        for(Column column : columns) {
            if(column.getFiled().equals("id")) {
                inputData.append("        <id column=\"id\" property=\"id\" jdbcType=\"BIGINT\"/>\n");
            } else {
                inputData.append("        <result column=\""+column.getFiled()+"\" property=\""+column.getFirstLowerName()+"\" jdbcType=\""+column.getJdbcType()+"\"/>\n");
                if(column.getJavaType().equals("String")) {
                    insertFiledData.append("            <if test=\""+column.getFirstLowerName()+" != null and "+column.getFirstLowerName()+" != ''\">\n");
                    insertValuedData.append("            <if test=\""+column.getFirstLowerName()+" != null and "+column.getFirstLowerName()+" != ''\">\n");
                } else {
                    insertFiledData.append("            <if test=\""+column.getFirstLowerName()+" != null\">\n");
                    insertValuedData.append("            <if test=\""+column.getFirstLowerName()+" != null\">\n");
                }
                insertFiledData.append("                "+column.getFiled()+",\n");
                insertFiledData.append("            </if>\n");
                insertValuedData.append("                #{"+column.getFirstLowerName()+"},\n");
                insertValuedData.append("            </if>\n");
                if(column.getFiled().equals("create_date")){
                    insertFiledData.append("            <if test=\""+column.getFirstLowerName()+" == null\">\n");
                    insertFiledData.append("                "+column.getFiled()+",\n");
                    insertFiledData.append("            </if>\n");
                    insertValuedData.append("            <if test=\""+column.getFirstLowerName()+" == null\">\n");
                    insertValuedData.append("                SYSDATE(),\n");
                    insertValuedData.append("            </if>\n");
                }
                updateData.append("                <if test=\""+column.getFirstLowerName()+" != null\" >\n");
                updateData.append("                    "+column.getFiled()+" = #{"+column.getFirstLowerName()+"},\n");
                updateData.append("                </if>\n");
            }
            if(column.getFiled().equals("create_date") || column.getFiled().equals("update_date")) {
                String startDate = column.getFiled().equals("create_date") ? "createStartDate" : "updateStartDate";
                String endDate = column.getFiled().equals("create_date") ? "createEndDate" : "updateEndDate";
                selectSqlData.append("            <if test=\""+startDate+" != null\">\n");
                selectSqlData.append("                <![CDATA[ AND "+column.getFiled()+" >= #{"+startDate+"} ]]>\n");
                selectSqlData.append("            </if>\n");
                selectSqlData.append("            <if test=\""+endDate+" != null\">\n");
                selectSqlData.append("                <![CDATA[ AND "+column.getFiled()+" <= #{"+endDate+"} ]]>\n");
                selectSqlData.append("            </if>\n");
            } else {
                if(column.getJavaType().equals("String")) {
                    selectSqlData.append("            <if test=\""+column.getFirstLowerName()+" != null and "+column.getFirstLowerName()+" != ''\">\n");
                } else {
                    selectSqlData.append("            <if test=\""+column.getFirstLowerName()+" != null\">\n");
                }
                selectSqlData.append("                AND "+column.getFiled()+" = #{"+column.getFirstLowerName()+"}\n");
                selectSqlData.append("            </if>\n");
            }
        }
        inputData.append("    </resultMap>\n");
        //selectSql
        inputData.append("    <sql id=\"selectSql\">\n");
        inputData.append("        <where>\n");
        inputData.append(selectSqlData);
        inputData.append("        </where>\n");
        inputData.append("    </sql>\n");
        //countByMap
        inputData.append("    <!-- 查询记录总条数 -->\n");
        inputData.append("    <select id=\"countByMap\" parameterType=\"java.util.Map\" resultType=\"int\">\n");
        inputData.append("        select count(1) from "+currentTableName+"\n");
        inputData.append("        <include refid=\"selectSql\"/>\n");
        inputData.append("    </select>\n");
        inputData.append("\r\n");
        //insert
        inputData.append("    <!-- 新增记录 -->\n");
        inputData.append("    <insert id=\"insert\" parameterType=\""+entityClass+"\"\n");
        inputData.append("            useGeneratedKeys=\"true\" keyProperty=\"id\">\n");
        inputData.append("        insert into "+currentTableName+"\n");
        inputData.append("        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
        inputData.append(insertFiledData);
        inputData.append("        </trim>\n");
        inputData.append("        <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\n");
        inputData.append(insertValuedData);
        inputData.append("        </trim>\n");
        inputData.append("    </insert>\n");
        inputData.append("\n");
        //batchInsert
        inputData.append("    <!-- 批量新增记录 -->\n");
        inputData.append("    <insert id=\"batchInsert\" parameterType=\"java.util.List\">\n");
        inputData.append("    <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\";\">\n");
        inputData.append("        insert into "+currentTableName+"\n");
        inputData.append("        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
        inputData.append(insertFiledData);
        inputData.append("        </trim>\n");
        inputData.append("        <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\n");
        inputData.append(insertValuedData);
        inputData.append("        </trim>\n");
        inputData.append("    </foreach>\n");
        inputData.append("    </insert>\n");
        inputData.append("\r\n");
        //update
        inputData.append("    <!-- 更新单条记录 -->\n");
        inputData.append("    <update id=\"update\" parameterType=\""+entityClass+"\">\n");
        inputData.append("        update user\n");
        inputData.append("        <set>\n");
        inputData.append("            <trim suffixOverrides=\",\">\n");
        inputData.append(updateData);
        inputData.append("            </trim>\n");
        inputData.append("        </set>\n");
        inputData.append("        where id = #{id}\n");
        inputData.append("    </update>\n");
        inputData.append("\r\n");
        //batchUpdate
        inputData.append("    <!-- 批量更新记录 -->\n");
        inputData.append("    <update id=\"batchUpdate\" parameterType=\"java.util.List\">\n");
        inputData.append("    <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\"OR\">\n");
        inputData.append("        UPDATE "+currentTableName+"\n");
        inputData.append("        <set>\n");
        inputData.append("            <trim suffixOverrides=\",\">\n");
        inputData.append(updateData);
        inputData.append("            </trim>\n");
        inputData.append("        </set>\n");
        inputData.append("        <where>\n");
        inputData.append("            id = #{item.id}\n");
        inputData.append("        </where>\n");
        inputData.append("    </foreach>\n");
        inputData.append("    </update>\n");
        inputData.append("\r\n");
        //deleteById
        inputData.append("    <!-- 通过主键删除记录 -->\n");
        inputData.append("    <delete id=\"deleteById\" parameterType=\"java.lang.Long\">\n");
        inputData.append("        delete from "+currentTableName+" where id = #{id}\n");
        inputData.append("    </delete>\n");
        inputData.append("\r\n");
        //batchDelete
        inputData.append("    <!-- 批量删除记录 -->\n");
        inputData.append("    <delete id=\"batchDelete\" parameterType=\"java.util.List\">\n");
        inputData.append("        delete from "+currentTableName+" where id IN\n");
        inputData.append("        <foreach collection=\"list\" item=\"item\" open=\"(\" separator=\",\" close=\")\">\n");
        inputData.append("            #{item.id}\n");
        inputData.append("        </foreach>\n");
        inputData.append("    </delete>\n");
        inputData.append("\r\n");
        //selectById
        inputData.append("    <!-- 通过主键查找记录 -->\n");
        inputData.append("    <select id=\"selectById\" parameterType=\"java.lang.Long\" resultMap=\"BaseResultMap\"\n");
        inputData.append("            resultType=\""+entityClass+"\">\n");
        inputData.append("        select * from "+currentTableName+" where id = #{id}\n");
        inputData.append("    </select>\n");
        inputData.append("\r\n");
        //selectByProps
        inputData.append("    <!-- 查询符合条件的实体对象 -->\n");
        inputData.append("    <select id=\"selectByProps\" parameterType=\"java.util.Map\" resultMap=\"BaseResultMap\"\n");
        inputData.append("            resultType=\""+entityClass+"\">\n");
        inputData.append("        select * from "+currentTableName+"\n");
        inputData.append("        <include refid=\"selectSql\"/>\n");
        inputData.append("    </select>\n");
        inputData.append("\r\n");
        //selectAll
        inputData.append("    <!-- 查询所有实体对象 -->\n");
        inputData.append("    <select id=\"selectAll\"  resultMap=\"BaseResultMap\"\n");
        inputData.append("            resultType=\""+entityClass+"\">\n");
        inputData.append("        select * from "+currentTableName+" where 1=1\n");
        inputData.append("    </select>\n");
        inputData.append("\r\n");
        //selectByNativeSql
        inputData.append("    <!-- 根据原生Sql查询记录 -->\n");
        inputData.append("    <select id=\"selectByNativeSql\"  resultMap=\"BaseResultMap\"\n");
        inputData.append("            resultType=\""+entityClass+"\">\n");
        inputData.append("        ${value}\n");
        inputData.append("    </select>\n");
        inputData.append("\r\n");

        inputData.append("</mapper>");

        writeFile(file, inputData);
        System.out.print("新增\r\n    " + directoryName + "\\" + fileName);
    }

    public static void main(String[] args) {
        readProperties();
        initMysqlTypes();
        try {
            getConnection();
            Arrays.asList(tableNames).stream().forEach((String x) -> {
            currentTableName = x;
            entityName = CodeUtil.convertLowerOrUpper(currentTableName, "_", false)+"Entity";
            daoName = CodeUtil.convertLowerOrUpper(currentTableName, "_", false)+"Mapper";
            serviceName = "I"+CodeUtil.convertLowerOrUpper(currentTableName, "_", false)+"Service";
            serviceImplName = CodeUtil.convertLowerOrUpper(currentTableName, "_", false)+"ServiceImpl";
            try {
                executeQuery();
                System.out.println("\r\n"+currentTableName);
//                generaneEntity();
//                generaneDao();
//                generaneService();
//                generaneServiceImpl();
                generaneMapper();
            } catch (Exception e) {
                e.printStackTrace();
            }
            });
        } catch (Exception e) {

        } finally {
            try {
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(pstate != null){
                    pstate.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(conn != null){
                        conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    }

}

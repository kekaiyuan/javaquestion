package com.kky.reflect;

import com.kky.entity.*;
import com.kky.util.DBUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @author 柯凯元
 * @date 2021/07/05 21:22
 */
/*
想查询 n 张表的数据，但是不想写 n 个方法，能否使用一个方法完成所有工作
 */
public class BaseDaoImpl {
    /**
     * 统一的查询表的方法
     *
     * @param sql        不同的sql语句
     * @param parameters sql语句的参数
     * @param aClass     sql语句查询返回的对象
     * @return
     */
    public List query(String sql, Object[] parameters, Class aClass) {
        List list = new ArrayList();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            //建立连接
            connection = DBUtil.getConnection();

            //创建预处理块对象
            statement = connection.prepareStatement(sql);

            //给 sql 语句填充参数
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    statement.setObject(i + 1, parameters[i]);
                }
            }

            /*
            执行查询操作
            resultSet 中有返回的结果，需要将返回的结果放置到不同的对象中
             */
            resultSet = statement.executeQuery();
            //获取结果集合的元数据对象
            ResultSetMetaData metaData = resultSet.getMetaData();
            //判断查询到的每一行记录包含多少个列
            int columnCount = metaData.getColumnCount();
            //循环遍历 resultSet
            while (resultSet.next()) {
                //创建放置具体结果属性的对象
                Object object = aClass.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    //获取列的值
                    Object objectValue = resultSet.getObject(i + 1);
                    //获取列的名称
                    String columnName = metaData.getColumnName(i + 1).toLowerCase();
                    //获取类中的属性
                    Field declaredField = aClass.getDeclaredField(columnName);
                    //获取类中属性对应的set方法
                    Method method = aClass.getDeclaredMethod(getSetName(columnName),
                            declaredField.getType());
                    if (objectValue instanceof Number) {
                        Number number = (Number) objectValue;
                        String fname = declaredField.getType().getName();
                        if (fname.equals("int") || fname.equals("java.lang.Integer")) {
                            method.invoke(object, number.intValue());
                        } else if (fname.equals("byte") || fname.equals("java.lang.Byte")) {
                            method.invoke(object, number.byteValue());
                        } else if (fname.equals("short") || fname.equals("java.lang.Short")) {
                            method.invoke(object, number.shortValue());
                        } else if (fname.equals("long") || fname.equals("java.lang.Long")) {
                            method.invoke(object, number.longValue());
                        } else if (fname.equals("float") || fname.equals("java.lang.Float")) {
                            method.invoke(object, number.floatValue());
                        } else if (fname.equals("double") || fname.equals("java.lang.Double")) {
                            method.invoke(object, number.doubleValue());
                        }
                    } else if (objectValue instanceof java.util.Date) {
                        method.invoke(object,
                                new SimpleDateFormat("yyyy-mm-dd").format(objectValue));
                    } else {
                        method.invoke(object, objectValue);
                    }
                }
                list.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(connection, statement, resultSet);
        }

        return list;
    }

    public void save(Object object) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBUtil.getConnection();

            Class aClass = object.getClass();
            Method[] declaredMethods = aClass.getDeclaredMethods();

            List columnName = new ArrayList();
            List columnValue = new ArrayList();

            for (Method declaredMethod : declaredMethods) {
                if (declaredMethod.getName().contains("get")) {
                    columnName.add(declaredMethod.getName().substring(3).toLowerCase());
                    columnValue.add(declaredMethod.invoke(object));
                }
            }

            int size = columnName.size();
            String sql = "insert into ";
            sql += aClass.getSimpleName().toLowerCase() + "(";

            for (Object o : columnName) {
                sql += o + ",";
            }

            sql=sql.substring(0,sql.length()-1) + ") values(" ;
            for (Object o : columnValue) {
                if(o instanceof String){
                    sql += "'"+ o +"',";
                }else{
                    sql += o + ",";
                }
            }
            System.out.println(sql);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(connection, statement);
        }
    }

    public String getSetName(String name) {
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String getGetName(String name) {
        return "get";
    }

    public static void main(String[] args) {
        BaseDaoImpl baseDao = new BaseDaoImpl();
//        List rows = baseDao.query("select * from emp where deptno = ?",
//                new Object[]{10}, Emp.class);
//        for (Object row : rows) {
//            Emp emp = (Emp) row;
//            System.out.println(emp);
//        }
//
//        List rows1 = baseDao.query("select * from dept ",
//                new Object[]{}, Dept.class);
//        for (Object row : rows1) {
//            Dept dept = (Dept) row;
//            System.out.println(dept);
//        }

        Emp emp = new Emp(4444, "sisi", "SALES",
                1111, "19991111", 1500.0, 500.0, 10);
        baseDao.save(emp);
    }
}

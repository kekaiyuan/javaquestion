package com.kky.pool.c3p0;

import com.kky.pool.Test;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 柯凯元
 * @date 2021/07/07 13:57
 */
/*
使用配置文件进行连接
c3p0-config.xml 或 c3p0.properties 其中任一有效，另一个加.bak后缀使系统无法识别
 */
public class C3P0Test {
    public static Connection connection;
    public static ComboPooledDataSource dataSource;

    public static void getConnection(){
        dataSource = new ComboPooledDataSource();
    }

    public static void main(String[] args) {
        /*
        直接在类的方法设置连接的参数，一般没人使用，不太建议，最好使用配置文件
         */
//        ComboPooledDataSource cpds = new ComboPooledDataSource();
//        try {
//            cpds.setDriverClass( "com.mysql.cj.jdbc.Driver" ); //loads the jdbc driver
//        } catch (PropertyVetoException e) {
//            e.printStackTrace();
//        }
//        cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/test" );
//        cpds.setUser("root");
//        cpds.setPassword("123456");
        getConnection();
        Test.query(dataSource);
    }
}

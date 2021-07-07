package com.kky.util;

import java.sql.*;

/**
 * @author 柯凯元
 * @date 2021/07/04 21:00
 */
public class OracleDBUtil {
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static final String USER = "tiger";
    public static final String PASSWORD = "123456";

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     *
     * @return 返回连接对象
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭连接
     * @param connection
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接
     * @param connection
     * @param statement
     */
    public static void closeConnection(Connection connection, Statement statement) {
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接
     * @param connection
     * @param statement
     * @param resultSet
     */
    public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}

package com.kky.dao.impl;

import com.kky.util.OracleDBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author 柯凯元
 * @date 2021/07/05 16:09
 */
public class BatchDaoImpl {
    public static void main(String[] args) {
        insertBatch();
    }

    public static void insertBatch() {
        Connection connection = OracleDBUtil.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String sql = "insert into emp(empno,ename) values (?,?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < 10; i++) {
                preparedStatement.setInt(1, i + 1000);
                preparedStatement.setString(2, "kky" + i);
                preparedStatement.addBatch();
            }
            int[] ints = preparedStatement.executeBatch();
            for (int anInt : ints) {
                System.out.println(anInt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            OracleDBUtil.closeConnection(connection, preparedStatement);
        }

    }

}

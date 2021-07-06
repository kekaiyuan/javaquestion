package com.kky;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author 柯凯元
 * @date 2021/07/04 20:30
 */
public class CreateSequence {
    public static void main(String[] args) throws Exception{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection
                ("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
        Statement statement = connection.createStatement();
        String sql = "create sequence seq_psn " +
                "increment by 1" +
                "start with 1";
        boolean execute = statement.execute(sql);
        System.out.println(execute);
        statement.close();
        connection.close();
    }
}

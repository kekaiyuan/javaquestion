package com.kky.apachedbutil;

import com.kky.entity.Emp;
import com.kky.util.MysqlDBUtil;
import com.kky.util.OracleDBUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 柯凯元
 * @date 2021/07/06 15:33
 */
public class DBUtilTest {
    public static Connection connection = null;

    public static void testQuery() throws SQLException {
        connection = MysqlDBUtil.getConnection();
        String sql = "select * from emp where empno = ?";
        QueryRunner runner = new QueryRunner();
        Emp query = runner.query(connection, sql, new BeanHandler<Emp>(Emp.class), 7369);
        System.out.println(query);
        connection.close();
    }

    public static void main(String[] args) throws SQLException {
        testQuery();
    }

}

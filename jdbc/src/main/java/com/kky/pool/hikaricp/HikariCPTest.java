package com.kky.pool.hikaricp;

import com.kky.pool.Test;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author 柯凯元
 * @date 2021/07/07 18:06
 */
public class HikariCPTest {
    public static void main(String[] args) {
        //第一种方式
        {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/test");
            config.setUsername("root");
            config.setPassword("123456");
            HikariDataSource ds = new HikariDataSource(config);
            //Test.query(ds);
        }

        //第二种方式
        {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
            ds.setUsername("root");
            ds.setPassword("123456");
            //Test.query(ds);
        }

        //第三种方式
        {
            HikariConfig config = new HikariConfig("src/main/resources/hikari.properties");
            HikariDataSource ds = new HikariDataSource(config);
            Test.query(ds);
        }

    }
}

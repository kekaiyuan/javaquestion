package com.kky.pool.c3p0;

import com.kky.pool.Test;
import com.mchange.v2.c3p0.DataSources;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 柯凯元
 * @date 2021/07/07 17:09
 */
/*
使用配置文件进行连接
c3p0-config.xml 或 c3p0.properties 其中任一有效，另一个加.bak后缀使系统无法识别
 */
/*
使用工厂
 */
public class C3P0Test2 {
    public static void main(String[] args) throws SQLException {
        DataSource ds_unpooled = DataSources.unpooledDataSource(
                "jdbc:mysql://localhost:3306/test",
                "root",
                "123456");

        Map overrides = new HashMap();
        overrides.put("maxStatements", "200");         //Stringified property values work
        overrides.put("maxPoolSize", new Integer(50)); //"boxed primitives" also work

        DataSource ds_pooled = DataSources.pooledDataSource(ds_unpooled, overrides);

        Test.query(ds_pooled);
    }
}

package com.kky.dao.impl;

import com.kky.dao.EmpDao;
import com.kky.entity.Emp;
import com.kky.util.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 柯凯元
 * @date 2021/07/04 21:40
 */
public class EmpDaoImpl implements EmpDao {
    /*
    当插入数据的时候要注意数据类型
    1. Date
    2. String类型在拼接sql语句的时候要加 ''
     */
    @Override
    public void insert(Emp emp) {
        Connection connection = DBUtil.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "insert into emp values(" +
                    emp.getEmpno() + "," +
                    "'" + emp.getEname() + "'" + "," +
                    "'" + emp.getJob() + "'" + "," +
                    emp.getMgr() + "," +
                    "to_date(" + emp.getHiredate() + ",'YYYY/MM/DD')," +
                    emp.getSal() + "," +
                    emp.getComm() + "," +
                    emp.getDeptno() +
                    ")";
            System.out.println(sql);

            //返回值表示受影响的行数
            int i = statement.executeUpdate(sql);
            System.out.println("受影响的行数：" + i);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.closeConnection(connection, statement);
        }
    }

    @Override
    public void delete(Emp emp) {
        Connection connection = DBUtil.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "delete from emp where empno = " + emp.getEmpno();
            System.out.println(sql);

            //返回值表示受影响的行数
            int i = statement.executeUpdate(sql);
            System.out.println("受影响的行数：" + i);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.closeConnection(connection, statement);
        }
    }

    @Override
    public void update(Emp emp) {

    }

    @Override
    public Emp getEmpByEmpno(Integer empno) {
        Emp emp = null;
        Connection connection = DBUtil.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String sql = "select * from emp where empno = " + empno;
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);

            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

            while (resultSet.next()) {
                emp = new Emp(resultSet.getInt("empno"),
                        resultSet.getString("ename"),
                        resultSet.getString("job"),
                        resultSet.getInt("mgr"),
                        //sdf.format(resultSet.getString("hiredate")),
                        resultSet.getString("hiredate"),
                        resultSet.getDouble("sal"),
                        resultSet.getDouble("comm"),
                        resultSet.getInt("deptno")
                );
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.closeConnection(connection, statement,resultSet);
        }
        return emp;
    }

    @Override
    public Emp getEmpByEname(String ename) {
        Emp emp = null;
        Connection connection = DBUtil.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String sql = "select * from emp where ename = " + ename;
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);

            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

            while (resultSet.next()) {
                emp = new Emp(resultSet.getInt("empno"),
                        resultSet.getString("ename"),
                        resultSet.getString("job"),
                        resultSet.getInt("mgr"),
                        //sdf.format(resultSet.getString("hiredate")),
                        resultSet.getString("hiredate"),
                        resultSet.getDouble("sal"),
                        resultSet.getDouble("comm"),
                        resultSet.getInt("deptno")
                );
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.closeConnection(connection, statement,resultSet);
        }
        return emp;
    }

    public static void main(String[] args) {
        EmpDao empDao = new EmpDaoImpl();
//        Emp emp = new Emp(4444, "sisi", "SALES",
//                1111, "19991111", 1500.0, 500.0, 10);
//        empDao.insert(emp);

//        Emp emp = empDao.getEmpByEmpno(7369);
//        System.out.println(emp);

        Emp emp = empDao.getEmpByEname("'SMITH' or 1=1");
        System.out.println(emp);

//        empDao.delete(emp);
    }
}

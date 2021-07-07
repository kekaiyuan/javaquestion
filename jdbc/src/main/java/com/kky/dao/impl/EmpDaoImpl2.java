package com.kky.dao.impl;

import com.kky.dao.EmpDao;
import com.kky.entity.Emp;
import com.kky.util.OracleDBUtil;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author 柯凯元
 * @date 2021/07/04 21:40
 */
public class EmpDaoImpl2 implements EmpDao {
    /*
    当插入数据的时候要注意数据类型
    1. Date
    2. String类型在拼接sql语句的时候要加 ''
     */
    @Override
    public void insert(Emp emp) {
        Connection connection = OracleDBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "insert into emp values(?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, emp.getEmpno());
            preparedStatement.setString(2, emp.getEname());
            preparedStatement.setString(3, emp.getJob());
            preparedStatement.setInt(4, emp.getMgr());
            preparedStatement.setDate(
                    5,
                    new java.sql.Date(new SimpleDateFormat("yyyy-mm-dd").parse(emp.getHiredate()).getTime()));
            preparedStatement.setDouble(6, emp.getSal());
            preparedStatement.setDouble(7, emp.getComm());
            preparedStatement.setInt(8, emp.getDeptno());

            //返回值表示受影响的行数
            int i = preparedStatement.executeUpdate();
            System.out.println("受影响的行数：" + i);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            OracleDBUtil.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void delete(Emp emp) {
        Connection connection = OracleDBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        try {

            String sql = "delete from emp where empno = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, emp.getEmpno());

            //返回值表示受影响的行数
            int i = preparedStatement.executeUpdate();
            System.out.println("受影响的行数：" + i);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            OracleDBUtil.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void update(Emp emp) {

    }

    @Override
    public Emp getEmpByEmpno(Integer empno) {
        Emp emp = null;
        Connection connection = OracleDBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "select * from emp where empno = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, empno);

            resultSet = preparedStatement.executeQuery();

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
            OracleDBUtil.closeConnection(connection, preparedStatement, resultSet);
        }
        return emp;
    }

    @Override
    public Emp getEmpByEname(String ename) {
        Emp emp = null;
        Connection connection = OracleDBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {

            String sql = "select * from emp where ename = ?";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(sql);
            preparedStatement.setString(1, ename);
            resultSet = preparedStatement.executeQuery();
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
            OracleDBUtil.closeConnection(connection, preparedStatement, resultSet);
        }
        return emp;
    }

    public static void main(String[] args) {
        EmpDao empDao = new EmpDaoImpl2();

        Emp emp = new Emp(4444, "sisi", "SALES",
                1111, "1999-11-11", 1500.0, 500.0, 10);
//        empDao.insert(emp);

//        Emp emp = empDao.getEmpByEmpno(7369);
//        System.out.println(emp);

//        Emp emp = empDao.getEmpByEname("SMITH or 1=1");
//        System.out.println(emp);

        empDao.delete(emp);
    }
}

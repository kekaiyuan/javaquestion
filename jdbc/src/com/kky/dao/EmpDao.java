package com.kky.dao;

import com.kky.entity.Emp;

/**
 * @author 柯凯元
 * @date 2021/07/04 21:15
 */
public interface EmpDao {
    /**
     * 插入数据
     * @param emp
     */
    public void insert(Emp emp);

    /**
     * 删除数据
     * @param emp
     */
    public void delete(Emp emp);

    /**
     * 修改数据
     * @param emp
     */
    public void update(Emp emp);

    /**
     * 查找数据
     * @param empno
     * @return
     */
    public Emp getEmpByEmpno(Integer empno);

    /**
     * 查找数据
     * @param ename
     * @return
     */
    public Emp getEmpByEname(String ename);
}

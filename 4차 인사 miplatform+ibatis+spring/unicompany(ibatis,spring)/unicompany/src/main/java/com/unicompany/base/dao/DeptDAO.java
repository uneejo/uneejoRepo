package com.unicompany.base.dao;

import java.util.List;

import com.unicompany.base.to.DepartmentBean;

public interface DeptDAO {
    public List<DepartmentBean> selectDeptList(String businessPlaceCode);

    public void insertDept(DepartmentBean deptBean);
    public void updateDept(DepartmentBean deptBean);
    public void deleteDept(DepartmentBean deptBean);
}

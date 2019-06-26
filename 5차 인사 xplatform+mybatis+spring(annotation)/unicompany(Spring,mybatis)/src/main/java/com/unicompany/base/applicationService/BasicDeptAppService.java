package com.unicompany.base.applicationService;

import java.util.List;

import com.unicompany.base.to.DepartmentBean;


public interface BasicDeptAppService {
    public List<DepartmentBean> findDeptList(String businessPlaceCode);
    public void batchDept(List<DepartmentBean> deptList);
}

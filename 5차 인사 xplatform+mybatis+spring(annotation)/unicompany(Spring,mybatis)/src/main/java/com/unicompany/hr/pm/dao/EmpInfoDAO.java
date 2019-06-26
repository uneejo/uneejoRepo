package com.unicompany.hr.pm.dao;

import java.util.List;

import com.unicompany.hr.pm.to.EmpInfoBean;


public interface EmpInfoDAO {
    public List<EmpInfoBean> selectEmpInfoAll();
   
    public void updateEmpInfo(EmpInfoBean empInfoBean);
}

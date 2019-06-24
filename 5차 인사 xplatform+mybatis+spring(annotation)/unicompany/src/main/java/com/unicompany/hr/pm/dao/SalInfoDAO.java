package com.unicompany.hr.pm.dao;

import java.util.List;

import com.unicompany.hr.pm.to.SalInfoBean;

public interface SalInfoDAO {
    public List<SalInfoBean> selectSalInfoAll();
    
    public void updateSalInfo(SalInfoBean salInfoBean);
}

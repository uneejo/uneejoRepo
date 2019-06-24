package com.unicompany.hr.pm.dao;

import java.util.List;

import com.unicompany.hr.pm.to.WorkInfoBean;

public interface WorkInfoDAO {
    public List<WorkInfoBean> selectWorkInfoAll();
    
    public void updateWorkInfo(WorkInfoBean workInfoBean);
}

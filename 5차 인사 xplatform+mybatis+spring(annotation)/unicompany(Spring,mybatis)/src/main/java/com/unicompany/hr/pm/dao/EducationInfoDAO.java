package com.unicompany.hr.pm.dao;

import java.util.List;

import com.unicompany.hr.pm.to.EducationInfoBean;

public interface EducationInfoDAO {
    public List<EducationInfoBean> selectEducationInfoAll();
    public void insertEducationInfo(EducationInfoBean educationInfoBean);
	public void updateEducationInfo(EducationInfoBean educationInfoBean);
	public void deleteEducationInfo(EducationInfoBean educationInfoBean);
}

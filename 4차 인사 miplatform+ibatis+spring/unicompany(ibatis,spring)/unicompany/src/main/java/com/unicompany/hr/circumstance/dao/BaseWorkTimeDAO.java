package com.unicompany.hr.circumstance.dao;

import java.util.List;

import com.unicompany.hr.circumstance.to.BaseWorkTimeBean;

public interface BaseWorkTimeDAO {
	public List<BaseWorkTimeBean> selectBaseWorkTimeList();
	
	public void insertBaseWorkTime(BaseWorkTimeBean baseWorkTimeBean);
	public void updateBaseWorkTime(BaseWorkTimeBean baseWorkTimeBean);
	/*
	public void deleteBaseWorkTime(BaseWorkTimeBean baseWorkTimeBean);
	*/
}

package com.unicompany.hr.circumstance.dao;

import java.util.List;

import com.unicompany.hr.circumstance.to.HobongBean;

public interface HobongDAO {
	public List<HobongBean> selectHobongList();	
	
	public void insertHobong(HobongBean hobongBean);
	public void updateHobong(HobongBean hobongBean);
	public void deleteHobong(HobongBean hobongBean);
	
}

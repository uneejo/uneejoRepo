package com.unicompany.hr.circumstance.applicationService;

import java.util.List;

import com.unicompany.hr.circumstance.to.HobongBean;

public interface HobongAppService {
	public List<HobongBean> findHobongList();
	
	public void batchHobong(List<HobongBean> hobongList);
	
}

package com.project.uniproduct.authorityManager.dao;

import java.util.HashMap;

import com.project.uniproduct.authorityManager.to.UserMenuTO;

public interface UserMenuDAO {

	public HashMap<String, UserMenuTO> selectUserMenuCodeList(String workplaceCode, String deptCode, String positionCode);

	
}

package com.project.uniproduct.hr.dao;

import java.util.ArrayList;

import com.project.uniproduct.hr.to.EmpInfoTO;

public interface EmpSearchingDAO {

	public ArrayList<EmpInfoTO> selectAllEmpList(String searchCondition, String[] paramArray);

	public ArrayList<EmpInfoTO> getTotalEmpInfo(String companyCode, String workplaceCode, String userId);
	
	
}

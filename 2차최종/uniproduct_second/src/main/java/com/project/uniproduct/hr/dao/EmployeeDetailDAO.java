package com.project.uniproduct.hr.dao;

import java.util.ArrayList;

import com.project.uniproduct.hr.to.EmployeeDetailTO;

public interface EmployeeDetailDAO {

	public ArrayList<EmployeeDetailTO> selectEmployeeDetailList(String companyCode, String empCode);
	
	public ArrayList<EmployeeDetailTO> selectUserIdList(String companyCode);
	
	public void insertEmployeeDetail(EmployeeDetailTO TO);
	

}

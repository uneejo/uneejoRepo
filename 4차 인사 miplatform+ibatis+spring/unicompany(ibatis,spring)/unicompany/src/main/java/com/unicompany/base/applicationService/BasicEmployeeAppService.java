package com.unicompany.base.applicationService;

import java.util.List;

import com.unicompany.base.to.EmployeeBean;
import com.unicompany.base.to.EmployeeHireDateBean;


public interface BasicEmployeeAppService {
	
	public List<EmployeeBean> findEmployeeList();
   
	public void batchEmployee(EmployeeBean employeeBean);
	
	public List<EmployeeBean> findFilterEmployeeList();

	public void batcEditEmployee(List<EmployeeBean> employeeList);

    public List<EmployeeHireDateBean> findEmployeeHireDateList();
}

package com.unicompany.hr.pm.applicationService;

import com.unicompany.hr.pm.to.EmployeeInfoBean;

public interface PMAppService {
	
	public EmployeeInfoBean findEmployeeInfoAll();

	public void batchEmployeeInfoAll(EmployeeInfoBean employeeInfoBean);

}

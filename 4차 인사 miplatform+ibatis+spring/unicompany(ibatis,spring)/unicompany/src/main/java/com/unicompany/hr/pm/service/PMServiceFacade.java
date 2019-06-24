package com.unicompany.hr.pm.service;

import com.unicompany.hr.pm.to.EmployeeInfoBean;

public interface PMServiceFacade {
	public EmployeeInfoBean findEmployeeInfoAll();
	public void batchEmployeeInfoAll(EmployeeInfoBean employeeInfoBean);
}

package com.unicompany.hr.pm.serviceFacade;

import java.util.List;

import com.unicompany.hr.pm.to.EmployeeInfoBean;
import com.unicompany.hr.pm.to.ReportBean;

public interface PMServiceFacade {
	public EmployeeInfoBean findEmployeeInfoAll();
	public void batchEmployeeInfoAll(EmployeeInfoBean employeeInfoBean);
	public List<ReportBean> findEmpInfoReport(String empCode);
	
}


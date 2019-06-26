package com.unicompany.hr.pm.applicationService;

import java.util.List;

import com.unicompany.hr.pm.to.EmployeeInfoBean;
import com.unicompany.hr.pm.to.ReportBean;

public interface PMAppService {
	
	public EmployeeInfoBean findEmployeeInfoAll();

	public void batchEmployeeInfoAll(EmployeeInfoBean employeeInfoBean);
	
    public List<ReportBean> findEmpInfoReport(String empCode);

}

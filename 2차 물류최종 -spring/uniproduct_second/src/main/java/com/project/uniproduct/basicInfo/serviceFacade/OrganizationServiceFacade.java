package com.project.uniproduct.basicInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.basicInfo.to.CompanyTO;
import com.project.uniproduct.basicInfo.to.DepartmentTO;
import com.project.uniproduct.basicInfo.to.WorkplaceTO;

public interface OrganizationServiceFacade {

	public ArrayList<CompanyTO> getCompanyList();
	
	public ArrayList<WorkplaceTO> getWorkplaceList(String companyCode);

	public ArrayList<DepartmentTO> getDepartmentList(String searchCondition, String companyCode,
			String workplaceCode);
	
	public HashMap<String, Object> batchCompanyListProcess(ArrayList<CompanyTO> companyList);
	
	public HashMap<String, Object> batchWorkplaceListProcess(ArrayList<WorkplaceTO> workplaceList);
		
	public HashMap<String, Object> batchDepartmentListProcess(ArrayList<DepartmentTO> departmentList);


}

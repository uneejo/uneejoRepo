package com.project.uniproduct.basicInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.basicInfo.applicationService.CompanyApplicationService;
import com.project.uniproduct.basicInfo.applicationService.DepartmentApplicationService;
import com.project.uniproduct.basicInfo.applicationService.WorkplaceApplicationService;
import com.project.uniproduct.basicInfo.to.CompanyTO;
import com.project.uniproduct.basicInfo.to.DepartmentTO;
import com.project.uniproduct.basicInfo.to.WorkplaceTO;


public class OrganizationServiceFacadeImpl implements OrganizationServiceFacade {


	private CompanyApplicationService companyAS;
	private WorkplaceApplicationService workplaceAS;
	private DepartmentApplicationService deptAS;

	public void setCompanyAS(CompanyApplicationService companyAS) {
		this.companyAS=companyAS;
	}
	 
	public void setWorkplaceAS(WorkplaceApplicationService workplaceAS) {
		this.workplaceAS=workplaceAS;
	}
	
	public void setDeptAS(DepartmentApplicationService deptAS) {
		this.deptAS=deptAS;
	}
	
	@Override
	public ArrayList<CompanyTO> getCompanyList() {

			return companyAS.getCompanyList();
			
	}

	@Override
	public ArrayList<WorkplaceTO> getWorkplaceList(String companyCode) {

			return workplaceAS.getWorkplaceList(companyCode);
			
	}

	@Override
	public ArrayList<DepartmentTO> getDepartmentList(String searchCondition, String companyCode,
			String workplaceCode) {

			return deptAS.getDepartmentList(searchCondition, companyCode, workplaceCode);
			
	}

	@Override
	public HashMap<String, Object> batchCompanyListProcess(ArrayList<CompanyTO> companyList) {

			return companyAS.batchCompanyListProcess(companyList);
			
	}

	@Override
	public HashMap<String, Object> batchWorkplaceListProcess(ArrayList<WorkplaceTO> workplaceList) {


			return workplaceAS.batchWorkplaceListProcess(workplaceList);
			
	}

	@Override
	public HashMap<String, Object> batchDepartmentListProcess(ArrayList<DepartmentTO> departmentList) {


			return deptAS.batchDepartmentListProcess(departmentList);
			
	}

}

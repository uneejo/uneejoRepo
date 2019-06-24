package com.unicompany.hr.circumstance.applicationService;

import java.util.List;

import com.unicompany.common.exception.ProcedureException;
import com.unicompany.hr.circumstance.to.AnnualBean;

public interface AnnualAppService {
	public List<AnnualBean> findAnnualList();
	
	public List<AnnualBean> createAnnual(String standardYear,String empCode,String hireDate) throws ProcedureException;
	
	public void batchAnnual(List<AnnualBean> annualList);
	
	public List<AnnualBean> editAnnualMgt(String standardYear,String empCode,String days,String restDays);
	
}

package com.project.uniproduct.authorityManager.serviceFacade;

import javax.servlet.ServletContext;

import com.project.uniproduct.authorityManager.exception.IdNotFoundException;
import com.project.uniproduct.authorityManager.exception.PwMissMatchException;
import com.project.uniproduct.authorityManager.exception.PwNotFoundException;
import com.project.uniproduct.hr.to.EmpInfoTO;

public interface AuthorityManagerServiceFacade {

	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException;

	public String getUserMenuCode(String workplaceCode, String deptCode, String positionCode,
			ServletContext application);

}

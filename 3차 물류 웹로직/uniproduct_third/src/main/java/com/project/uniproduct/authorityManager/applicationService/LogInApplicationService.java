package com.project.uniproduct.authorityManager.applicationService;

import com.project.uniproduct.authorityManager.exception.IdNotFoundException;
import com.project.uniproduct.authorityManager.exception.PwMissMatchException;
import com.project.uniproduct.authorityManager.exception.PwNotFoundException;
import com.project.uniproduct.hr.to.EmpInfoTO;

public interface LogInApplicationService {

	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException;

}

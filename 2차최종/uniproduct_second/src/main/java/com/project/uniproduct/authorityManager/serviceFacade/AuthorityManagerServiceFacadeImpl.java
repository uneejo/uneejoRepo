package com.project.uniproduct.authorityManager.serviceFacade;

import javax.servlet.ServletContext;

import com.project.uniproduct.authorityManager.applicationService.LogInApplicationService;
import com.project.uniproduct.authorityManager.applicationService.UserMenuApplicationService;
import com.project.uniproduct.authorityManager.exception.IdNotFoundException;
import com.project.uniproduct.authorityManager.exception.PwMissMatchException;
import com.project.uniproduct.authorityManager.exception.PwNotFoundException;
import com.project.uniproduct.hr.to.EmpInfoTO;

public class AuthorityManagerServiceFacadeImpl implements AuthorityManagerServiceFacade {

	
	private LogInApplicationService logInAS;
	private UserMenuApplicationService userMenuAS;
	
	public void setLogInAS(LogInApplicationService logInAS) {
		this.logInAS=logInAS;
	}
	
	public void setUserMenuAS(UserMenuApplicationService userMenuAS) {
		this.userMenuAS=userMenuAS;
	}

	@Override
	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException {

			return logInAS.accessToAuthority(companyCode, workplaceCode, inputId, inputPassWord);
		
	}

	@Override
	public String getUserMenuCode(String workplaceCode, String deptCode, String positionCode,
			ServletContext application) {
		
		
		return userMenuAS.getUserMenuCode(workplaceCode, deptCode, positionCode, application);

    }
}

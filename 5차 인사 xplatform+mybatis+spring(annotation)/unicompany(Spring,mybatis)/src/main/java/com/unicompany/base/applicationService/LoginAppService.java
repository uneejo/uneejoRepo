package com.unicompany.base.applicationService;

import com.unicompany.base.exception.BusinessPlaceNotFoundException;
import com.unicompany.base.exception.DeptNotFoundException;
import com.unicompany.base.exception.EmpCodeNotFoundException;
import com.unicompany.base.exception.PwMissMatchException;
import com.unicompany.base.to.EmployeeBean;

public interface LoginAppService {

    public EmployeeBean checkLogin(String businessPlaceCode,String deptCode,String empCode,String password)throws EmpCodeNotFoundException,BusinessPlaceNotFoundException,DeptNotFoundException,PwMissMatchException;
    
    
}

	
		
	

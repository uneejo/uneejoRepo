package com.unicompany.hr.pay.serviceFacade;

import java.util.List;

import com.unicompany.common.exception.ProcedureException;
import com.unicompany.hr.pay.to.SalaryInputBean;

public interface PayServiceFacade {
	public List<SalaryInputBean> payCalculate(String paymentsDate, String standardDate) throws ProcedureException;
}

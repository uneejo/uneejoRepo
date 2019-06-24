package com.unicompany.hr.pay.applicationService;

import java.util.List;

import com.unicompany.common.exception.ProcedureException;
import com.unicompany.hr.pay.to.SalaryInputBean;

public interface PayAppService {
	public List<SalaryInputBean> payCalculate(String paymentsDate, String standardDate) throws ProcedureException;
}

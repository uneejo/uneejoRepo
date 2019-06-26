package com.unicompany.hr.pay.applicationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unicompany.common.exception.ProcedureException;
import com.unicompany.hr.pay.dao.PayDAO;
import com.unicompany.hr.pay.to.SalaryInputBean;

@Component
public class PayAppServiceImpl implements PayAppService {
	/*  PayDAO setting */
	@Autowired
	private PayDAO payDAO;
	

	/* 급여를 계산하는 메서드 */
	@Override
	public List<SalaryInputBean> payCalculate(String paymentDate, String standardDate) throws ProcedureException {
		
		Map<String, String> map =new HashMap<>();
		map.put("paymentDate", paymentDate);
		map.put("standardDate", standardDate);
		
		payDAO.payCalculate(map);
		
		if(map.get("errorCode")!=null) {
	        int errorCode = Integer.parseInt((String) map.get("errorCode"));
		if (errorCode < 0) {
			throw new ProcedureException((String) map.get("errorMsg"));
		}
		}
		return payDAO.selectSalaryInputList(paymentDate); // 급여 조회 
	}
}
